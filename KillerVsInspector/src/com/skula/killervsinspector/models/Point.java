package com.skula.killervsinspector.models;

public class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point clone(int x, int y){
		return new Point(this.x + x, this.y + y);
	}

	public void move(int x, int y){
		this.x += x;
		this.y += y;
	}
	
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
}
