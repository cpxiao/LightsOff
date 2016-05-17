package com.cpxiao.lightsoff.activity;

import android.os.Bundle;
import android.view.View;

import com.cpxiao.lightsoff.ExtraKey;
import com.cpxiao.lightsoff.R;
import com.cpxiao.utils.PreferencesUtils;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		findViewById(R.id.btn_play).setOnClickListener(this);
		findViewById(R.id.btn_menu).setOnClickListener(this);
		findViewById(R.id.btn_options).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		int level = PreferencesUtils.getInt(this, ExtraKey.KEY_LEVEL, ExtraKey.VALUE_LEVEL_DEFAULT);
		if (id == R.id.btn_play) {
			GameActivity.comeToMe(this, level);
		} else if (id == R.id.btn_menu) {
			MenuActivity.comeToMe(this, level);
		} else if (id == R.id.btn_options) {
			OptionsActivity.comeToMe(this);
		}
	}
}
