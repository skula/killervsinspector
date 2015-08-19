package com.skula.killervsinspector.services;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skula.killervsinspector.R;
import com.skula.killervsinspector.cnst.PictureLibrary;
import com.skula.killervsinspector.models.Board;
import com.skula.killervsinspector.models.Person;

public class Drawer {
	private static final int X0 = 50;
	private static final int Y0 = 50;

	private static final int PERSON_WIDTH = 150;
	private static final int PERSON_HEIGHT = 80;
	private static final Rect PERSON_RECT = new Rect(0, 0, PERSON_WIDTH, PERSON_HEIGHT);
	private PictureLibrary lib;
	private Paint paint;
	private GameEngine engine;

	public Drawer(Resources res, GameEngine engine) {
		this.engine = engine;
		this.paint = new Paint();
		this.lib = new PictureLibrary(res);
	}

	public void draw(Canvas c) {
		drawPersons(c);
		drawShiftButtons(c);
		drawEvidences(c);
		drawPlayer(c);
	}

	public void drawPersons(Canvas c) {
		Board b = engine.getBoard();
		Rect r = null;
		int x = X0;
		int y = Y0;
		int sep = 20;
		for (int i = 0; i < b.getnColumns(); i++) {
			for (int j = 0; j < b.getnRows(); j++) {
				r = new Rect(x, y, PERSON_WIDTH, PERSON_HEIGHT);
				int d = b.get(j, i).getDrawableId();
				c.drawBitmap(lib.get(b.get(j, i).getDrawableId()), PERSON_RECT, r, paint);
				switch (b.get(j, i).getState()) {
				case Person.INNOCENT:
					c.drawBitmap(lib.get(R.drawable.innocent), PERSON_RECT, r, paint);
					break;
				case Person.DECEASED:
					c.drawBitmap(lib.get(R.drawable.deceased), PERSON_RECT, r, paint);
					break;
				}
				x += PERSON_WIDTH + sep;
			}
			y += PERSON_HEIGHT + sep;
			x = X0;
		}
	}

	public void drawShiftButtons(Canvas c) {

	}

	public void drawEvidences(Canvas c) {

	}

	public void drawPlayer(Canvas c) {

	}
}