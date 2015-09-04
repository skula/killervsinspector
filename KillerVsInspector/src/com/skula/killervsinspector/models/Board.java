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
		//setDeceased(true, 0, 0);
		//setDeceased(true, 1, 0);
		//setDeceased(true, 2, 0);
		//setDeceased(true, 3, 0);
		//setDeceased(true, 4, 0);
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
		if(xSrc == xDest && ySrc == yDest){
			return false;
		}

		if(xDest < xSrc - 1 || xDest > xSrc + 1){
			return false;
		}

		if(yDest < ySrc - 1 || yDest > ySrc + 1){
			return false;
		}
		
		return true;
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

	public boolean isRowErasable(int rowId) {
		for (int i = 0; i < nRows; i++) {
			if (!board[i][rowId].isDeceased()) {
				return false;
			}
		}
		return true;
	}

	public boolean isColumnErasable(int colId) {
		for (int i = 0; i < nColumns; i++) {
			if (!board[colId][i].isDeceased()) {
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

	public void setDeceased(boolean deceased, int x, int y) {
		board[x][y].setDeceased(deceased);
	}

	public void setInnocent(boolean innocent, int x, int y) {
		board[x][y].setDeceased(innocent);
	}

	public void setDeceased(boolean deceased, Position p) {
		setDeceased(deceased, p.getX(), p.getY());
	}

	public void setInnocent(boolean innocent, Position p) {
		setInnocent(innocent, p.getX(), p.getY());
	}

	public int getnRows() {
		return nRows;
	}

	public int getnColumns() {
		return nColumns;
	}
}
