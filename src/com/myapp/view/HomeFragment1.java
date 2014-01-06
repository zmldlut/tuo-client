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
import com.myapp.base.BaseFragment;
import com.myapp.base.BaseMessage;
import com.myapp.base.C;
import com.myapp.model.Classify;
import com.myapp.view.CenterLinearLayout.OnTouchListViewListener;

@SuppressLint({ "ValidFragment", "NewApi" })
public class HomeFragment1 extends BaseFragment implements OnClickListener{
	
	private static final String TAG = "HomeFragment1";
	
	private Context context;
	private View view; 
	
	private FragmentManager fragmentManager;
	private OnTouchListViewListener mOnTouchLister;

	private ViewPager viewPager;
	private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
	private List<TextView> tvTitles = new ArrayList<TextView>();
	
	private HorizontalScrollView horizontalScrollView;
	private LinearLayout linearLayout;
	
	private final int height = 70;
	private int H_width;
	
	private ArrayList<Classify> classifyList = new ArrayList<Classify>();
	private int length = 0;

	public void setFragmentManager(FragmentManager fragmentManager) {
		this.fragmentManager = fragmentManager;
	}
	
	public HomeFragment1(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		this.context = context;
		Log.i(TAG, TAG+"-----HomeFragment1");
		try {
			mOnTouchLister = (OnTouchListViewListener)this.context;
		} catch (ClassCastException e) {
			throw new ClassCastException(((Activity)context).toString() + "must implement OnbtnSendClickListener");//这条表示，你不在Activity里实现这个接口的话，我就要抛出异常咯。知道下一步该干嘛了吧？
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, TAG+"-----onActivityCreated");
		getWidget();
		doTaskGetClassify();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, TAG+"-----onCreateView");
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.home_fragment1, container, false);
		return view;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onTaskComplete(int taskId, BaseMessage message){
		switch (taskId) {
		case C.task.classifyList:
			try {
				classifyList = (ArrayList<Classify>) message.getResultList("Classify");
				length = classifyList.size();
				initTitle();
				setSelector(0);
				initViewPager();
			} catch (Exception e) {
				e.printStackTrace();
				toast(e.getMessage());
			}
			break;
		}
	}
	
	public void doTaskGetClassify() {

		try {
			this.doTaskAsync(C.task.classifyList, C.api.classifyList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getWidget(){
		Log.i(TAG, TAG+"-----getWidget");
		linearLayout = (LinearLayout) view.findViewById(R.id.ll_main);
		viewPager = (ViewPager) view.findViewById(R.id.pager);
		horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollView);
	}
	
	private void initViewPager() {
		 fragmentsList = new ArrayList<Fragment>();
		 for (int i = 0; i < length; i++){
			 Fragment surveyFragment = SurveyFragment1.newInstance(classifyList.get(i),context); 
			 fragmentsList.add(surveyFragment);
		 }
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
				 }else if(arg0==viewPager.getChildCount()-1 && arg2==0 && (arg1>-0.0001 && arg1<=0.0001)){
					 mOnTouchLister.onTouchListView();
				 }
			 }
			 
			 @Override
			 public void onPageScrollStateChanged(int arg0) {

			 }
		 });
		 viewPager.setCurrentItem(0);
	 }
	
	/***
	 * init title
	 */
	@SuppressWarnings("deprecation")
	public void initTitle() {
		
		tvTitles.clear();
		H_width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth() / 4;
		
		for (int i = 0; i < length; i++) {
			TextView textView = new TextView(context);
			textView.setText(classifyList.get(i).getName());
			textView.setTextSize(17);
			textView.setTextColor(Color.BLACK);
			textView.setWidth(H_width);
			
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
			if (i != length - 1) {
				linearLayout.addView(view);
			}

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
		for (int i = 0; i < length; i++) {
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
