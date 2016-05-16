package com.cpxiao.lightsoff.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cpxiao.lightsoff.R;

public class HomeActivity extends Activity {

	private TextView mMoves;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		GameActivity.comeToMe(this);
	}
}
