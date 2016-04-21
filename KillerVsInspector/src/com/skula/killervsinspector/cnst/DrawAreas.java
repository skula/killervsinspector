package com.skula.killervsinspector.cnst;

import com.skula.killervsinspector.models.Point;

public class DrawAreas {
	public static final Point P0 = new Point(20, 0);
	public static final Point TILE_0_0 = new Point(P0.getX() + 100, P0.getY()+100);

	public static final Point NEXT_PLAYER_LABEL = new Point(325, 970);
	public static final Point LOG = new Point(30, 1180);
	public static final Point WINNER = new Point(325, 970);
	
	public static final Point BUTTON_PICK = new Point(325, 970);
	public static final Point BUTTON_END = new Point(325, 970);
}
