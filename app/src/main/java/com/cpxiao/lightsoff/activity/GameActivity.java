package com.cpxiao.lightsoff.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpxiao.lightsoff.Data;
import com.cpxiao.lightsoff.ExtraKey;
import com.cpxiao.lightsoff.OnGameListener;
import com.cpxiao.lightsoff.R;
import com.cpxiao.lightsoff.views.GameView;
import com.cpxiao.utils.PreferencesUtils;

public class GameActivity extends BaseActivity implements OnGameListener {
	private TextView mLevelView;
	private TextView mMovesView;
	private TextView mBestView;

	private int mLevel;
	private int mMoves;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_game);

		mLevel = getIntent().getIntExtra(ExtraKey.INTENT_EXTRA_LEVEL, ExtraKey.VALUE_LEVEL_DEFAULT);
		initWidget();

	}


	private void initWidget() {
		mLevelView = (TextView) findViewById(R.id.level);
		mLevelView.setText(formatLevel(getApplicationContext(), mLevel));

		mMovesView = (TextView) findViewById(R.id.moves);
		mMovesView.setText(getString(R.string.label_moves) + String.valueOf(0));

		mBestView = (TextView) findViewById(R.id.best);
		String key = ExtraKey.KEY_BEST_LEVEL_FORMAT + mLevel;
		int best = PreferencesUtils.getInt(this, key, ExtraKey.VALUE_BEST_DEFAULT);
		mBestView.setText(getString(R.string.label_best) + String.valueOf(best));

		initGameView();
	}

	private void initGameView() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout_game);
//		GameView view = new GameView(this, 4, 4);

// 		int[] store = {1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1};
//		GameView view = new GameView(this, 4, store);

		String store = Data.getStore(mLevel);
		if (store == null) {
			mBestView.setVisibility(View.GONE);
			mLevelView.setVisibility(View.GONE);
			mMovesView.setVisibility(View.GONE);
			TextView view = new TextView(this);
			view.setTextSize(60);
			view.setText("恭喜你，已完成全部关卡！\n敬请期待");
			layout.addView(view);
		} else {
			mBestView.setVisibility(View.VISIBLE);
			mLevelView.setVisibility(View.VISIBLE);
			mMovesView.setVisibility(View.VISIBLE);
			int gameType = (int) Math.sqrt(store.length());
			GameView view = new GameView(this, gameType, store);

			view.setOnGameListener(this);
			layout.addView(view);
		}
	}


	@Override
	public void onGameSuccess() {
		String key = ExtraKey.KEY_BEST_LEVEL_FORMAT + mLevel;
		PreferencesUtils.putInt(GameActivity.this, key, mMoves);

		mLevel++;
		PreferencesUtils.putInt(GameActivity.this, ExtraKey.KEY_LEVEL, mLevel);
		ResultActivity.comeToMe(GameActivity.this, mLevel);
	}

	@Override
	public void onMovesChange(int moves) {
		mMoves = moves;
		mMovesView.setText(getString(R.string.label_moves) + String.valueOf(moves));
	}

	public static void comeToMe(Context context, int level) {
		Intent intent = new Intent(context, GameActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(ExtraKey.INTENT_EXTRA_LEVEL, level);
		context.startActivity(intent);
	}
}
