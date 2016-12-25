package com.cpxiao.lightsoff.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.lib.activity.BaseActivity;
import com.cpxiao.lightsoff.mode.Extra;
import com.cpxiao.lightsoff.R;

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
        initFbAds50("1618817068448912_1618817565115529");
    }


    private void initWidget() {
        findViewById(R.id.btn_play).setOnClickListener(this);
        findViewById(R.id.btn_quit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_play) {
            int level = PreferencesUtils.getInt(this, Extra.KEY_LEVEL, Extra.VALUE_LEVEL_DEFAULT);
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
