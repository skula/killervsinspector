package com.skula.killervsinspector.activities;

import android.app.Activity;
import android.os.Bundle;

import com.skula.killervsinspector.activities.views.BoardView;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(new BoardView(this));
	}
}
