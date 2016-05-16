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

import java.util.Random;

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
	private int mPaddingLR;
	private int mPaddingTB;
	private boolean isGravityCenter = true;

	private GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		setOnTouchListener(this);
	}

	public GameView(Context context, int gameType, int moves) {
		this(context);
		String tmp = "";
		for (int i = 0; i < 16; i++) {
			init(5, i + 4);
			tmp = tmp + printf();
		}
		Log.d(TAG, tmp);
		init(gameType, moves);
	}

	private String printf() {
		String tmp = "\"";
		for (int y = 0; y < mGameType; y++) {
			for (int x = 0; x < mGameType; x++) {
				tmp = tmp + mStore[y][x];
			}
		}
		tmp = tmp + "\",";
//		Log.d(TAG, tmp);
		return tmp;
	}

	public GameView(Context context, int gameType, int[] store) {
		this(context);
		init(gameType, store);
	}

	public GameView(Context context, int gameType, String store) {
		this(context);
		init(gameType, store);
	}

	private void init(int gameType, int moves) {
		mGameType = gameType;
		if (moves < 0) {
			throw new IllegalArgumentException("moves number error!");
		}
		mStore = new int[gameType][gameType];
		for (int y = 0; y < gameType; y++) {
			for (int x = 0; x < gameType; x++) {
				mStore[y][x] = 0;
			}
		}
		Random random = new Random();
		for (int i = 0; i < moves; i++) {
			updateStore(random.nextInt(gameType), random.nextInt(gameType));
		}
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
				int cx = mPaddingLR + mItemWidth / 2 + mItemWidth * x;
				int cy = mPaddingTB + mItemHeight / 2 + mItemHeight * y;

				if (mStore[y][x] == 1) {
					lightOn(canvas, cx, cy);
				} else {
					lightOff(canvas, cx, cy);
				}
			}
		}
	}

	private Paint mPaint = new Paint();

	private void lightOn(Canvas canvas, int cx, int cy) {

		mPaint.setColor(Color.YELLOW);
//		Shader shader = new RadialGradient(cx, cy, mItemRadius * 3f, Color.YELLOW, Color.GREEN, Shader.TileMode.CLAMP);
//		mPaint.setShader(shader);
		canvas.drawCircle(cx, cy, mItemRadius, mPaint);

		mPaint.setAlpha(64);
		canvas.drawCircle(cx, cy, (mItemRadius + mItemRadius * (1f - 0.618f)), mPaint);
	}


	private void lightOff(Canvas canvas, int cx, int cy) {
		mPaint.setColor(Color.BLACK);
		canvas.drawCircle(cx, cy, mItemRadius, mPaint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.d(TAG, "surfaceChanged()..............");
		mViewWidth = width;
		mViewHeight = height;
		mItemWidth = width / mGameType;
		mItemHeight = height / mGameType;
		mItemRadius = (int) (Math.min(width, height) / mGameType * 0.4 * 0.618);

		/**
		 * 若需要居中，则重置相关值
		 */
		if (isGravityCenter) {
			if (width > height) {
				mPaddingTB = 0;
				mPaddingLR = (width - height) / 2;
			} else {
				mPaddingLR = 0;
				mPaddingTB = (height - width) / 2;
			}
			mItemWidth = mItemHeight = Math.min(mItemWidth, mItemHeight);
		}


		myDraw();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "surfaceDestroyed()..............");
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			float tmpX = event.getX() - mPaddingLR;
			float tmpY = event.getY() - mPaddingTB;
			/**
			 * 非电灯区域，不处理
			 */
			if (tmpX < 0 || tmpX > mGameType * mItemWidth || tmpY < 0 || tmpY > mGameType * mItemHeight) {
				return true;
			}

			mMoves++;
			if (mOnGameListener != null) {
				mOnGameListener.onMovesChange(mMoves);
			}
			int indexX = (int) (tmpX / mItemWidth);
			int indexY = (int) (tmpY / mItemHeight);

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
