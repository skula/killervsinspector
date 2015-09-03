package com.skula.killervsinspector.models;

public class Action {
	public static final int SELECT_PERSON = 0;
	public static final int PICK_EVIDENCE = 1;
	public static final int END_TURN = 2;

	public static final int SHIFT_UP = 3;
	public static final int SHIFT_DOWN = 4;
	public static final int SHIFT_LEFT = 5;
	public static final int SHIFT_RIGHT = 6;
	public static final int CHANGE_PLAYER = 7;
	public static final int NONE = 8;

	private int type;
	private Position position;

	public Action() {
	}

	public Action(int type, Position position) {
		this.type = type;
		this.position = position;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public String toString() {
		switch (type) {
		case Action.SELECT_PERSON:
			return "SELECT_PERSON, " + position.getX() + ", " + position.getY();
		case Action.PICK_EVIDENCE:
			return "PICK_EVIDENCE";
		case Action.SHIFT_DOWN:
			return "SHIFT_DOWN, " + position.getX() + ", " + position.getY();
		case Action.SHIFT_UP:
			return "SHIFT_UP, " + position.getX() + ", " + position.getY();
		case Action.SHIFT_LEFT:
			return "SHIFT_LEFT, " + position.getX() + ", " + position.getY();
		case Action.SHIFT_RIGHT:
			return "SHIFT_RIGHT, " + position.getX() + ", " + position.getY();
		case Action.END_TURN:
			return "END_TURN";
		case Action.NONE:
			return "NONE";
		default:
			return "";
		}
	}
}
