package com.skula.killervsinspector.services;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skula.killervsinspector.R;
import com.skula.killervsinspector.cnst.PictureLibrary;
import com.skula.killervsinspector.models.Action;
import com.skula.killervsinspector.models.Board;

public class Drawer {
	public static final int SEPARATOR = 20;
	public static final int SHIFT_WIDTH = 71;
	public static final int SHIFT_HEIGHT = 71;
	private static final Rect H_SHIFT_RECT = new Rect(0, 0, SHIFT_WIDTH, SHIFT_HEIGHT);
	private static final Rect V_SHIFT_RECT = new Rect(0, 0, SHIFT_HEIGHT, SHIFT_WIDTH);

	public static final int PERSON_WIDTH = 107;
	public static final int PERSON_HEIGHT = 142;

	public static final int BUTTON_WIDTH = 142;
	public static final int BUTTON_HEIGHT = 142;

	public static final int X0 = (800 - (5 * PERSON_WIDTH + 4 * SEPARATOR + 2 * SHIFT_WIDTH)) / 2 + SHIFT_WIDTH;
	public static final int Y0 = 120;

	private static final Rect PERSON_RECT = new Rect(0, 0, PERSON_WIDTH, PERSON_HEIGHT);
	private PictureLibrary lib;
	private Paint paint;
	private GameEngine engine;

	public Drawer(Resources res, GameEngine engine) {
		this.engine = engine;
		this.paint = new Paint();
		this.lib = new PictureLibrary(res);
	}

	public void draw(boolean waitForPlayer, Canvas c) {
		paint.setColor(Color.WHITE);
		c.drawRect(new Rect(0, 0, 800, 1200), paint);
		drawPersons(waitForPlayer, c);

		if (!waitForPlayer) {
			drawShiftButtons(c);
			drawButtons(c);
		} else {
			drawWaitPlayerPanel(c);
		}

		paint.setColor(Color.RED);
		paint.setTextSize(30f);
		int w = lib.get(R.drawable.btn_pick).getWidth();
		int h = lib.get(R.drawable.btn_pick).getHeight();

		if (engine.getToken() == GameEngine.TURN_INSPECTOR) {
			c.drawText("INSPECTEUR", 400, 40, paint);
		} else {
			c.drawText("TUEUR", 400, 40, paint);
		}
	}

	public void drawWaitPlayerPanel(Canvas c) {
		paint.setColor(Color.RED);
		c.drawRect(new Rect(320, 1000, 480, 1160), paint);
	}

	public void drawPersons(boolean waitForPlayer, Canvas c) {
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
				if (b.get(j, i).isInnocent()) {
					c.drawBitmap(lib.get(R.drawable.innocent), PERSON_RECT, r, paint);
				}
				if (!waitForPlayer && engine.getToken() == GameEngine.TURN_INSPECTOR) {
					if (engine.isEvidence(b.getId(j, i))) {
						c.drawBitmap(lib.get(R.drawable.evidence), PERSON_RECT, r, paint);
					}
				}

				// draw player
				if (!waitForPlayer) {
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
		Action lastAction = engine.getLastAction();

		for (int i = 0; i < b.getnColumns(); i++) {
			if (lastAction.getType() == Action.SHIFT_DOWN && lastAction.getPosition().getX() == i) {
				c.drawBitmap(lib.get(R.drawable.shift_up_disabled), H_SHIFT_RECT, new Rect(x, y1, x + SHIFT_WIDTH, y1
						+ SHIFT_HEIGHT), paint);
			} else {
				c.drawBitmap(lib.get(R.drawable.shift_up), H_SHIFT_RECT, new Rect(x, y1, x + SHIFT_WIDTH, y1
						+ SHIFT_HEIGHT), paint);
			}

			if (lastAction.getType() == Action.SHIFT_UP && lastAction.getPosition().getX() == i) {
				c.drawBitmap(lib.get(R.drawable.shift_down_disabled), H_SHIFT_RECT, new Rect(x, y2, x + SHIFT_WIDTH, y2
						+ SHIFT_HEIGHT), paint);
			} else {
				c.drawBitmap(lib.get(R.drawable.shift_down), H_SHIFT_RECT, new Rect(x, y2, x + SHIFT_WIDTH, y2
						+ SHIFT_HEIGHT), paint);
			}
			x += sep;
		}

		y1 = Y0 + 40;
		x = X0 - SHIFT_WIDTH;
		int x2 = X0 + 5 * PERSON_WIDTH + 4 * SEPARATOR;
		sep = PERSON_HEIGHT + SEPARATOR;
		for (int i = 0; i < b.getnRows(); i++) {
			if (lastAction.getType() == Action.SHIFT_RIGHT && lastAction.getPosition().getY() == i) {
				c.drawBitmap(lib.get(R.drawable.shift_left_disabled), V_SHIFT_RECT, new Rect(x, y1, x + SHIFT_HEIGHT,
						y1 + SHIFT_WIDTH), paint);
			} else {
				c.drawBitmap(lib.get(R.drawable.shift_left), V_SHIFT_RECT, new Rect(x, y1, x + SHIFT_HEIGHT, y1
						+ SHIFT_WIDTH), paint);
			}

			if (lastAction.getType() == Action.SHIFT_LEFT && lastAction.getPosition().getY() == i) {
				c.drawBitmap(lib.get(R.drawable.shift_right_disabled), V_SHIFT_RECT, new Rect(x2, y1,
						x2 + SHIFT_HEIGHT, y1 + SHIFT_WIDTH), paint);
			} else {
				c.drawBitmap(lib.get(R.drawable.shift_right), V_SHIFT_RECT, new Rect(x2, y1, x2 + SHIFT_HEIGHT, y1
						+ SHIFT_WIDTH), paint);
			}
			y1 += sep;
		}
	}

	public void drawButtons(Canvas c) {
		if (!engine.isEndOfTurn()) {
			if (!engine.isFirstTurn()) {
				c.drawBitmap(lib.get(R.drawable.btn_pick), new Rect(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT), new Rect(
						SHIFT_WIDTH, 1000, SHIFT_WIDTH + BUTTON_WIDTH, 1000 + BUTTON_HEIGHT), paint);
			}
		} else {
			c.drawBitmap(lib.get(R.drawable.btn_end), new Rect(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT), new Rect(800
					- SHIFT_WIDTH - BUTTON_WIDTH, 1000, 800 - SHIFT_WIDTH, 1000 + BUTTON_HEIGHT), paint);
		}
	}
}