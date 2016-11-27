package com.cpxiao.lightsoff.activity;

import android.app.Activity;
import android.os.Bundle;

import com.cpxiao.lightsoff.ads.ZAdManager;

/**
 * @author cpxiao on 2016/11/27.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZAdManager.getInstance().init(getApplicationContext());
        HomeActivity.comeToMe(this);
        finish();
    }
}
