package com.cpxiao.lightsoff.activity;

import android.content.Context;
import android.content.Intent;

import com.cpxiao.lightsoff.ExtraKey;

/**
 * Created by cpxiao on 5/17/16.
 * MenuActivity
 */
public class MenuActivity extends BaseActivity {

	public static void comeToMe(Context context, int level) {
		Intent intent = new Intent(context, MenuActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(ExtraKey.INTENT_EXTRA_LEVEL, level);
		context.startActivity(intent);
	}
}
