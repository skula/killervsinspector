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

	public BoardView(Context context) {
		super(context);
		this.ge = new GameEngine();
		this.res = context.getResources();
		this.drawer = new Drawer(res, ge);
		this.msg = "";
		this.paint = new Paint();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Action act = getAction(x, y);
			if (act != null) {
				msg = act.toString();
			} else {
				msg = "VOID";
			}
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		invalidate();
		return true;
	}

	public Action getAction(int x, int y) {

		return null;
	}

	public Position getArea(int x, int y) {
		Board b = ge.getBoard();
		Rect r = null;
		int x1 = Drawer.X0;
		int y1 = Drawer.Y0;
		for (int i = 0; i < b.getnRows(); i++) {
			for (int j = 0; j < b.getnColumns(); j++) {
				r = new Rect(x1, y1, x1 + Drawer.PERSON_WIDTH, y1 + Drawer.PERSON_HEIGHT);
				if (r.contains(x, y)) {
					return new Position(j, i);
				}
			}
		}
		return null;
	}

	@Override
	public void draw(Canvas canvas) {
		drawer.draw(canvas);
		paint.setColor(Color.RED);
		canvas.drawText(msg, 10, 10, paint);
	}
}
