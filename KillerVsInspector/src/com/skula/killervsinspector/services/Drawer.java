package com.skula.killervsinspector.services;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skula.killervsinspector.R;
import com.skula.killervsinspector.cnst.PictureLibrary;
import com.skula.killervsinspector.models.Board;

public class Drawer {
	private static final int SEPARATOR = 20;
	private static final int SHIFT_WIDTH = 71;
	private static final int SHIFT_HEIGHT = 71;
	private static final Rect H_SHIFT_RECT = new Rect(0, 0, SHIFT_WIDTH, SHIFT_HEIGHT);
	private static final Rect V_SHIFT_RECT = new Rect(0, 0, SHIFT_HEIGHT, SHIFT_WIDTH);

	private static final int PERSON_WIDTH = 107;
	private static final int PERSON_HEIGHT = 142;
	
	private static final int X0 = (800 - (5 * PERSON_WIDTH + 4 * SEPARATOR + 2 * SHIFT_WIDTH)) / 2 + SHIFT_WIDTH;
	private static final int Y0 = 120;

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
		paint.setColor(Color.WHITE);
		c.drawRect(new Rect(0, 0, 800, 1200), paint);
		drawPersons(c);
		drawShiftButtons(c);
		drawButtons(c);

		paint.setColor(Color.RED);
		paint.setTextSize(30f);
		int w = lib.get(R.drawable.alyss_suspect).getWidth();
		int h = lib.get(R.drawable.alyss_suspect).getHeight();
		c.drawText("" + X0, 40, 40, paint);
	}

	public void drawPersons(Canvas c) {
		Board b = engine.getBoard();
		Rect r = null;
		int x = X0;
		int y = Y0;
		for (int i = 0; i < b.getnRows(); i++) {
			for (int j = 0; j < b.getnColumns(); j++) {
				r = new Rect(x, y, x + PERSON_WIDTH, y + PERSON_HEIGHT);
				int d = b.get(j, i).getDrawableId();

				// draw card type
				c.drawBitmap(lib.get(b.get(j, i).getDrawableId()), PERSON_RECT, r, paint);

				// draw special effect
				if (b.get(j, i).isDeceased()) {
					c.drawBitmap(lib.get(R.drawable.deceased), PERSON_RECT, r, paint);
				}
				if (b.get(j, i).isDeceased()) {
					c.drawBitmap(lib.get(R.drawable.innocent), PERSON_RECT, r, paint);
				}
				if (engine.isEvidence(b.getId(j, i))) {
					c.drawBitmap(lib.get(R.drawable.evidence), PERSON_RECT, r, paint);
				}

				// draw player
				if (engine.getToken() == GameEngine.TURN_KILLER) {
					int aa = engine.getKillerId();
					if (b.getId(j, i) == engine.getKillerId()) {
						c.drawBitmap(lib.get(R.drawable.player), PERSON_RECT, r, paint);
					}
				} else {
					if (b.getId(j, i) == engine.getInspectorId()) {
						c.drawBitmap(lib.get(R.drawable.player), PERSON_RECT, r, paint);
					}
				}

				x += PERSON_WIDTH + SEPARATOR;
			}
			y += PERSON_HEIGHT + SEPARATOR;
			x = X0;
		}
	}

	public void drawShiftButtons(Canvas c) {
		Board b = engine.getBoard();
		int x = X0 + (PERSON_WIDTH - SHIFT_WIDTH) / 2;
		int y1 = Y0 - SHIFT_WIDTH;
		int y2 = Y0 + 5 * PERSON_HEIGHT + 4 * SEPARATOR;
		int sep = PERSON_WIDTH + SEPARATOR;
		for (int i = 0; i < b.getnColumns(); i++) {
			c.drawBitmap(lib.get(R.drawable.shift_up), H_SHIFT_RECT,
					new Rect(x, y1, x + SHIFT_WIDTH, y1 + SHIFT_HEIGHT), paint);
			c.drawBitmap(lib.get(R.drawable.shift_down), H_SHIFT_RECT, new Rect(x, y2, x + SHIFT_WIDTH, y2
					+ SHIFT_HEIGHT), paint);
			x += sep;
		}

		y1 = Y0 + 40;
		x = X0 - SHIFT_WIDTH;
		int x2 = X0 + 5 * PERSON_WIDTH + 4 * SEPARATOR;
		;
		sep = PERSON_HEIGHT + SEPARATOR;
		for (int i = 0; i < b.getnRows(); i++) {
			c.drawBitmap(lib.get(R.drawable.shift_left), V_SHIFT_RECT, new Rect(x, y1, x + SHIFT_HEIGHT, y1
					+ SHIFT_WIDTH), paint);
			c.drawBitmap(lib.get(R.drawable.shift_right), V_SHIFT_RECT, new Rect(x2, y1, x2 + SHIFT_HEIGHT, y1
					+ SHIFT_WIDTH), paint);
			y1 += sep;
		}
	}

	public void drawButtons(Canvas c) {
		c.drawBitmap(lib.get(R.drawable.btn_pick), new Rect(0, 0, 142, 142), new Rect(80, 1000, 80 + 142, 1000 + 142),
				paint);
		c.drawBitmap(lib.get(R.drawable.btn_end), new Rect(0, 0, 142, 142), new Rect(600, 1000, 600 + 142, 1000 + 142),
				paint);
	}
}