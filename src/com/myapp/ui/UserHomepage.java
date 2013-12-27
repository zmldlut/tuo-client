package com.myapp.ui;

import java.util.Calendar;

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
import android.widget.Toast;

import com.myapp.R;
import com.myapp.base.BaseAuth;
import com.myapp.base.BaseUi;
import com.myapp.manager.MyFragmentManager;
import com.myapp.view.MicroBlogFragment;

@SuppressLint("NewApi")
public class UserHomepage extends BaseUi implements OnClickListener {
	private ImageButton last_view;
	private Button bad;
	private TextView userName;
	
	private int day;
	private static final String SHAREDPREFERENCES_NAME = "myClick";
	private int day_now;
	private static final int USER_MICROBLOG = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_homepage);
		
		getWidgetId();
		setClickEvent();
		setCalendarEvent();
		initView();
		MicroBlogFragment microBlog_frag = new MicroBlogFragment(UserHomepage.this, R.layout.list_micro_blog_user, USER_MICROBLOG);
		MyFragmentManager.microBlogFragmentChange(getSupportFragmentManager(),microBlog_frag);
	}
	

	public void setCalendarEvent() {
		SharedPreferences preferences = getSharedPreferences(
	            SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		day = preferences.getInt("day", -1);
		Calendar c = Calendar.getInstance();
		day_now = c.get(Calendar.DAY_OF_MONTH);
		if(day_now >= (day + 1) || day == -1) {
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
		userName.setText(BaseAuth.getUser().getName());
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
			toast("你狠狠地踩了Ta！");
			Calendar c = Calendar.getInstance();
			int temp = c.get(Calendar.DAY_OF_MONTH);
			Editor editor = preferences.edit();
		        // 存入数据
		    editor.putInt("day", temp);
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
