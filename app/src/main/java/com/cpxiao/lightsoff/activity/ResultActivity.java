package com.cpxiao.lightsoff.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cpxiao.lightsoff.R;

/**
 * Created by cpxiao on 5/16/16.
 * ResultActivity
 */
public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_result);
	}

	public static void comeToMe(Context context) {
		Intent intent = new Intent(context, ResultActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
}
