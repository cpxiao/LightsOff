package com.cpxiao.lightsoff.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpxiao.lightsoff.OnGameListener;
import com.cpxiao.lightsoff.R;
import com.cpxiao.lightsoff.views.GameView;

public class GameActivity extends Activity implements OnGameListener {

	private TextView MLevel;
	private TextView mMoves;
	private TextView mBest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_game);
		initWidget();
	}

	private void initWidget() {
		MLevel = (TextView) findViewById(R.id.level);

		mMoves = (TextView) findViewById(R.id.moves);

		mBest = (TextView) findViewById(R.id.best);


		LinearLayout layout = (LinearLayout) findViewById(R.id.layout_game);
		GameView view = new GameView(this, 4, 4);

// 		int[] store = {1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1};
//		GameView view = new GameView(this, 4, store);

//		String store = "1111100110011111";
//		GameView view = new GameView(this, 4, store);

		view.setOnGameListener(this);
		layout.addView(view);
	}

	@Override
	public void onGameSuccess() {
		ResultActivity.comeToMe(GameActivity.this);
	}

	@Override
	public void onMovesChange(int moves) {
		mMoves.setText(String.valueOf(moves));
	}

	public static void comeToMe(Context context) {
		Intent intent = new Intent(context, GameActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
}
