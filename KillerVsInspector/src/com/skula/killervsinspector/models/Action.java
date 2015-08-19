package com.skula.killervsinspector.models;


public class Action {
	public static final int SELECT_PERSON = 0;
	public static final int PICK_EVIDENCE = 1;

	public static final int SHIFT_UP = 2;
	public static final int SHIFT_DOWN = 3;
	public static final int SHIFT_LEFT = 4;
	public static final int SHIFT_RIGHT = 5;

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
}
