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
	public static final int MAX_DECEASED = 16;

	private Board board;
	private List<Person> evidenceDeck;
	private List<Integer> evidenceHand;

	private int token;
	private int winnerId;
	private int killerId;
	private int inspectorId;

	private Action action;
	private Action lastAction;

	private int nDeceased;
	private boolean isFirstTurn;
	private boolean endOfGame;
	private boolean isExonerating;
	private Position cluePosition;
	private boolean hasClue;
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
		this.isExonerating = false;
		this.hasClue = false;
		this.cluePosition = null;
		this.nDeceased = 0;
		this.endOfTurn = false;

		// bouchon
		// this.killerId = 12;
		// evidenceHand.add(18);
		// evidenceHand.add(20);
		// evidenceHand.add(15);
		// evidenceHand.add(5);
		// evidenceHand.add(4);
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
			if (isExonerating) {
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
						if (board.get(action.getPosition()).getId() != inspectorId) {
							this.action = null;
							return false;
						}
					}

					if (board.get(action.getPosition()).isDeceased() || board.get(action.getPosition()).isInnocent()) {
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

	private String processFirstTurn() {
		if (token == TURN_KILLER) {
			if (killerId == -1) {
				setKillerId();
				action = null;
				return "";
			} else {
				board.setDeceased(true, action.getPosition());
				nDeceased++;
				lastAction = action;
				action = null;
				endOfTurn = true;
				return "Le tueur a éliminé " + board.get(lastAction.getPosition()).getName();
			}
		} else {
			if (evidenceHand.isEmpty()) {
				for (int i = 0; i < 4; i++) {
					addEvidenceToHand();
				}
				this.action = null;
				return "La liste des innocents est vide";
			} else {
				inspectorId = board.getId(action.getPosition());
				int cpt = 0;
				for (Integer i : evidenceHand) {
					if (i == inspectorId) {
						break;
					}
					cpt++;
				}
				evidenceHand.remove(cpt);

				lastAction = action;
				action = null;
				isFirstTurn = false;
				endOfTurn = true;
				return "L'inspecteur a choisi son identité";
			}
		}
	}

	private String processNextTurns() {
		// gestion des deplacement de lignes et colonnes
		switch (action.getType()) {
		case Action.SHIFT_DOWN:
			board.shiftColumn(action.getPosition().getX(), Action.SHIFT_DOWN);
			lastAction = action;
			this.action = null;
			endOfTurn = true;
			if (token == TURN_KILLER) {
				return "Le tueur a déplacé la colonne " + (lastAction.getPosition().getX() + 1) + " vers le bas";
			} else {
				return "L'inspecteur a déplacé la colonne " + (lastAction.getPosition().getX() + 1) + " vers le bas";
			}
		case Action.SHIFT_UP:
			board.shiftColumn(action.getPosition().getX(), Action.SHIFT_UP);
			lastAction = action;
			this.action = null;
			endOfTurn = true;
			if (token == TURN_KILLER) {
				return "Le tueur a déplacé la colonne " + (lastAction.getPosition().getX() + 1) + " vers le haut";
			} else {
				return "L'inspecteur a déplacé la colonne " + (lastAction.getPosition().getX() + 1) + " vers le haut";
			}
		case Action.SHIFT_LEFT:
			board.shiftRow(action.getPosition().getY(), Action.SHIFT_LEFT);
			lastAction = action;
			this.action = null;
			endOfTurn = true;
			if (token == TURN_KILLER) {
				return "Le tueur a dÃ©placer la ligne " + (lastAction.getPosition().getX() + 1) + " vers la gauche";
			} else {
				return "L'inspecteur a déplacé la ligne " + (lastAction.getPosition().getX() + 1) + " vers la gauche";
			}
		case Action.SHIFT_RIGHT:
			board.shiftRow(action.getPosition().getY(), Action.SHIFT_RIGHT);
			lastAction = action;
			this.action = null;
			endOfTurn = true;
			if (token == TURN_KILLER) {
				return "Le tueur a déplacé la ligne " + (lastAction.getPosition().getX() + 1) + " vers la droite";
			} else {
				return "L'inspecteur a déplacé la ligne " + (lastAction.getPosition().getX() + 1) + " vers la droite";
			}
		}

		// autres actions
		if (token == TURN_KILLER) {
			switch (action.getType()) {
			case Action.SELECT_PERSON:
				board.setDeceased(true, action.getPosition());

				nDeceased++;
				if (nDeceased == MAX_DECEASED) {
					endOfGame = true;
					winnerId = TURN_KILLER;
					return "Le tueur a éliminé 16 personnes";
				}

				if (isInspector(action.getPosition())) {
					endOfGame = true;
					winnerId = TURN_KILLER;
					return "Le tueur a éliminé l'inspecteur";
				}

				lastAction = action;
				action = null;

				if (isInspectorClose(lastAction.getPosition())) {
					hasClue = true;
					cluePosition = lastAction.getPosition();
				}

				endOfTurn = true;
				return "Le tueur a éliminé " + board.get(lastAction.getPosition()).getName();
			case Action.PICK_EVIDENCE:
				int id = evidenceDeck.remove(0).getId();
				if (!board.get(id).isDeceased()) {
					Person p = board.get(killerId);
					board.setInnocent(true, board.getPosition(killerId));
					killerId = id;
					lastAction = action;
					action = null;
					endOfTurn = true;
					return "Le tueur a changé d'identité. " + p.getName() + " est disculpé";
				} else {
					board.setInnocent(true, lastAction.getPosition());
					return "Le tueur a tenté de prendre l'identité de " + board.get(lastAction.getPosition()).getName();
				}
			}
		} else {
			if (isExonerating) {
				board.setInnocent(true, action.getPosition());
				evidenceHand.remove(new Integer(board.getId(action.getPosition())));
				lastAction = action;
				action = null;
				isExonerating = false;
				if (isKillerClose(lastAction.getPosition())) {
					hasClue = true;
					cluePosition = lastAction.getPosition();
				}
				endOfTurn = true;
				return "L'inspecteur disculpe " + board.get(lastAction.getPosition()).getName();
			} else {
				switch (action.getType()) {
				case Action.SELECT_PERSON:
					if (board.getId(action.getPosition()) == killerId) {
						endOfGame = true;
						winnerId = TURN_INSPECTOR;
						return "L'inspecteur a arreté le tueur";
					}
					board.setInnocent(true, action.getPosition());
					lastAction = action;
					action = null;
					endOfTurn = true;
					return "L'inspecteur arrête " + board.get(lastAction.getPosition()).getName();
				case Action.PICK_EVIDENCE:
					addEvidenceToHand();
					lastAction = action;
					action = null;
					isExonerating = true;
					break;
				default:
					break;
				}
			}
		}
		return "";
	}

	public boolean canProcess() {
		if (isFirstTurn) {
			return canProcessFirstTurn();
		} else {
			return canProcessNextTurns();
		}
	}

	public String process() {
		if (isFirstTurn) {
			return processFirstTurn();
		} else {
			return processNextTurns();
		}
	}

	public void buildEvidenceHand() {
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

	public void nextPlayer() {
		this.endOfTurn = false;
		this.hasClue = false;
		this.cluePosition = null;
		this.token = token == TURN_INSPECTOR ? TURN_KILLER : TURN_INSPECTOR;
	}

	public boolean canBeExonerate(Position p) {
		int id = board.getId(p.getX(), p.getY());
		for (Integer tmp : evidenceHand) {
			if (tmp == id) {
				return true;
			}
		}
		return false;
	}

	public boolean isInspector(int x, int y) {
		return board.getId(x, y) == inspectorId;
	}

	public boolean isInspector(Position p) {
		return board.getId(p.getX(), p.getY()) == inspectorId;
	}

	public boolean isKiller(int x, int y) {
		return board.getId(x, y) == killerId;
	}

	public boolean isKillerClose(int x, int y) {
		return isAdjacentPerson(killerId, x, y);
	}

	public boolean isKillerClose(Position pos) {
		return isAdjacentPerson(killerId, pos.getX(), pos.getY());
	}

	public boolean isInspectorClose(Position pos) {
		return isAdjacentPerson(inspectorId, pos.getX(), pos.getY());
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

	public boolean hasClue() {
		return hasClue;
	}

	public Position getCluePosition() {
		return cluePosition;
	}

	public int getWinner() {
		return winnerId;
	}
}
