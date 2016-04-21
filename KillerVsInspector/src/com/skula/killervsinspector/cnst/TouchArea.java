package com.skula.killervsinspector.cnst;

import android.graphics.Rect;

import com.skula.killervsinspector.models.Point;
import com.skula.killervsinspector.services.Drawer;

public class TouchArea {

	public static final Rect[][] TILES;
	public static final Rect[] SHIFT_UP;
	public static final Rect[] SHIFT_DOWN;
	public static final Rect[] SHIFT_RIGHT;
	public static final Rect[] SHIFT_LEFT;

	static {
		TILES = new Rect[5][5];
		int dx = Drawer.SEPARATOR;
		int x0 = DrawAreas.TILE_0_0.getX();
		int y0 = DrawAreas.TILE_0_0.getY();
		int pw = 156;
		int ph = 208;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				TILES[j][i] = new Rect(x0 + j*(dx + pw) , y0 + i*(dx + ph), x0 + j*(dx + pw) + pw, y0 + i*(dx + ph) + ph);
			}
		}
		
		SHIFT_UP = new Rect[5];
		SHIFT_DOWN = new Rect[5];
		int sw = 107;
		int sh = 107;
		x0 = DrawAreas.TILE_0_0.getX() + (pw - sw) / 2;
		y0 = DrawAreas.TILE_0_0.getY() - sw;
		int y02 = DrawAreas.TILE_0_0.getY() + 5 * ph + 4 * Drawer.SEPARATOR;
		for(int i=0;i<5;i++){
			SHIFT_UP[i] = new Rect(x0, y0, x0 + sw, y0 + sh);
			SHIFT_DOWN[i] = new Rect(x0, y02, x0 + sw, y02 + sh);
		}
		
		SHIFT_LEFT = new Rect[5];
		SHIFT_RIGHT = new Rect[5];
		y0 = DrawAreas.TILE_0_0.getY() + 40;
		x0 = DrawAreas.TILE_0_0.getX() - sw;
		int x02 = DrawAreas.TILE_0_0.getX() + 5 * pw + 4 * Drawer.SEPARATOR;
		for(int i=0;i<5;i++){
			SHIFT_LEFT[i] = new Rect(x0, y0, x0 + sw, y0 + sh);
			SHIFT_RIGHT[i] = new Rect(x02, y0, x02 + sw, y0 + sh);
		}
	}


	private static Rect createRect(Point p, int w, int h) {
		return new Rect(p.getX(), p.getY(), p.getX() + w, p.getY() + h);
	}
}
