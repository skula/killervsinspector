package com.skula.killervsinspector.services;

import java.util.ArrayList;
import java.util.List;

import com.skula.killervsinspector.models.Action;
import com.skula.killervsinspector.models.Board;
import com.skula.killervsinspector.models.Person;
import com.skula.killervsinspector.models.Position;

public class GameEngine {
	public static final int TURN_KILLER = 0;
	public static final int TURN_INSPECTOR = 1;

	private Board board;

	private List<Person> evidenceDeck;
	private List<Integer> evidenceHand;
	private int killerId;
	private int inspectorId;
	private Action action;
	private Action lastAction;

	private int token;
	private boolean isFirstTurn;
	private boolean isExonerate;

	public GameEngine() {
		// Person.shufflePersons();
		this.board = new Board(Person.getAllPersons());
		Person.shufflePersons();
		this.evidenceDeck = Person.getAllPersons();
		this.evidenceHand = new ArrayList<Integer>();

		this.token = TURN_KILLER;
		this.action = null;
		this.lastAction = null;
		this.isFirstTurn = true;
		this.killerId = -1;
		this.inspectorId = -1;
		this.isExonerate = false;

		// bouchon
		this.killerId = 12;
		evidenceHand.add(18);
	}

	private boolean canProcessFirstTurn() {
		if (token == TURN_KILLER) {
			if (killerId == -1) {
				if (!action.equals(Action.PICK_EVIDENCE)) {
					this.action = null;
					return false;
				}
				return true;
			} else {
				if (!action.equals(Action.SELECT_PERSON)) {
					this.action = null;
					return false;
				}

				if (!isAdjacentPerson(killerId, action.getPosition())) {
					this.action = null;
					return false;
				}

				return true;
			}
		} else {
			if (evidenceHand.isEmpty()) {
				if (!action.equals(Action.PICK_EVIDENCE)) {
					this.action = null;
					return false;
				}
				return true;
			} else {
				if (!action.equals(Action.SELECT_PERSON)) {
					this.action = null;
					return false;
				}

				if (!canBeExonerate(action.getPosition())) {
					this.action = null;
					return false;
				}

				return true;
			}
		}
	}

	private boolean canProcessNextTurns() {
		// gestion des deplacement de lignes et colonnes
		switch (action.getType()) {
		case Action.SHIFT_DOWN:
			if (lastAction.getType() == Action.SHIFT_UP
					&& lastAction.getPosition().getX() == action.getPosition().getX()) {
				return false;
			}
			if (action.getPosition().getX() >= board.getnColumns()) {
				this.action = null;
				return false;
			}
			return true;
		case Action.SHIFT_UP:
			if (lastAction.getType() == Action.SHIFT_DOWN
					&& lastAction.getPosition().getX() == action.getPosition().getX()) {
				return false;
			}
			if (action.getPosition().getX() >= board.getnColumns()) {
				this.action = null;
				return false;
			}
			return true;
		case Action.SHIFT_LEFT:
			if (lastAction.getType() == Action.SHIFT_RIGHT
					&& lastAction.getPosition().getY() == action.getPosition().getY()) {
				return false;
			}
			if (action.getPosition().getY() >= board.getnRows()) {
				this.action = null;
				return false;
			}
			return true;
		case Action.SHIFT_RIGHT:
			if (lastAction.getType() == Action.SHIFT_LEFT
					&& lastAction.getPosition().getY() == action.getPosition().getY()) {
				return false;
			}
			if (action.getPosition().getY() >= board.getnRows()) {
				this.action = null;
				return false;
			}
			return true;
		}

		// autres actions
		if (token == TURN_KILLER) {
			switch (action.getType()) {
			case Action.SELECT_PERSON:
				if (!isAdjacentPerson(killerId, action.getPosition())) {
					this.action = null;
					return false;
				}

				if (board.get(action.getPosition()).getState() != Person.SUSPECT) {
					this.action = null;
					return false;
				}
				return true;
			case Action.PICK_EVIDENCE:
				if (evidenceDeck.isEmpty()) {
					this.action = null;
					return false;
				}
				return true;
			default:
				return false;
			}
		} else {
			if (isExonerate) {
				if (action.getType() != Action.SELECT_PERSON) {
					this.action = null;
					return false;
				}

				if (!canBeExonerate(action.getPosition())) {
					this.action = null;
					return false;
				}

				return true;
			} else {
				switch (action.getType()) {
				case Action.SELECT_PERSON:
					if (!isAdjacentPerson(inspectorId, action.getPosition())) {
						this.action = null;
						return false;
					}

					if (board.get(action.getPosition()).getState() != Person.SUSPECT) {
						this.action = null;
						return false;
					}
					return true;
				case Action.PICK_EVIDENCE:
					if (evidenceDeck.isEmpty()) {
						this.action = null;
						return false;
					}
					return true;
				default:
					return false;
				}
			}
		}
	}

