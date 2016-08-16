package com.cpxiao.lightsoff.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.cpxiao.commonlibrary.utils.PreferencesUtils;
import com.cpxiao.lightsoff.ExtraKey;
import com.cpxiao.lightsoff.OnGameListener;
import com.cpxiao.lightsoff.R;

import java.util.Random;

/**
 * Created by cpxiao on 5/15/16.
 * GameView
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private static final String TAG = GameView.class.getSimpleName();
    private SurfaceHolder mSurfaceHolder;
    private int mX, mY;
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


    private Paint mPaintLightOn;
    private Paint mPaintLightOnAlpha64;
    private Paint mPaintLightOnAlpha32;
    private Paint mPaintLightOff;

    private GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);

        initPaints();

    }

    private void initPaints() {
        mPaintLightOn = new Paint();
        mPaintLightOn.setColor(Color.YELLOW);

        mPaintLightOnAlpha64 = new Paint();
        mPaintLightOnAlpha64.setColor(Color.YELLOW);
        mPaintLightOnAlpha64.setAlpha(64);

        mPaintLightOnAlpha32 = new Paint();
        mPaintLightOnAlpha32.setColor(Color.YELLOW);
        mPaintLightOnAlpha32.setAlpha(32);

        mPaintLightOff = new Paint();
        mPaintLightOff.setColor(Color.BLACK);
    }

    public GameView(Context context, int x, int y, int moves, String store) {
        this(context);
        init(x, y, moves, store);
    }

    private void init(final int x, final int y, final int moves) {
        mX = x;
        mY = y;
        if (moves < 0) {
            throw new IllegalArgumentException("moves number error!");
        }
        mStore = new int[y][x];
        for (int indexY = 0; indexY < x; indexY++) {
            for (int indexX = 0; indexX < x; indexX++) {
                mStore[indexY][indexX] = 0;
            }
        }
        Random random = new Random();
        for (int i = 0; i < moves; i++) {
            updateStore(random.nextInt(x), random.nextInt(y));
        }
    }

    private void init(final int x, final int y, final int moves, String store) {
        if (store == null) {
            init(x, y, moves);
            PreferencesUtils.putInt(getContext(), ExtraKey.KEY_X, x);
            PreferencesUtils.putInt(getContext(), ExtraKey.KEY_X, x);
            PreferencesUtils.putInt(getContext(), ExtraKey.KEY_X, x);
            PreferencesUtils.putInt(getContext(), ExtraKey.KEY_X, x);
            return;
        }
        mX = x;
        mY = y;
        if (store.length() != x * y) {
            throw new IllegalArgumentException("store number error!");
        }

        mStore = new int[y][x];
        int indexX;
        int indexY;
        for (int i = 0; i < store.length(); i++) {
            indexX = i % x;
            indexY = i / x;
            mStore[indexY][indexX] = store.charAt(i) == '1' ? 1 : 0;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated()..............");
        mSurfaceHolder = holder;

    }

    private void myDraw() {
        Canvas canvas = mSurfaceHolder.lockCanvas();

//		canvas.drawColor(Color.WHITE);
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.background_activity));
        drawLights(canvas);
        canvas.drawText("a", 0, 0, new Paint());


        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void drawLights(Canvas canvas) {
        for (int y = 0; y < mY; y++) {
            for (int x = 0; x < mX; x++) {
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


    /**
     * 黄金分割
     */
    private static final float GOLDEN_RATIO = 0.618f;

    private void lightOn(Canvas canvas, int cx, int cy) {
        canvas.drawCircle(cx, cy, mItemRadius, mPaintLightOn);
        canvas.drawCircle(cx, cy, (mItemRadius + mItemRadius * (1f - GOLDEN_RATIO)), mPaintLightOnAlpha64);
        canvas.drawCircle(cx, cy, (mItemRadius + mItemRadius * (1f - GOLDEN_RATIO) + mItemRadius * GOLDEN_RATIO * GOLDEN_RATIO * (1f - GOLDEN_RATIO)), mPaintLightOnAlpha32);
    }


    private void lightOff(Canvas canvas, int cx, int cy) {
        canvas.drawCircle(cx, cy, mItemRadius, mPaintLightOff);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged()..............");
        mViewWidth = width;
        mViewHeight = height;
        mItemWidth = width / mX;
        mItemHeight = height / mX;
        mItemRadius = (int) (Math.min(width, height) / mX * 0.4 * GOLDEN_RATIO);

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
            if (tmpX < 0 || tmpX > mX * mItemWidth || tmpY < 0 || tmpY > mX * mItemHeight) {
                return true;
            }

            mMoves++;
            if (mOnGameListener != null) {
                mOnGameListener.onStepsChange(mMoves);
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

    private boolean checkArguments() {
        if (mX <= 0 || mY <= 0) {
            throw new IllegalArgumentException();
        }
        if (mStore == null) {
            throw new IllegalArgumentException();
        }
        return true;
    }

    /**
     * @param indexX x
     * @param indexY y
     */
    private void updateStore(int indexX, int indexY) {
        changeItem(indexX, indexY);
        changeItem(indexX - 1, indexY);
        changeItem(indexX + 1, indexY);
        changeItem(indexX, indexY - 1);
        changeItem(indexX, indexY + 1);
    }

    private void changeItem(int indexX, int indexY) {
        if (indexX < 0 || indexX >= mX || indexY < 0 || indexY >= mX) {
            return;
        }
        mStore[indexY][indexX] = mStore[indexY][indexX] == 1 ? 0 : 1;
    }

    private boolean isSuccess() {
        for (int y = 0; y < mX; y++) {
            for (int x = 0; x < mX; x++) {
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
