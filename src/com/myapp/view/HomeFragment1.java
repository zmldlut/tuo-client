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
import com.myapp.base.BaseMessage;
import com.myapp.base.C;
import com.myapp.model.Classify;
import com.myapp.util.TaskAsyncUtil;
import com.myapp.view.CenterLinearLayout.OnTouchListViewListener;

@SuppressLint({ "ValidFragment", "NewApi" })
public class HomeFragment1 extends Fragment implements OnClickListener{
	private Context context;
	private View view; 
	
	private FragmentManager fragmentManager;
	private OnTouchListViewListener mOnTouchLister;

	private ViewPager viewPager;
	private ArrayList<Fragment> fragmentsList;
	private List<TextView> tvTitles;
	
	private HorizontalScrollView horizontalScrollView;
	private LinearLayout linearLayout;
	
	private TaskAsyncUtil taskAsyncUtil;
	
	private final int height = 70;
	private int H_width;
	
	private ArrayList<Classify> classifyList = new ArrayList<Classify>();
	private int length = 0;
	private String title[] = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "tem" };
	
	
	public void setFragmentManager(FragmentManager fragmentManager) {
		this.fragmentManager = fragmentManager;
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
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		getWidget();
		initTask();
		doTaskGetClassify();
		
		InitViewPager();
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
	
	public void getWidget(){
		linearLayout = (LinearLayout) view.findViewById(R.id.ll_main);
		viewPager = (ViewPager) view.findViewById(R.id.pager);
		horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollView);
	}
	
	
	public void doTaskGetClassify() {

		try {
			taskAsyncUtil.doTaskAsync(C.task.classifyList, C.api.classifyList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initTask(){
		taskAsyncUtil = new TaskAsyncUtil(context){
			@Override
			public void onTaskComplete(int taskId, BaseMessage message){
				
				switch (taskId) {
				case C.task.classifyList:
					try {
						classifyList = (ArrayList<Classify>) message.getResultList("Classify");
						length = classifyList.size();
						InItTitle();
						setSelector(0);
					} catch (Exception e) {
						e.printStackTrace();
						toast(e.getMessage());
					}
					break;
				}
			}
		};
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
	 * init title
	 */
	public void InItTitle() {
		tvTitles = new ArrayList<TextView>();
		H_width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth() / 4;
		
		for (int i = 0; i < length; i++) {
			TextView textView = new TextView(context);
			textView.setText(classifyList.get(i).getName());
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
			if (i != length - 1) {
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
