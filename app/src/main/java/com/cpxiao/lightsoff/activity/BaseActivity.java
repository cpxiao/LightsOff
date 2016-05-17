package com.cpxiao.lightsoff.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.cpxiao.lightsoff.R;

/**
 * Created by cpxiao on 5/16/16.
 * BaseActivity
 */
public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * 格式化level，用于显示
	 *
	 * @param context context
	 * @param level   level
	 * @return String
	 */
	public String formatLevel(Context context, int level) {
		if (level <= 0) {
			level = 1;
		}
		return context.getString(R.string.label_level_format) + (level / 16 + 1) + "-" + level % 16;
	}
}
