package com.cpxiao.lightsoff.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cpxiao.lightsoff.R;

/**
 * Created by cpxiao on 5/18/16.
 */
public class MenuView extends ViewGroup {

	public MenuView(Context context, String[] data) {
		super(context);
		LayoutInflater.from(getContext()).inflate(R.layout.activity_game, null);
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

	}
}
