package com.cpxiao.lightsoff.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cpxiao.minigamelib.activity.BaseActivity;

/**
 * Created by cpxiao on 5/17/16.
 * SettingsActivity
 */
public class SettingsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public static void comeToMe(Context context) {
		Intent intent = new Intent(context, SettingsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

}
