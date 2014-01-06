package com.myapp.ui;

import java.util.Calendar;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.myapp.R;
import com.myapp.base.BaseAuth;
import com.myapp.base.BaseMessage;
import com.myapp.base.BaseUi;
import com.myapp.base.C;
import com.myapp.manager.MyFragmentManager;
import com.myapp.view.MicroBlogFragment;

@SuppressLint("NewApi")
public class UserHomepage extends BaseUi implements OnClickListener {
	private ImageButton last_view;
	private Button bad;
	private TextView userName;
	
	private int day;
	private int year;
	private static final String SHAREDPREFERENCES_NAME = "myClick";
	private int day_now;
	private int year_now;
	private static final int USER_MICROBLOG = 0;
	
	private String id;
	private String YEAR;
	private String name;
	private String face;
	private String faceUrl;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_homepage);
	
		getWidgetId();
		setClickEvent();
		initView();
		setCalendarEvent();
		MicroBlogFragment microBlog_frag = new MicroBlogFragment(UserHomepage.this, R.layout.list_micro_blog_user, USER_MICROBLOG, id);
		MyFragmentManager.microBlogFragmentChange(getSupportFragmentManager(),microBlog_frag);
	}
	
	public void setCalendarEvent() {
		SharedPreferences preferences = getSharedPreferences(
	            SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		day = preferences.getInt(id, -1);
		year = preferences.getInt(YEAR, -1);
		Calendar c = Calendar.getInstance();
		day_now = c.get(Calendar.DAY_OF_YEAR);
		year_now = Calendar.YEAR;
		
		if(year == -1||day == -1||year_now>year||day_now >= (day + 1)) {
			bad.setClickable(true);
		}else {
			bad.setClickable(false);
		}
	}
	public void getWidgetId() {
		last_view = (ImageButton)findViewById(R.id.b_last_view);
		bad = (Button)findViewById(R.id.b_bad);
		userName = (TextView)findViewById(R.id.tv_user_home_userName);
		
	}
	
	public void setClickEvent() {
		last_view.setOnClickListener(this);
		bad.setOnClickListener(this);
	}
	
	public void initView(){
		Bundle bundle = this.getIntent().getExtras();
		id = bundle.getString("id")+"_id";
		YEAR = id+"YEAR";
		name = bundle.getString("name");
		face = bundle.getString("face");
		faceUrl = bundle.getString("faceUrl");
		
		userName.setText(name);
		
		if(bundle.getString("id").equals(BaseAuth.getUser().getId())){
			bad.setVisibility(View.INVISIBLE);
		}
	}
	
	public void doTaskStamp(){	
		
		try {
			HashMap<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("stamponId", id);
			this.doTaskAsync(C.task.stamp, C.api.stamp, urlParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTaskComplete(int taskId, BaseMessage message){
		switch (taskId) {
		case C.task.stamp:
			try {
				String codeReturn = message.getCode();
				if(codeReturn.equals("10000")){
					toast("明天继续来哦！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				toast(e.getMessage());
			}
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.b_last_view:
			finish();
			break;
		case R.id.b_bad:
			SharedPreferences preferences = getSharedPreferences(
		            SHAREDPREFERENCES_NAME, MODE_PRIVATE);
			doTaskStamp();
			Calendar c = Calendar.getInstance();
			int temp = c.get(Calendar.DAY_OF_YEAR);
			Editor editor = preferences.edit();
		        // 存入数据
		    editor.putInt(id, temp);
		    editor.putInt(YEAR, Calendar.YEAR);
		        // 提交修改
		    editor.commit();
			bad.setClickable(false);
			break;	
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_homepage, menu);
		return true;
	}
}
