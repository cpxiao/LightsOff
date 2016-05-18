package com.cpxiao.lightsoff.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cpxiao.lightsoff.ExtraKey;
import com.cpxiao.lightsoff.R;

import java.util.ArrayList;

/**
 * Created by cpxiao on 5/17/16.
 * MenuActivity
 */
public class MenuActivity extends BaseActivity {

	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.mipmap.app_icon);
		ImageView imageView1 = new ImageView(this);
		imageView1.setImageResource(R.mipmap.app_icon222);
		images.add(imageView);
		images.add(imageView1);
		mViewPager.setAdapter(new MyAdapter(images));
	}

	private ArrayList<ImageView> images = new ArrayList<>();


	private class MyAdapter extends PagerAdapter {
		private ArrayList<ImageView> mList;

		public MyAdapter(ArrayList<ImageView> list) {
			mList = list;
		}

		// 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
		@Override
		public int getCount() {
			return mList.size();
		}

		// 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		// PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
		@Override
		public void destroyItem(ViewGroup view, int position, Object object) {
			view.removeView(mList.get(position));
		}

		// 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			view.addView(mList.get(position));
			return mList.get(position);
		}
	}

	public static void comeToMe(Context context, int level) {
		Intent intent = new Intent(context, MenuActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(ExtraKey.INTENT_EXTRA_LEVEL, level);
		context.startActivity(intent);
	}
}
