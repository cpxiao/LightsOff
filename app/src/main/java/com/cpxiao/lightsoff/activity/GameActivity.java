package com.cpxiao.lightsoff.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpxiao.commonlibrary.utils.PreferencesUtils;
import com.cpxiao.lightsoff.Data;
import com.cpxiao.lightsoff.ExtraKey;
import com.cpxiao.lightsoff.OnGameListener;
import com.cpxiao.lightsoff.R;
import com.cpxiao.lightsoff.views.GameView;

public class GameActivity extends BaseActivity implements OnGameListener, View.OnClickListener {
    private TextView mLevelView;
    private TextView mRemainingStepsView;

    private int mLevel;
    private int mRemainingSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        mLevel = getIntent().getIntExtra(ExtraKey.INTENT_EXTRA_LEVEL, ExtraKey.VALUE_LEVEL_DEFAULT);

        initWidget();
        initAds("1618817068448912_1618817468448872");
    }

    private void initWidget() {
        mLevelView = (TextView) findViewById(R.id.level);
        mLevelView.setText(getString(R.string.label_level) + mLevel);
        mRemainingStepsView = (TextView) findViewById(R.id.moves);
        mRemainingStepsView.setText(getString(R.string.label_moves) + String.valueOf(0));

        Button btnRestart = (Button) findViewById(R.id.btn_restart);
        btnRestart.setOnClickListener(this);
        Button btnHome = (Button) findViewById(R.id.btn_home);
        btnHome.setOnClickListener(this);


        initGameView();


    }

    private void initGameView() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_game);

        String[] data = Data.getData(mLevel);
        if (data == null) {
            mLevelView.setVisibility(View.GONE);
            mRemainingStepsView.setVisibility(View.GONE);
            TextView view = new TextView(this);
            view.setTextSize(60);
            view.setText(R.string.all_success);
            layout.addView(view);
        } else {
            mLevelView.setVisibility(View.VISIBLE);
            mRemainingStepsView.setVisibility(View.VISIBLE);
            int x = Data.getX(data);
            int y = Data.getY(data);
            int moves = Data.getMoves(data);
            String store = Data.getStore(data);

            GameView view = new GameView(this, x, y, moves, store);

            view.setOnGameListener(this);
            layout.addView(view);
        }
    }


    @Override
    public void onGameSuccess() {
        String key = ExtraKey.KEY_BEST_LEVEL_FORMAT + mLevel;
        PreferencesUtils.putInt(GameActivity.this, key, mRemainingSteps);

        mLevel++;
        PreferencesUtils.putInt(GameActivity.this, ExtraKey.KEY_LEVEL, mLevel);
        ResultActivity.comeToMe(GameActivity.this, mLevel);
        finish();
    }

    @Override
    public void onStepsChange(int moves) {
        mRemainingSteps = moves;
        mRemainingStepsView.setText(getString(R.string.label_moves) + String.valueOf(moves));
    }

    @Override
    public void onGameOver() {

    }

    public static void comeToMe(Context context, int level) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(ExtraKey.INTENT_EXTRA_LEVEL, level);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_restart) {
            GameActivity.comeToMe(this, mLevel);
        } else if (id == R.id.btn_home) {
            HomeActivity.comeToMe(this);
        }
    }
}
