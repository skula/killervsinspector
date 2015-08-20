package com.skula.killervsinspector.services;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skula.killervsinspector.R;
import com.skula.killervsinspector.cnst.PictureLibrary;
import com.skula.killervsinspector.models.Board;
import com.skula.killervsinspector.models.Person;

public class Drawer {
	private static final int X0 = 80;
	private static final int Y0 = 80;

	private static final int SHIFR_WIDTH = 80;
	private static final int SHIFT_HEIGHT = 80;
	private static final Rect H_SHIFT_RECT = new Rect(0, 0, SHIFR_WIDTH, SHIFT_HEIGHT);
	private static final Rect V_SHIFT_RECT = new Rect(0, 0, SHIFT_HEIGHT, SHIFR_WIDTH);
	
	private static final int PERSON_WIDTH = 120;
	private static final int PERSON_HEIGHT = 160;
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
		drawEvidenceButton(c);
	}

	public void drawPersons(Canvas c) {
		Board b = engine.getBoard();
		Rect r = null;
		int x = X0;
		int y = Y0;
		int sep = 10;
		for (int i = 0; i < b.getnRows(); i++) {
			for (int j = 0; j < b.getnColumns(); j++) {
				r = new Rect(x, y, x +PERSON_WIDTH, y +PERSON_HEIGHT);
				int d = b.get(j, i).getDrawableId();
				
				// draw card type
				c.drawBitmap(lib.get(b.get(j, i).getDrawableId()), PERSON_RECT, r, paint);
				
				// draw special effect
				if(b.get(j, i).isDeceased()){
					c.drawBitmap(lib.get(R.drawable.deceased), PERSON_RECT, r, paint);
				}
				if(b.get(j, i).isDeceased()){
					c.drawBitmap(lib.get(R.drawable.innocent), PERSON_RECT, r, paint);
				}				
				if(engine.isEvidence(b.getId(j, i))){
					c.drawBitmap(lib.get(R.drawable.evidence), PERSON_RECT, r, paint);
				}
				
				// draw player
				if(engine.getToken() == GameEngine.TURN_KILLER){
					int aa = engine.getKillerId();
					if(b.getId(j, i) == engine.getKillerId()){
						c.drawBitmap(lib.get(R.drawable.player), PERSON_RECT, r, paint);
					}
				}else{
					if(b.getId(j, i) == engine.getInspectorId()){
						c.drawBitmap(lib.get(R.drawable.player), PERSON_RECT, r, paint);
					}
				}
				
				x += PERSON_WIDTH + sep;
			}
			y += PERSON_HEIGHT + sep;
			x = X0;
		}
	}

	public void drawShiftButtons(Canvas c) {
		Board b = engine.getBoard();
		int x = X0 + 20;
		int y1 = Y0 - 80;
		int y2 = Y0 + 840;
		int sep = 10 + 20 + 20;
		for(int i = 0; i< b.getnColumns(); i++){
			c.drawBitmap(lib.get(R.drawable.shift_up), H_SHIFT_RECT, new Rect(x, y1, x +SHIFR_WIDTH, y1 + SHIFT_HEIGHT), paint);
			c.drawBitmap(lib.get(R.drawable.shift_down), H_SHIFT_RECT, new Rect(x, y2, x +SHIFR_WIDTH, y2 + SHIFT_HEIGHT), paint);
			x+= SHIFR_WIDTH + sep;
		}
		
		y1 = Y0+40;
		x = X0 - 80;
		int x2 = X0 + 640;		
		sep = 10 + 40 + 40;
		for(int i = 0; i< b.getnRows(); i++){
			c.drawBitmap(lib.get(R.drawable.shift_left), V_SHIFT_RECT, new Rect(x, y1, x + SHIFT_HEIGHT, y1 + SHIFR_WIDTH), paint);
			c.drawBitmap(lib.get(R.drawable.shift_right), V_SHIFT_RECT, new Rect(x2, y1, x2 +SHIFT_HEIGHT, y1 + SHIFR_WIDTH), paint);
			y1+= SHIFR_WIDTH + sep;
		}
	}

	public void drawEvidenceButton(Canvas c) {
		
	}
}