package com.cpxiao.lightsoff.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.cpxiao.lightsoff.Config;
import com.cpxiao.lightsoff.R;
import com.cpxiao.lightsoff.ads.ZAdManager;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;


/**
 * BaseActivity
 *
 * @author cpxiao on 2016/6/13
 */
public class BaseActivity extends Activity {
    protected static final boolean DEBUG = Config.DEBUG;
    protected final String TAG = "CPXIAO--" + getClass().getSimpleName();

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //no title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //隐藏状态栏部分（电池电量、时间等部分）
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
            mAdView = null;
        }
        super.onDestroy();
    }

    protected void initAds(Context context, String adPosition) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.ads_layout);
        if (layout == null) {
            if (DEBUG) {
                throw new IllegalArgumentException("layout == null error!");
            }
            return;
        }
        if (DEBUG) {
            Log.d(TAG, "initAds: ");
        }

        mAdView = new AdView(context, adPosition, AdSize.BANNER_HEIGHT_50);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                if (DEBUG) {
                    Log.d(TAG, "onError: getErrorCode = " + adError.getErrorCode() + ", getErrorMessage = " + adError.getErrorMessage());
                }

            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (DEBUG) {
                    Log.d(TAG, "onAdLoaded: ");
                }

            }

            @Override
            public void onAdClicked(Ad ad) {
                if (DEBUG) {
                    Log.d(TAG, "onAdClicked: ");
                }

            }
        });
        if (DEBUG) {
            List<String> testDevices = new ArrayList<>();
            testDevices.add("e2aec15df12cd8b4571af5f1275a34ec");//htc native?
            testDevices.add("55c4f301d7c1183f1fa6ede6b3f2fe2e");//坚果 native?
            AdSettings.addTestDevices(testDevices);
        }
        layout.removeAllViews();
        layout.addView(mAdView);
        mAdView.loadAd();
    }

    protected void initAds(Context context, int adPosition) {
        if (DEBUG) {
            Log.d(TAG, "initAds: ");
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.ads_layout);
        if (layout == null) {
            if (DEBUG) {
                throw new IllegalArgumentException("layout == null error!");
            }
            return;
        }
        View view = ZAdManager.getInstance().getAd(context, adPosition);
        if (view != null) {
            layout.removeAllViews();
            layout.addView(view);
        }
    }

}
