package com.cpxiao.lightsoff.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cpxiao.lightsoff.ExtraKey;
import com.cpxiao.lightsoff.R;
import com.cpxiao.minigamelib.activity.BaseActivity;

/**
 * Created by cpxiao on 5/16/16.
 * ResultActivity
 */
public class ResultActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        initWidget();
        initSmallAds("1618817068448912_1619268745070411");
    }

    private void initWidget() {
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
        int mLevel = getIntent().getIntExtra(ExtraKey.INTENT_EXTRA_LEVEL, ExtraKey.VALUE_LEVEL_DEFAULT);
        GameActivity.comeToMe(this, mLevel);
        finish();
    }
}
