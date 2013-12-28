package com.myapp.view;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.base.BaseAuth;
import com.myapp.manager.MyFragmentManager;

@SuppressLint({ "ValidFragment", "NewApi" })
public class FriendsFragment extends Fragment implements OnClickListener {

	private Context context;
	private View view;
	private FragmentManager fragmentManager;
	
	private Button sign;
	private TextView userName;
	
	private int day;
	private static final String SHAREDPREFERENCES_NAME = "mySign";
	private int day_now;
	private static final int FRIENDS_MICROBLOG =1;
	
	
	
	public void setFragmentManager(FragmentManager fragmentManager) {
		this.fragmentManager = fragmentManager;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getWidgetId();
		setClickEvent();
		setCalendarEvent();
		MicroBlogFragment microBlog_frag = new MicroBlogFragment(context, R.layout.list_micro_blog, FRIENDS_MICROBLOG);
		MyFragmentManager.microBlogFriendsFragmentChange(fragmentManager, microBlog_frag);
	}
	
	@SuppressWarnings("static-access")
	public void setCalendarEvent() {
		SharedPreferences preferences = context.getSharedPreferences(
	            SHAREDPREFERENCES_NAME, context.MODE_PRIVATE);
		day = preferences.getInt("day", -1);
		Calendar c = Calendar.getInstance();
		day_now = c.get(Calendar.DAY_OF_MONTH);
		if(day_now >= (day + 1) || day == -1) {
			sign.setClickable(true);
		}else {
			sign.setClickable(false);
		}
	}
	public void getWidgetId() {
		
		sign = (Button)view.findViewById(R.id.b_sign);
		userName = (TextView)view.findViewById(R.id.tv_friends_userName);
		userName.setText(BaseAuth.getUser().getName());
	}
	public void setClickEvent() {
		sign.setOnClickListener(this);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {

		case R.id.b_sign:
			SharedPreferences preferences = context.getSharedPreferences(
		            SHAREDPREFERENCES_NAME, context.MODE_PRIVATE);
			 Toast.makeText(context.getApplicationContext(), "签到成功，明天继续啊！", Toast.LENGTH_SHORT).show();
			 Calendar c = Calendar.getInstance();
			 int temp = c.get(Calendar.DAY_OF_MONTH);
			 Editor editor = preferences.edit();
		        // 存入数据
		        editor.putInt("day", temp);
		        // 提交修改
		        editor.commit();
			sign.setClickable(false);
			break;	
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.friends_fragment, container, false);
		return view;
	}	

	public FriendsFragment(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
}
