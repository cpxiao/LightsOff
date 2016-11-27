package com.cpxiao.lightsoff.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.lightsoff.ExtraKey;
import com.cpxiao.lightsoff.R;
import com.cpxiao.lightsoff.ads.ZAdManager;
import com.cpxiao.lightsoff.ads.core.ZAdPosition;

/**
 * HomeActivity
 *
 * @author cpxiao on 2016/5/16.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initWidget();

        ZAdManager.getInstance().init(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSmallAds(getApplicationContext(), ZAdPosition.POSITION_HOME_ACTIVITY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZAdManager.getInstance().destroyAll();
    }

    private void initWidget() {
        findViewById(R.id.btn_play).setOnClickListener(this);
        findViewById(R.id.btn_settings).setOnClickListener(this);
        findViewById(R.id.btn_quit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_play) {
            int level = PreferencesUtils.getInt(this, ExtraKey.KEY_LEVEL, ExtraKey.VALUE_LEVEL_DEFAULT);
            GameActivity.comeToMe(this, level);
        } else if (id == R.id.btn_quit) {
            finish();
        }
    }

    public static void comeToMe(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
