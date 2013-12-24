package com.myapp.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.myapp.R;
import com.myapp.adapter.FragmentPagerAdapterSurvey;
import com.myapp.adapter.ViewPagerAdapterSurvey;
import com.myapp.view.CenterLinearLayout.OnTouchListViewListener;

@SuppressLint({ "ValidFragment", "NewApi" })
public class HomeFragment1 extends Fragment implements OnClickListener{
	private Context context;
	private View view; 
	

	private ViewPager viewPager;
	private List<View> listPageViews;
	private ArrayList<Fragment> fragmentsList;
	private List<TextView> tvTitles;
	private final int height = 70;
	private int H_width;
	private HorizontalScrollView horizontalScrollView;
	private LinearLayout linearLayout;
	
	private FragmentManager fragmentManager;
	
//	private OnTouchListViewListener mOnTouchLister;

	private String title[] = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "tem" };
	
	private OnTouchListViewListener mOnTouchLister;
	
	public void setFragmentManager(FragmentManager fragmentManager) {
		this.fragmentManager = fragmentManager;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		SurveyFragment survey_frag = new SurveyFragment(context);
//		MyFragmentManager.surveyFragmentChange(getFragmentManager(),survey_frag);
		linearLayout = (LinearLayout) view.findViewById(R.id.ll_main);
		viewPager = (ViewPager) view.findViewById(R.id.pager);
		horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollView);
		InItTitle1();
		setSelector(0);
		InItView();
		InitViewPager();
//		viewPager.setAdapter(new ViewPagerAdapterSurvey(listPageViews));
		viewPager.setAdapter(new FragmentPagerAdapterSurvey(fragmentManager,fragmentsList));
		viewPager.clearAnimation();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setSelector(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				if(arg0==0&&arg2==0&&(arg1>-0.0001&&arg1<=0.0001)){
					mOnTouchLister.onTouchListView();
				}else if(arg0==viewPager.getChildCount() && arg2==0 && (arg1>-0.0001 && arg1<=0.0001)){
					mOnTouchLister.onTouchListView();
				}
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.home_fragment1, container, false);
		return view;
	}	

	public HomeFragment1(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		try {
			mOnTouchLister = (OnTouchListViewListener)this.context;
		} catch (ClassCastException e) {
			throw new ClassCastException(((Activity)context).toString() + "must implement OnbtnSendClickListener");//这条表示，你不在Activity里实现这个接口的话，我就要抛出异常咯。知道下一步该干嘛了吧？
		}
	}
	
	 private void InitViewPager() {
		 fragmentsList = new ArrayList<Fragment>();
		 
		 Fragment activityfragment = SurveyFragment1.newInstance("Hello Activity.",context);
		 Fragment groupFragment = SurveyFragment1.newInstance("Hello Group.",context);
		 Fragment friendsFragment=SurveyFragment1.newInstance("Hello Friends.",context);
		 Fragment chatFragment=SurveyFragment1.newInstance("Hello Chat.",context);

		 fragmentsList.add(activityfragment);
		 fragmentsList.add(groupFragment);
		 fragmentsList.add(friendsFragment);
		 fragmentsList.add(chatFragment);
	 }
	
	
	/***
	 * init view
	 */
	public void InItView() {
		listPageViews = new ArrayList<View>();
//		SurveyFragment survey_frag = new SurveyFragment(context);
		View view01 = new TextView(context);
		view01.setBackgroundColor(Color.BLUE);
		View view02 = new TextView(context);
		view02.setBackgroundColor(Color.RED);
		View view03 = new TextView(context);
		view03.setBackgroundColor(Color.YELLOW);
		View view04 = new TextView(context);
		view04.setBackgroundColor(Color.BLUE);
		View view05 = new TextView(context);
		view05.setBackgroundColor(Color.RED);
		View view06 = new TextView(context);
		view06.setBackgroundColor(Color.YELLOW);
		View view07 = new TextView(context);
		view07.setBackgroundColor(Color.BLUE);
		View view08 = new TextView(context);
		view08.setBackgroundColor(Color.RED);
		View view09 = new TextView(context);
		view09.setBackgroundColor(Color.BLUE);
		View view10 = new TextView(context);
		view10.setBackgroundColor(Color.RED);

		listPageViews.add(view01);
		listPageViews.add(view02);
		listPageViews.add(view03);
		listPageViews.add(view04);
		listPageViews.add(view05);
		listPageViews.add(view06);
		listPageViews.add(view07);
		listPageViews.add(view08);
		listPageViews.add(view09);
		listPageViews.add(view10);
	}
	
	/***
	 * init title
	 */
	public void InItTitle1() {
		tvTitles = new ArrayList<TextView>();
		H_width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth() / 4;
		for (int i = 0; i < title.length; i++) {
			TextView textView = new TextView(context);
			textView.setText(title[i]);
			textView.setTextSize(17);
			textView.setTextColor(Color.BLACK);
			textView.setWidth(H_width);
			Log.e("aa", "text_width=" + textView.getWidth());
			textView.setHeight(height - 30);
			textView.setGravity(Gravity.CENTER);
			textView.setId(i);
			textView.setOnClickListener(this);
			tvTitles.add(textView);
			// 分割线
			View view = new View(context);
			LinearLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.width = 1;
			layoutParams.height = height - 40;
			layoutParams.gravity = Gravity.CENTER;
			view.setLayoutParams(layoutParams);
			view.setBackgroundColor(Color.GRAY);
			linearLayout.addView(textView);
			if (i != title.length - 1) {
				linearLayout.addView(view);
			}
			Log.e("aa", "linearLayout_width=" + linearLayout.getWidth());

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		setSelector(v.getId());
	}
	
	/***
	 * 选中效果
	 */
	@SuppressWarnings("deprecation")
	public void setSelector(int id) {
		for (int i = 0; i < title.length; i++) {
			if (id == i) {
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.grouplist_item_bg_normal);
				tvTitles.get(id).setBackgroundDrawable(new BitmapDrawable(bitmap));
				tvTitles.get(id).setTextColor(Color.RED);
				if (i > 2) {
					horizontalScrollView.smoothScrollTo((tvTitles.get(i).getWidth() * i - 180), 0);
				} else {
					horizontalScrollView.smoothScrollTo(0, 0);
				}
				viewPager.setCurrentItem(i);
			} else {
				tvTitles.get(i).setBackgroundDrawable(new BitmapDrawable());
				tvTitles.get(i).setTextColor(Color.BLACK);
			}
		}
	}
}
