package com.cpxiao.lightsoff.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.lib.activity.BaseActivity;
import com.cpxiao.lightsoff.mode.Data;
import com.cpxiao.lightsoff.mode.Extra;
import com.cpxiao.lightsoff.imps.OnGameListener;
import com.cpxiao.lightsoff.R;
import com.cpxiao.lightsoff.views.GameView;


/**
 * GameActivity
 *
 * @author cpxiao on 2016/5/16.
 */
public class GameActivity extends BaseActivity implements OnGameListener, View.OnClickListener {
    private TextView mLevelView;
    private TextView mRemainingStepsView;

    private int mLevel;
    private int mRemainingSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        mLevel = getIntent().getIntExtra(Extra.INTENT_EXTRA_LEVEL, Extra.VALUE_LEVEL_DEFAULT);

        initWidget();
        initFbAds50("1618817068448912_1618817468448872");
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
        GameView view;
        String[] data = Data.getData(mLevel);
        if (data == null) {

            if (mLevel < 20) {
                view = new GameView(this, 4, 5, 6, null);
            } else if (mLevel < 40) {
                view = new GameView(this, 4, 5, 8, null);
            } else if (mLevel < 60) {
                view = new GameView(this, 4, 6, 6, null);
            } else if (mLevel < 80) {
                view = new GameView(this, 4, 6, 8, null);
            } else if (mLevel < 100) {
                view = new GameView(this, 4, 6, 6, null);
            } else {
                view = new GameView(this, 5, 6, 8, null);
            }
        } else {
            int x = Data.getX(data);
            int y = Data.getY(data);
            int moves = Data.getMoves(data);
            String store = Data.getStore(data);
            view = new GameView(this, x, y, moves, store);
        }
        view.setOnGameListener(this);
        layout.addView(view);
    }


    @Override
    public void onGameSuccess() {
        String key = Extra.KEY_BEST_LEVEL_FORMAT + mLevel;
        PreferencesUtils.putInt(GameActivity.this, key, mRemainingSteps);

        mLevel++;
        PreferencesUtils.putInt(GameActivity.this, Extra.KEY_LEVEL, mLevel);
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
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Extra.INTENT_EXTRA_LEVEL, level);
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
