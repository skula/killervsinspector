package com.skula.killervsinspector.models;

import java.util.List;

public class Board {

	private Person[][] board;
	private int nRows;
	private int nColumns;

	public Board(List<Person> persons) {
		this.nRows = 5;
		this.nColumns = 5;
		int cpt = 0;
		board = new Person[nColumns][nRows];
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nColumns; j++) {
				this.board[j][i] = persons.get(cpt);
				cpt++;
			}
		}

		// bouchons
		set(Person.DECEASED, 0, 0);
		set(Person.DECEASED, 1, 0);
		set(Person.DECEASED, 2, 0);
		set(Person.DECEASED, 3, 0);
		set(Person.INNOCENT, 4, 0);
	}

	public int getId(int i, int j) {
		return board[i][j].getId();
	}

	public int getId(Position p) {
		return board[p.getX()][p.getY()].getId();
	}

	public Position getPosition(int id) {
		int ySrc = 0;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nColumns; j++) {
				if (getId(i, j) == id) {
					return new Position(i, j);
				}
			}
		}
		return null;
	}

	public boolean isAdjacent(int xSrc, int ySrc, int xDest, int yDest) {
		return (xDest != xSrc || yDest != ySrc)
				&& (xDest >= xSrc - 1 && xDest <= xSrc + 1)
				&& (yDest >= ySrc - 1 && yDest <= ySrc + 1);
	}

	public void shiftColumn(int colId, int dir) {
		Person tmp = null;
		if (dir == Action.SHIFT_UP) {
			tmp = board[colId][0];
			for (int i = 0; i < nRows - 1; i++) {
				board[colId][i] = board[colId][i + 1];
			}
			board[colId][nRows - 1] = tmp;
		} else {
			tmp = board[colId][nRows - 1];
			for (int i = nRows - 2; i >= 0; i--) {
				board[colId][i + 1] = board[colId][i];
			}
			board[colId][0] = tmp;
		}
	}

	public void shiftRow(int rowId, int dir) {
		Person tmp = null;
		if (dir == Action.SHIFT_LEFT) {
			tmp = board[0][rowId];
			for (int i = 0; i < nColumns - 1; i++) {
				board[i][rowId] = board[i + 1][rowId];
			}
			board[nColumns - 1][rowId] = tmp;
		} else {
			tmp = board[nColumns - 1][rowId];
			for (int i = nColumns - 2; i >= 0; i--) {
				board[i + 1][rowId] = board[i][rowId];
			}
			board[0][rowId] = tmp;
		}
	}

	public boolean isRowEmpty(int rowId) {
		for (int i = 0; i < nRows; i++) {
			if (board[i][rowId].getState() == Person.SUSPECT) {
				return false;
			}
		}
		return true;
	}

	public boolean isColumnEmpty(int colId) {
		for (int i = 0; i < nColumns; i++) {
			if (board[colId][i].getState() == Person.SUSPECT) {
				return false;
			}
		}
		return true;
	}

	public void deleteColumn(int colId) {
		Person[][] tmp = new Person[nRows - 1][nColumns];
		int cpt = 0;
		for (int i = 0; i < nRows; i++) {
			if (i != colId) {
				for (int j = 0; j < nColumns; j++) {
					tmp[cpt][j] = board[i][j];
				}
				cpt++;
			}
		}

		nColumns--;
		board = tmp;
	}

	public void deleteRow(int rowId) {
		Person[][] tmp = new Person[nRows][nColumns - 1];
		int cpt = 0;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nColumns; j++) {
				if (j != rowId) {
					tmp[i][cpt] = board[i][j];
					cpt++;
				}
			}
			cpt = 0;
		}

		nRows--;
		board = tmp;
	}
	
	public Person get(int x, int y) {
		return board[x][y];
	}

	public Person get(Position p) {
		return board[p.getX()][p.getY()];
	}

	public void printBoard() {
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nColumns; j++) {
				if (board[j][i].getState() == Person.SUSPECT) {
					System.out.print("?" + board[j][i].getId() + "\t");
				} else if (board[j][i].getState() == Person.DECEASED) {
					System.out.print("*" + board[j][i].getId() + "\t");
				} else {
					System.out.print("!" + board[j][i].getId() + "\t");
				}
			}
			System.out.println("");
		}
	}

	public Position getPositionByOrder(int o) {
		return new Position(o / nColumns, o % nColumns);
	}

	public void set(int state, int x, int y) {
		board[x][y].setState(state);
	}

	public void set(int state, Position p) {
		board[p.getX()][p.getY()].setState(state);
	}

	public int getnRows() {
		return nRows;
	}

	public int getnColumns() {
		return nColumns;
	}
}