	private void processFirstTurn() {
		if (token == TURN_KILLER) {
			if (killerId == -1) {
				setKillerId();
				action = null;
			} else {
				board.set(Person.DECEASED, action.getPosition());
				lastAction = action;
				action = null;
				nextPlayer();
			}
		} else {
			if (evidenceHand.isEmpty()) {
				for (int i = 0; i < 4; i++) {
					addEvidenceToHand();
				}
				this.action = null;
			} else {
				inspectorId = board.getId(action.getPosition());
				evidenceHand.remove(inspectorId);
				lastAction = action;
				action = null;
				isFirstTurn = false;
				nextPlayer();
			}
		}
	}

	private void processNextTurns() {
		// gestion des deplacement de lignes et colonnes
		switch (action.getType()) {
		case Action.SHIFT_DOWN:
			board.shiftColumn(action.getPosition().getX(), Action.SHIFT_DOWN);
			lastAction = action;
			this.action = null;
			nextPlayer();
			return;
		case Action.SHIFT_UP:
			board.shiftColumn(action.getPosition().getX(), Action.SHIFT_UP);
			lastAction = action;
			this.action = null;
			nextPlayer();
			return;
		case Action.SHIFT_LEFT:
			board.shiftRow(action.getPosition().getY(), Action.SHIFT_LEFT);
			lastAction = action;
			this.action = null;
			nextPlayer();
			return;
		case Action.SHIFT_RIGHT:
			board.shiftRow(action.getPosition().getY(), Action.SHIFT_RIGHT);
			lastAction = action;
			this.action = null;
			nextPlayer();
			return;
		}

		// autres actions
		if (token == TURN_KILLER) {
			switch (action.getType()) {
			case Action.SELECT_PERSON:
				board.set(Person.DECEASED, action.getPosition());
				lastAction = action;
				action = null;
				nextPlayer();
				return;
			case Action.PICK_EVIDENCE:
				board.set(Person.INNOCENT, board.getPosition(killerId));
				setKillerId();
				lastAction = action;
				action = null;
				nextPlayer();
				return;
			}
		} else {
			if (isExonerate) {
				board.set(Person.INNOCENT, action.getPosition());
				lastAction = action;
				action = null;
				isExonerate = false;
				nextPlayer();
			} else {
				switch (action.getType()) {
				case Action.SELECT_PERSON:
					// TODO: end of game !!!
					break;
				case Action.PICK_EVIDENCE:
					addEvidenceToHand();
					lastAction = action;
					action = null;
					isExonerate = true;
					break;
				default:
					break;
				}
			}
		}
	}

	public boolean canProcess() {
		if (isFirstTurn) {
			return canProcessFirstTurn();
		} else {
			return canProcessNextTurns();
		}
	}

	public void process() {
		if (isFirstTurn) {
			processFirstTurn();
		} else {
			processNextTurns();
		}
	}

	public void nextPlayer() {
		this.token = token == TURN_INSPECTOR ? TURN_KILLER : TURN_INSPECTOR;
	}

	public boolean canBeExonerate(Position p) {
		return evidenceHand.contains(board.getId(p.getX(), p.getY()));
	}

	public boolean isInspector(int x, int y) {
		return board.getId(x, y) == inspectorId;
	}

	public boolean isKiller(int x, int y) {
		return board.getId(x, y) == killerId;
	}

	public boolean isKillerClose(int x, int y) {
		return isAdjacentPerson(killerId, x, y);
	}

	private void setKillerId() {
		this.killerId = evidenceDeck.remove(0).getId();
	}

	public void addEvidenceToHand() {
		this.evidenceHand.add(evidenceDeck.remove(0).getId());
	}

	public void shiftRow(int rowId, int dir) {
		board.shiftRow(rowId, dir);
	}

	public void shiftColumn(int colId, int dir) {
		board.shiftColumn(colId, dir);
	}

	public void deleteRow(int rowId) {
		board.deleteRow(rowId);
	}

	public void deleteColumn(int colId) {
		board.deleteColumn(colId);
	}

	public boolean isAdjacentPerson(int id, int xDest, int yDest) {
		Position p = board.getPosition(id);
		if (p == null) {
			return false;
		}
		return board.isAdjacent(p.getX(), p.getY(), xDest, yDest);
	}

	public boolean isAdjacentPerson(int id, Position p) {
		return isAdjacentPerson(id, p.getX(), p.getY());
	}

	public boolean isEvidence(int id) {
		return evidenceHand.contains(id);
	}

	public void printEvidenceDeck() {
		System.out.print("Deck: ");
		for (Person p : evidenceDeck) {
			System.out.print(p.getId() + ", ");
		}
		System.out.println("");
	}

	public void printEvidenceHand() {
		System.out.print("Hand: ");
		for (Integer p : evidenceHand) {
			System.out.print(p + ", ");
		}
		System.out.println("");
	}

	public void printBoard() {
		board.printBoard();
	}

	public Board getBoard() {
		return board;
	}

	public int getToken() {
		return token;
	}

	public int getKillerId() {
		return killerId;
	}

	public int getInspectorId() {
		return inspectorId;
	}
}
