package com.skula.killervsinspector.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.skula.killervsinspector.models.Action;
import com.skula.killervsinspector.models.Board;
import com.skula.killervsinspector.models.Person;
import com.skula.killervsinspector.models.Position;

public class GameEngine {
	public static final int TURN_KILLER = 0;
	public static final int TURN_INSPECTOR = 1;
	public static final int MAX_DECEASED = 16;

	private Board board;
	private List<Person> evidenceDeck;
	private List<Integer> evidenceHand;

	private int token;
	private int killerId;
	private int inspectorId;

	private Action action;
	private Action lastAction;

	private int nDeceased;
	private boolean isFirstTurn;
	private boolean endOfGame;
	private boolean isExonerate;
	private boolean endOfTurn;
	
	public GameEngine() {
		// Person.shufflePersons();
		this.board = new Board(Person.getAllPersons());
		Person.shufflePersons();
		this.evidenceDeck = Person.getAllPersons();
		this.evidenceHand = new ArrayList<Integer>();

		this.token = TURN_KILLER;
		this.action = null;
		this.lastAction = new Action(Action.NONE, new Position());
		this.isFirstTurn = true;
		setKillerId();
		this.inspectorId = -1;
		this.isExonerate = false;
		this.nDeceased = 0;
		this.endOfTurn = false;

		// bouchon
		//this.killerId = 12;
		//evidenceHand.add(18);
		//evidenceHand.add(20);
		//evidenceHand.add(15);
		//evidenceHand.add(5);
		//evidenceHand.add(4);
	}

	private boolean canProcessFirstTurn() {
		if (token == TURN_KILLER) {
			if (action.getType() != Action.SELECT_PERSON) {
				this.action = null;
				return false;
			}
			if (!isAdjacentPerson(killerId, action.getPosition())) {
				this.action = null;
				return false;
			}
			return true;
		} else {
			if (evidenceHand.isEmpty()) {
				if (action.getType() != Action.PICK_EVIDENCE) {
					this.action = null;
					return false;
				}
				return true;
			} else {
				if (action.getType() != Action.SELECT_PERSON) {
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
					&& lastAction.getPosition().getX() == action.getPosition()
							.getX()) {
				return false;
			}
			if (action.getPosition().getX() >= board.getnColumns()) {
				this.action = null;
				return false;
			}
			return true;
		case Action.SHIFT_UP:
			if (lastAction.getType() == Action.SHIFT_DOWN
					&& lastAction.getPosition().getX() == action.getPosition()
							.getX()) {
				return false;
			}
			if (action.getPosition().getX() >= board.getnColumns()) {
				this.action = null;
				return false;
			}
			return true;
		case Action.SHIFT_LEFT:
			if (lastAction.getType() == Action.SHIFT_RIGHT
					&& lastAction.getPosition().getY() == action.getPosition()
							.getY()) {
				return false;
			}
			if (action.getPosition().getY() >= board.getnRows()) {
				this.action = null;
				return false;
			}
			return true;
		case Action.SHIFT_RIGHT:
			if (lastAction.getType() == Action.SHIFT_LEFT
					&& lastAction.getPosition().getY() == action.getPosition()
							.getY()) {
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

				if (board.get(action.getPosition()).isDeceased()) {
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

					if (board.get(action.getPosition()).isDeceased()
							|| board.get(action.getPosition()).isInnocent()) {
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
				board.setDeceased(true, action.getPosition());
				nDeceased++;
				lastAction = action;
				action = null;
				endOfTurn = true;
			}
		} else {
			if (evidenceHand.isEmpty()) {
				for (int i = 0; i < 4; i++) {
					addEvidenceToHand();
				}
				this.action = null;
			} else {
				inspectorId = board.getId(action.getPosition());
				//evidenceHand.remove(inspectorId);
				int cpt = 0;
				for(Integer i: evidenceHand){
					if(i == inspectorId){
						break;
					}
					cpt++;
				}
				evidenceHand.remove(cpt);

				lastAction = action;
				action = null;
				isFirstTurn = false;
				endOfTurn = true;
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
			endOfTurn = true;
			return;
		case Action.SHIFT_UP:
			board.shiftColumn(action.getPosition().getX(), Action.SHIFT_UP);
			lastAction = action;
			this.action = null;
			endOfTurn = true;
			return;
		case Action.SHIFT_LEFT:
			board.shiftRow(action.getPosition().getY(), Action.SHIFT_LEFT);
			lastAction = action;
			this.action = null;
			endOfTurn = true;
			return;
		case Action.SHIFT_RIGHT:
			board.shiftRow(action.getPosition().getY(), Action.SHIFT_RIGHT);
			lastAction = action;
			this.action = null;
			endOfTurn = true;
			return;
		}

		// autres actions
		if (token == TURN_KILLER) {
			switch (action.getType()) {
			case Action.SELECT_PERSON:
				board.setDeceased(true, action.getPosition());
				if (board.getId(action.getPosition()) == inspectorId) {
					endOfGame = true;
				}
				nDeceased++;
				if (nDeceased == MAX_DECEASED) {
					endOfGame = true;
				}
				lastAction = action;
				action = null;
				endOfTurn = true;
				return;
			case Action.PICK_EVIDENCE:
				board.setInnocent(true, board.getPosition(killerId));
				setKillerId();
				lastAction = action;
				action = null;
				endOfTurn = true;
				return;
			}
		} else {
			if (isExonerate) {
				// TODO: a vérifier
				board.setInnocent(true, action.getPosition());
				lastAction = action;
				action = null;
				isExonerate = false;
				endOfTurn = true;
			} else {
				switch (action.getType()) {
				case Action.SELECT_PERSON:
					if (board.getId(action.getPosition()) == killerId) {
						endOfGame = true;
					}
					endOfTurn = true;
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
	
	public void buildEvidenceHand(){
		evidenceHand.add(evidenceDeck.remove(0).getId());
		evidenceHand.add(evidenceDeck.remove(0).getId());
		evidenceHand.add(evidenceDeck.remove(0).getId());
		evidenceHand.add(evidenceDeck.remove(0).getId());
	}

	public boolean isEndOfGame() {
		return endOfGame;
	}

	public boolean isFirstTurn() {
		return isFirstTurn;
	}

	public boolean isEndOfTurn() {
		return endOfTurn;
	}

	public int getWinner() {
		if (nDeceased == MAX_DECEASED) {
			return TURN_KILLER;
		}
		if (board.get(board.getPosition(inspectorId)).isDeceased()) {
			return TURN_KILLER;
		}
		return TURN_INSPECTOR;
	}

	public void nextPlayer() {
		this.endOfTurn = false;
		this.token = token == TURN_INSPECTOR ? TURN_KILLER : TURN_INSPECTOR;
	}

	public boolean canBeExonerate(Position p) {
		int id = board.getId(p.getX(), p.getY());
		for(Integer tmp : evidenceHand){
			if(tmp == id){
				return true;
			}
		}
		return false;
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

	public List<Integer> getEvidenceHand() {
		return evidenceHand;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Action getLastAction() {
		return lastAction;
	}
}
