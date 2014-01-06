package com.myapp.view;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.myapp.R;
import com.myapp.base.BaseAuth;
import com.myapp.base.BaseFragment;
import com.myapp.base.BaseMessage;
import com.myapp.base.C;
import com.myapp.manager.MyFragmentManager;

@SuppressLint({ "ValidFragment", "NewApi" })
public class FriendsFragment extends BaseFragment implements OnClickListener {

	private Context context;
	private View view;
	private FragmentManager fragmentManager;
	
	private Button sign;
	private TextView userName;
	
	private int day;
	private int year;
	private static final String SHAREDPREFERENCES_NAME = "mySign";
	private int day_now;
	private int year_now;
	private static final int FRIENDS_MICROBLOG =1;
	
	private static final String USERID = "id:"+BaseAuth.getUser().getId();
	private static final String YEAR = USERID + "YEAR";
	
	
	
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
		day = preferences.getInt(USERID, -1);
		year = preferences.getInt(YEAR, -1);
		Calendar c = Calendar.getInstance();
		day_now = c.get(Calendar.DAY_OF_YEAR);
		year_now = Calendar.YEAR;
		if(day == -1||year == -1||year_now>year||day_now >= (day + 1)) {
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
			
			doTaskSign();
			Calendar c = Calendar.getInstance();
			int temp = c.get(Calendar.DAY_OF_YEAR);
			Editor editor = preferences.edit();
			// 存入数据
			editor.putInt(USERID, temp);
			editor.putInt(YEAR, Calendar.YEAR);
			// 提交修改
			editor.commit();
			sign.setClickable(false);
			break;	
		}
	}

	public void doTaskSign(){	
		
		try {
			this.doTaskAsync(C.task.checkIn, C.api.checkIn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message){
		switch (taskId) {
		case C.task.checkIn:
			try {
				String codeReturn = message.getCode();
				if(codeReturn.equals("10000")){
					toast("签到成功，明天继续啊！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				toast(e.getMessage());
			}
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
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
}
