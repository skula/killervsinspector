package com.skula.killervsinspector.activities.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.skula.killervsinspector.services.Drawer;
import com.skula.killervsinspector.services.GameEngine;

public class BoardView extends View {
	private Paint paint;
	private Resources res;
	private Drawer drawer;
	private GameEngine ge;

	public BoardView(Context context) {
		super(context);
		this.ge = new GameEngine();
		this.res = context.getResources();
		this.drawer = new Drawer(res, ge);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		invalidate();
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		drawer.draw(canvas);
	}
}
