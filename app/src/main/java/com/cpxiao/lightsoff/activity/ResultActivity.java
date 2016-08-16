package com.cpxiao.lightsoff.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpxiao.lightsoff.ExtraKey;
import com.cpxiao.lightsoff.R;

/**
 * Created by cpxiao on 5/16/16.
 * ResultActivity
 */
public class ResultActivity extends BaseActivity implements View.OnClickListener {

    private int mLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        initWidget();
        initAds("1618817068448912_1619268745070411");
    }

    private void initWidget() {
        TextView mLevelView = (TextView) findViewById(R.id.level);
        mLevel = getIntent().getIntExtra(ExtraKey.INTENT_EXTRA_LEVEL, ExtraKey.VALUE_LEVEL_DEFAULT);

        mLevelView.setText("" + mLevel);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_result);
        layout.setOnClickListener(this);
    }

    public static void comeToMe(Context context, int level) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(ExtraKey.INTENT_EXTRA_LEVEL, level);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        GameActivity.comeToMe(this, mLevel);
        finish();
    }
}
