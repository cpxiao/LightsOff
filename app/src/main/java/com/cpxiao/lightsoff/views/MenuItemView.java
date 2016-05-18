package com.cpxiao.lightsoff.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.cpxiao.lightsoff.R;

/**
 * Created by cpxiao on 5/18/16.
 * MenuItemView
 */
public class MenuItemView extends View {
	private boolean isClock;
	private int mBest;

	private Bitmap mBitmapClock;

	public MenuItemView(Context context, boolean isClock, int best) {
		super(context);
		this.isClock = isClock;
		this.mBest = best;
	}

	private int mViewWidth;
	private int mViewHeight;

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mViewWidth = w;
		mViewHeight = h;
		mBitmapClock = BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon);
		rectSrc = new Rect(0, 0, w, h);
		rectDst = new Rect(0, 0, w, h);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

		if (isClock) {
			myDrawClock(canvas);
		} else {
			myDrawBest(canvas);
		}
		myDrawLevel(canvas);
	}

	private void myDrawLevel(Canvas canvas) {

	}

	private void myDrawBest(Canvas canvas) {
		canvas.drawText("" + mBest, 0, 0, new Paint());
	}

	Rect rectSrc;
	Rect rectDst;

	private void myDrawClock(Canvas canvas) {
		canvas.drawBitmap(mBitmapClock, rectSrc, rectDst, new Paint());
	}

}
