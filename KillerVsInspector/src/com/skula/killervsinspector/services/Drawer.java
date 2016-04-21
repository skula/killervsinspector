package com.skula.killervsinspector.services;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skula.killervsinspector.R;
import com.skula.killervsinspector.cnst.DrawAreas;
import com.skula.killervsinspector.cnst.PictureLibrary;
import com.skula.killervsinspector.cnst.TouchArea;
import com.skula.killervsinspector.models.Action;
import com.skula.killervsinspector.models.Board;
import com.skula.killervsinspector.models.Point;
import com.skula.killervsinspector.models.Position;

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
	private String log;

	public Drawer(Resources res, GameEngine engine) {
		this.log = "";
		this.engine = engine;
		this.paint = new Paint();
		this.lib = new PictureLibrary(res);
		Bitmap b = lib.get(R.drawable.barrin_offside);//156 208

		b = lib.get(R.drawable.shift_down); //107 107
		b=null;
	}

	public void draw(boolean waitForPlayer, Canvas c) {
		drawPict(c, R.drawable.background, DrawAreas.P0);
		drawPersons(waitForPlayer, c);

		if(!engine.isEndOfGame()){
			drawShiftButtons(c);
			if (!waitForPlayer ) {
				drawButtons(c);
			} else {
				drawWaitPlayerPanel(c);
			}
		}else{
			if(engine.getWinner() == GameEngine.TURN_KILLER){
				drawPict(c, R.drawable.killer_wins, DrawAreas.WINNER);
			}else{
				drawPict(c, R.drawable.inspector_wins, DrawAreas.WINNER);
			}
		}
		
		paint.setColor(Color.WHITE);
		paint.setTextSize(35f);
		c.drawText(log, DrawAreas.LOG.getX(), DrawAreas.LOG.getY(), paint);
	}

	private void drawWaitPlayerPanel(Canvas c) {
		if(engine.getToken() == GameEngine.TURN_INSPECTOR){
			drawPict(c, R.drawable.killer_turn, DrawAreas.NEXT_PLAYER_LABEL);
		}else{
			drawPict(c, R.drawable.inspector_turn, DrawAreas.NEXT_PLAYER_LABEL);
		}
	}

	public void drawPersons(boolean waitForPlayer, Canvas c) {
		Board b = engine.getBoard();
		for (int i = 0; i < b.getnRows(); i++) {
			for (int j = 0; j < b.getnColumns(); j++) {
				// draw card type
				drawPict(c, b.get(j, i).getDrawableId(), TouchArea.TILES[j][i]);

				// draw special effect
				if (b.get(j, i).isDeceased()) {
					drawPict(c, R.drawable.deceased, TouchArea.TILES[j][i]);
				}else if(b.get(j, i).isInnocent()) {
					drawPict(c, R.drawable.innocent, TouchArea.TILES[j][i]);
				}
				if (!waitForPlayer && engine.getToken() == GameEngine.TURN_INSPECTOR) {
					if (engine.isEvidence(b.getId(j, i))) {
						drawPict(c, R.drawable.evidence, TouchArea.TILES[j][i]);
					}
				}

				// draw player
				if (!waitForPlayer) {
					if (engine.getToken() == GameEngine.TURN_KILLER) {
						int aa = engine.getKillerId();
						if (b.getId(j, i) == engine.getKillerId()) {
							drawPict(c, R.drawable.player, TouchArea.TILES[j][i]);
						}
					} else {
						if (b.getId(j, i) == engine.getInspectorId()) {
							drawPict(c, R.drawable.player, TouchArea.TILES[j][i]);
						}
					}
				}
			}
		}

		// indice sur la position du tueur en cas d'exoneration
		if (engine.getToken() == GameEngine.TURN_INSPECTOR) {
			if (engine.hasClue()) {
				Position tmp = engine.getCluePosition();
				for (int j = -1; j <= 1; j++) {
					for (int i = -1; i <= 1; i++) {
						if (tmp.getX() + i >= 0 && tmp.getX() + i < b.getnColumns() && tmp.getY() + j >= 0 && tmp.getY() + j < b.getnRows()) {
							if (!(j == 0 && i == 0) && !b.get(tmp.getX() + i, tmp.getY() + j).isDeceased() && !b.get(tmp.getX() + i, tmp.getY() + j).isInnocent()) {
								drawPict(c, R.drawable.near_killer, TouchArea.TILES[tmp.getX() + i][tmp.getY() + j]);
							}
						}
					}
				}
			}
		}
		// indice sur la position de l'inspecteur
		else {
			if (engine.hasClue()) {
				Position tmp = engine.getCluePosition();
				for (int j = -1; j <= 1; j++) {
					for (int i = -1; i <= 1; i++) {
						if (tmp.getX() + i >= 0 && tmp.getX() + i < b.getnColumns() && tmp.getY() + j >= 0 && tmp.getY() + j < b.getnRows()) {
							if (!(j == 0 && i == 0) && !b.get(tmp.getX() + i, tmp.getY() + j).isDeceased() && !b.get(tmp.getX() + i, tmp.getY() + j).isInnocent()) {
								drawPict(c, R.drawable.near_inspector, TouchArea.TILES[tmp.getX() + i][tmp.getY() + j]);
							}
						}
					}
				}
			}
		}
	}

	public void drawShiftButtons(Canvas c) {
		Board b = engine.getBoard();
		Action lastAction = engine.getLastAction();

		for (int i = 0; i < b.getnColumns(); i++) {
			if (lastAction.getType() == Action.SHIFT_DOWN && lastAction.getPosition().getX() == i) {
				drawPict(c, R.drawable.shift_up_disabled, TouchArea.SHIFT_UP[i]);
			}else {
				drawPict(c, R.drawable.shift_up, TouchArea.SHIFT_UP[i]);
			}

			if (lastAction.getType() == Action.SHIFT_UP && lastAction.getPosition().getX() == i) {
				drawPict(c, R.drawable.shift_down_disabled, TouchArea.SHIFT_DOWN[i]);
			} else {
				drawPict(c, R.drawable.shift_down, TouchArea.SHIFT_DOWN[i]);
			}
		}

		for (int i = 0; i < b.getnRows(); i++) {
			if (lastAction.getType() == Action.SHIFT_RIGHT && lastAction.getPosition().getY() == i) {
				drawPict(c, R.drawable.shift_left_disabled, TouchArea.SHIFT_LEFT[i]);
			} else {
				drawPict(c, R.drawable.shift_left, TouchArea.SHIFT_LEFT[i]);
			}

			if (lastAction.getType() == Action.SHIFT_LEFT && lastAction.getPosition().getY() == i) {
				drawPict(c, R.drawable.shift_right_disabled, TouchArea.SHIFT_RIGHT[i]);
			} else {
				drawPict(c, R.drawable.shift_right, TouchArea.SHIFT_RIGHT[i]);
			}
		}
	}
	
	public void drawShiftButtons2(Canvas c) {
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
			}else {
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
				drawPict(c, R.drawable.btn_pick, DrawAreas.BUTTON_PICK);
			}
		} else {
			drawPict(c, R.drawable.btn_end, DrawAreas.BUTTON_END);
		}
	}
	
	public void setLog(String log){
		this.log = log;
	}

	private void drawPict(Canvas c, int id, Point p) {
		Bitmap bmp = lib.get(id);
		Rect src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
		Rect dest = new Rect(0 + p.getX(), 0 + p.getY(), bmp.getWidth() + p.getX(), bmp.getHeight() + p.getY());
		c.drawBitmap(bmp, src, dest, paint);
	}

	private void drawPict(Canvas c, int id, Rect dest) {
		Bitmap bmp = lib.get(id);
		Rect src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
		c.drawBitmap(bmp, src, dest, paint);
	}
}