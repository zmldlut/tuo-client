package com.myapp.ui;

import java.util.ArrayList;
import java.util.List;

import com.myapp.R;
import com.myapp.adapter.ViewPagerAdapterGuide;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Guide extends Activity implements OnPageChangeListener {
	
	private ViewPager vp;
    private ViewPagerAdapterGuide vpAdapter;
    private List<View> views;
    
    private ImageView[] dots;
    
    private int currentIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initViews();
		initDots();
	}

	 private void initViews() {
	        LayoutInflater inflater = LayoutInflater.from(this);

	        views = new ArrayList<View>();
	        // 初始化引导图片列表
	        views.add(inflater.inflate(R.layout.one, null));
	        views.add(inflater.inflate(R.layout.two, null));
	        views.add(inflater.inflate(R.layout.three, null));
	        views.add(inflater.inflate(R.layout.four, null));

	        // 初始化Adapter
	        vpAdapter = new ViewPagerAdapterGuide(views, this);

	        vp = (ViewPager) findViewById(R.id.viewpager);
	        vp.setAdapter(vpAdapter);
	        // 绑定回调
	        vp.setOnPageChangeListener(this);
	    }

	    private void initDots() {
	        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

	        dots = new ImageView[views.size()];

	        // 循环取得小点图片
	        for (int i = 0; i < views.size(); i++) {
	            dots[i] = (ImageView) ll.getChildAt(i);
	            dots[i].setEnabled(true);// 都设为灰色
	        }

	        currentIndex = 0;
	        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	    }

	    private void setCurrentDot(int position) {
	        if (position < 0 || position > views.size() - 1
	                || currentIndex == position) {
	            return;
	        }

	        dots[position].setEnabled(false);
	        dots[currentIndex].setEnabled(true);

	        currentIndex = position;
	    }

	    // 当滑动状态改变时调用
	    @Override
	    public void onPageScrollStateChanged(int arg0) {
	    }

	    // 当当前页面被滑动时调用
	    @Override
	    public void onPageScrolled(int arg0, float arg1, int arg2) {
	    }

	    // 当新的页面被选中时调用
	    @Override
	    public void onPageSelected(int arg0) {
	        // 设置底部小点选中状态
	        setCurrentDot(arg0);
	    }
	


}
