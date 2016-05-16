package com.cpxiao.lightsoff.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.cpxiao.lightsoff.OnGameListener;

/**
 * Created by cpxiao on 5/15/16.
 * GameView
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
	private static final String TAG = "CPXIAO" + GameView.class.getSimpleName();
	private SurfaceHolder mSurfaceHolder;
	private int mGameType;
	private int[][] mStore;

	private int mMoves = 0;

	private int mViewWidth;
	private int mViewHeight;
	private int mItemWidth;
	private int mItemHeight;
	private int mItemRadius;

	private GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		setOnTouchListener(this);
	}

	public GameView(Context context, int gameType, int[] store) {
		this(context);
		init(gameType, store);
	}

	public GameView(Context context, int gameType, String store) {
		this(context);
		init(gameType, store);
	}


	private void init(int gameType, int[] store) {
		mGameType = gameType;
		if (store == null || store.length != gameType * gameType) {
			throw new IllegalArgumentException("store number error!");
		}

		mStore = new int[gameType][gameType];
		int x;
		int y;
		for (int i = 0; i < store.length; i++) {
			x = i % gameType;
			y = i / gameType;
			mStore[y][x] = store[i];
		}
	}

	private void init(int gameType, String store) {
		mGameType = gameType;
		if (store == null || store.length() != gameType * gameType) {
			throw new IllegalArgumentException("store number error!");
		}

		mStore = new int[gameType][gameType];
		int x;
		int y;
		for (int i = 0; i < store.length(); i++) {
			x = i % gameType;
			y = i / gameType;
			mStore[y][x] = store.charAt(i) == '1' ? 1 : 0;
		}
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated()..............");
		mSurfaceHolder = holder;

	}

	private void myDraw() {
		Canvas canvas = mSurfaceHolder.lockCanvas();

		canvas.drawColor(Color.WHITE);
		drawLights(canvas);
		canvas.drawText("a", 0, 0, new Paint());


		mSurfaceHolder.unlockCanvasAndPost(canvas);
	}

	private void drawLights(Canvas canvas) {
		for (int y = 0; y < mGameType; y++) {
			for (int x = 0; x < mGameType; x++) {
				if (mStore[y][x] == 1) {
					lightOn(canvas, x, y);
				} else {
					lightOff(canvas, x, y);
				}
			}
		}
	}

	private Paint mPaint = new Paint();

	private void lightOn(Canvas canvas, int x, int y) {


//		drawLight(canvas, x, y, mPaint);
		float cx = mItemWidth / 2 + mItemWidth * x;
		float cy = mItemHeight / 2 + mItemHeight * y;


		mPaint.setColor(Color.YELLOW);
//		Shader shader = new RadialGradient(cx, cy, mItemRadius * 3f, Color.YELLOW, Color.GREEN, Shader.TileMode.CLAMP);
//		mPaint.setShader(shader);
		canvas.drawCircle(cx, cy, mItemRadius, mPaint);
	}


	private void lightOff(Canvas canvas, int x, int y) {
		mPaint.setColor(Color.BLACK);
		drawLight(canvas, x, y, mPaint);
	}

	private void drawLight(Canvas canvas, int x, int y, Paint mPaint) {
		float cx = mItemWidth / 2 + mItemWidth * x;
		float cy = mItemHeight / 2 + mItemHeight * y;
		canvas.drawCircle(cx, cy, mItemRadius, mPaint);
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.d(TAG, "surfaceChanged()..............");
		mViewWidth = width;
		mViewHeight = height;

		mItemWidth = width / mGameType;
		mItemHeight = height / mGameType;
		mItemRadius = (int) (Math.min(width, height) / mGameType * 0.4);

		myDraw();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "surfaceDestroyed()..............");
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			mMoves++;
			if (mOnGameListener != null) {
				mOnGameListener.onMovesChange(mMoves);
			}
			int indexX = (int) (event.getX() / mItemWidth);
			int indexY = (int) (event.getY() / mItemHeight);

			updateStore(indexX, indexY);
			myDraw();

			if (isSuccess()) {
				if (mOnGameListener != null) {
					mOnGameListener.onGameSuccess();
				}
				Toast.makeText(getContext(), "You Win!", Toast.LENGTH_LONG).show();
			}
		}
		return true;
	}

	private void updateStore(int indexX, int indexY) {
		changeItem(indexX, indexY);
		changeItem(indexX - 1, indexY);
		changeItem(indexX + 1, indexY);
		changeItem(indexX, indexY - 1);
		changeItem(indexX, indexY + 1);
	}

	private void changeItem(int indexX, int indexY) {
		if (indexX < 0 || indexX >= mGameType || indexY < 0 || indexY >= mGameType) {
			return;
		}
		mStore[indexY][indexX] = mStore[indexY][indexX] == 1 ? 0 : 1;
	}

	private boolean isSuccess() {
		for (int y = 0; y < mGameType; y++) {
			for (int x = 0; x < mGameType; x++) {
				if (mStore[y][x] == 1) {
					return false;
				}
			}
		}
		return true;
	}

	private OnGameListener mOnGameListener;

	public void setOnGameListener(OnGameListener listener) {
		mOnGameListener = listener;
	}
}
