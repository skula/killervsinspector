package com.skula.killervsinspector.activities.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.skula.killervsinspector.models.Action;
import com.skula.killervsinspector.models.Board;
import com.skula.killervsinspector.models.Position;
import com.skula.killervsinspector.services.Drawer;
import com.skula.killervsinspector.services.GameEngine;

public class BoardView extends View {
	private Paint paint;
	private Resources res;
	private Drawer drawer;
	private GameEngine ge;
	private String msg;
	private boolean waitForPlayer;

	public BoardView(Context context) {
		super(context);
		this.ge = new GameEngine();
		this.res = context.getResources();
		this.drawer = new Drawer(res, ge);
		this.msg = "";
		this.paint = new Paint();
		this.waitForPlayer = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Action a = getAction(x, y);
			ge.setAction(a);
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			if(!ge.isEndOfGame()){
				if (ge.getAction().getType() == Action.END_TURN) {
					waitForPlayer = true;
				} else if (ge.getAction().getType() == Action.CHANGE_PLAYER) {
					ge.nextPlayer();
					waitForPlayer = false;
					if(ge.getInspectorId() == -1){
						ge.buildEvidenceHand();
					}
				} else if (ge.getAction().getType() != Action.NONE) {
					drawer.setLog("");
					if(ge.canProcess()){
						drawer.setLog(ge.process());
					}
				}
			}
			break;
		}
		invalidate();
		return true;
	}

	private Action getAction(int x, int y) {
		Action res = new Action();
		Board b = ge.getBoard();
		Rect r = null;
		int x1 = Drawer.X0;
		int y1 = Drawer.Y0;

		if (!ge.isEndOfTurn()) {
			// personnages
			for (int i = 0; i < b.getnRows(); i++) {
				for (int j = 0; j < b.getnColumns(); j++) {
					r = new Rect(x1, y1, x1 + Drawer.PERSON_WIDTH, y1 + Drawer.PERSON_HEIGHT);
					if (r.contains(x, y)) {
						res.setType(Action.SELECT_PERSON);
						res.setPosition(new Position(j, i));
						return res;
					}
					x1 += Drawer.PERSON_WIDTH + Drawer.SEPARATOR;
				}
				y1 += Drawer.PERSON_HEIGHT + Drawer.SEPARATOR;
				x1 = Drawer.X0;
			}

			// deplacements vertical de colonnes
			x1 = Drawer.X0 + (Drawer.PERSON_WIDTH - Drawer.SHIFT_WIDTH) / 2;
			y1 = Drawer.Y0 - Drawer.SHIFT_WIDTH;
			int y2 = Drawer.Y0 + 5 * Drawer.PERSON_HEIGHT + 4 * Drawer.SEPARATOR;
			int sep = Drawer.PERSON_WIDTH + Drawer.SEPARATOR;
			for (int i = 0; i < b.getnColumns(); i++) {
				r = new Rect(x1, y1, x1 + Drawer.SHIFT_WIDTH, y1 + Drawer.SHIFT_HEIGHT);
				if (r.contains(x, y)) {
					res.setType(Action.SHIFT_UP);
					res.setPosition(new Position(i, 0));
					return res;
				}
				r = new Rect(x1, y2, x1 + Drawer.SHIFT_WIDTH, y2 + Drawer.SHIFT_HEIGHT);
				if (r.contains(x, y)) {
					res.setType(Action.SHIFT_DOWN);
					res.setPosition(new Position(i, 0));
					return res;
				}
				x1 += sep;
			}

			// deplacements horizontal de colonnes
			y1 = Drawer.Y0 + 40;
			x1 = Drawer.X0 - Drawer.SHIFT_WIDTH;
			int x2 = Drawer.X0 + 5 * Drawer.PERSON_WIDTH + 4 * Drawer.SEPARATOR;
			sep = Drawer.PERSON_HEIGHT + Drawer.SEPARATOR;
			for (int i = 0; i < b.getnRows(); i++) {
				r = new Rect(x1, y1, x1 + Drawer.SHIFT_HEIGHT, y1 + Drawer.SHIFT_WIDTH);
				if (r.contains(x, y)) {
					res.setType(Action.SHIFT_LEFT);
					res.setPosition(new Position(0, i));
					return res;
				}
				r = new Rect(x2, y1, x2 + Drawer.SHIFT_HEIGHT, y1 + Drawer.SHIFT_WIDTH);
				if (r.contains(x, y)) {
					res.setType(Action.SHIFT_RIGHT);
					res.setPosition(new Position(0, i));
					return res;
				}
				y1 += sep;
			}

			// piocher une carte
			r = new Rect(Drawer.SHIFT_WIDTH, 1000, Drawer.SHIFT_WIDTH + Drawer.BUTTON_WIDTH, 1000 + Drawer.BUTTON_HEIGHT);
			if (r.contains(x, y)) {
				res.setType(Action.PICK_EVIDENCE);
				res.setPosition(null);
				return res;
			}
		} else {
			// finir le tour
			r = new Rect(800 - Drawer.SHIFT_WIDTH - Drawer.BUTTON_WIDTH, 1000, 800 - Drawer.SHIFT_WIDTH,
					1000 + Drawer.BUTTON_HEIGHT);
			if (r.contains(x, y)) {
				res.setType(Action.END_TURN);
				res.setPosition(null);
				return res;
			}
		}

		// changement de joueur
		if(waitForPlayer){
			r = new Rect(325, 970, 467, 1112);
			if (r.contains(x, y)) {
				res.setType(Action.CHANGE_PLAYER);
				res.setPosition(null);
				return res;
			}
		}
		// TODO: image de changement de joueur

		res.setType(Action.NONE);
		return res;
	}

	@Override
	public void draw(Canvas canvas) {
		drawer.draw(waitForPlayer, canvas);
	}
}
