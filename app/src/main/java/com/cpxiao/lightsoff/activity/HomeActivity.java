package com.cpxiao.lightsoff.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cpxiao.commonlibrary.utils.PreferencesUtils;
import com.cpxiao.lightsoff.ExtraKey;
import com.cpxiao.lightsoff.R;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		findViewById(R.id.btn_play).setOnClickListener(this);
		findViewById(R.id.btn_options).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.btn_play) {
			int level = PreferencesUtils.getInt(this, ExtraKey.KEY_LEVEL, ExtraKey.VALUE_LEVEL_DEFAULT);
			GameActivity.comeToMe(this, level);
		} else if (id == R.id.btn_options) {
			OptionsActivity.comeToMe(this);
		}
	}

	public static void comeToMe(Context context) {
		Intent intent = new Intent(context, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
}
