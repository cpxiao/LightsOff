package com.cpxiao.lightsoff.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpxiao.lightsoff.OnGameListener;
import com.cpxiao.lightsoff.views.GameView;

public class HomeActivity extends Activity implements OnGameListener {

	private TextView mMoves;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_home);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		mMoves = new TextView(this);
		mMoves.setText("aaaaa");
		layout.addView(mMoves);

		int[] store = {1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1};
		GameView view = new GameView(this, 4, store);
		view.setOnGameListener(this);
		layout.addView(view);
		setContentView(layout);
	}

	@Override
	public void onGameSuccess() {

	}

	@Override
	public void onMovesChange(int moves) {
		mMoves.setText(String.valueOf(moves));
	}
}
