package com.cpxiao.lightsoff.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cpxiao.lightsoff.ExtraKey;
import com.cpxiao.lightsoff.R;
import com.cpxiao.lightsoff.ads.core.ZAdPosition;

/**
 * ResultActivity
 *
 * @author cpxiao on 2016/5/16.
 */
public class ResultActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        initWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSmallAds(getApplicationContext(), ZAdPosition.POSITION_RESULT_ACTIVITY);
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
