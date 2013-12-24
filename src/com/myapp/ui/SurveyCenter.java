package com.myapp.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.manager.MyFragmentManager;
import com.myapp.view.BidirSlidingLayout;
import com.myapp.view.CenterLinearLayout.OnTouchListViewListener;
import com.myapp.view.FriendsFragment;
import com.myapp.view.HomeFragment;
import com.myapp.view.HomeFragment1;
import com.myapp.view.SettingFragment;

@SuppressLint("NewApi")
public class SurveyCenter extends FragmentActivity implements OnTouchListViewListener{
	private List<View> myLinear;
	private List<TextView> myText;
	
	private int currentSelectIndex = 0;
	
	public BidirSlidingLayout bidirSldingLayout; 
	
	private ImageView user;
	private TextView title;
	private TextView titleRight;
	private ImageButton showLeftButton;
	private ImageButton showRightButton;
	private LinearLayout content;
	
	private boolean isexit = false;   
	private boolean hastask = false;  
	Timer texit = new Timer();  
	TimerTask task = new TimerTask() {  
        public void run() {  
        isexit = false;  
        hastask = true;  
        }  
    };  
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.survey_center);
		
        getWidget();
    	setEvent();
    	setHomeFrag();
	}
	
	public void getWidget(){
		bidirSldingLayout = (BidirSlidingLayout) findViewById(R.id.bidir_sliding_layout);  
        showLeftButton = (ImageButton) findViewById(R.id.show_left_button);
		showRightButton = (ImageButton) findViewById(R.id.show_right_button);
		user = (ImageView)findViewById(R.id.img_user);
		title = (TextView)findViewById(R.id.tv_title);
		titleRight = (TextView)findViewById(R.id.tv_title_right);
		content = (LinearLayout)findViewById(R.id.content);
		
		myLinear = new ArrayList<View>();
		myLinear.add((View) findViewById(R.id.s_linear_survey_center));
		myLinear.add((View) findViewById(R.id.s_linear_friends));
		myLinear.add((View) findViewById(R.id.s_linear_setting));
		
		myText = new ArrayList<TextView>();
		myText.add((TextView) findViewById(R.id.tv_survey_color));
		myText.add((TextView) findViewById(R.id.tv_friends_color));
		myText.add((TextView) findViewById(R.id.tv_setting_color));
	}
	
	public void setEvent() {
		user.setOnClickListener(new myListener());
		for (int i = 0; i < this.myLinear.size(); i++) 
			myLinear.get(i).setOnClickListener(new myListener());
		showLeftButton.setOnClickListener(new myListener());
		showRightButton.setOnClickListener(new myListener());
		bidirSldingLayout.setScrollEvent(content);
	}
	
	public void setHomeFrag() {
//		HomeFragment home_frag = new HomeFragment(SurveyCenter.this);
//		MyFragmentManager.myFragment(getFragmentManager(),home_frag);
		HomeFragment1 home_frag = new HomeFragment1(SurveyCenter.this);
		home_frag.setFragmentManager(getSupportFragmentManager());
		MyFragmentManager.myChangeFragment(getSupportFragmentManager(),home_frag);
	}
	
	class myListener implements OnClickListener {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int index = -1;
			switch (v.getId()) {
			case R.id.img_user:
				Intent intent = new Intent();
				intent.setClass(SurveyCenter.this, UserHomepage.class);
				startActivity(intent);
				break;
			case R.id.s_linear_survey_center:
				index = 0;
				break;
			case R.id.s_linear_friends:
				index = 1;	
				break;
			case R.id.s_linear_setting:
				index = 2;
				break;
			case R.id.show_left_button:
				if (bidirSldingLayout.isLeftLayoutVisible()) {
					bidirSldingLayout.scrollToContentFromLeftMenu();
				} else {
					bidirSldingLayout.initShowLeftState();
					bidirSldingLayout.scrollToLeftMenu();
				}
				break;
			case R.id.show_right_button:
				if (bidirSldingLayout.isRightLayoutVisible()) {
					bidirSldingLayout.scrollToContentFromRightMenu();
				} else {
					bidirSldingLayout.initShowRightState();
					bidirSldingLayout.scrollToRightMenu();
				}
				break;
			}
			
			if (index != currentSelectIndex && index != -1) {

				myText.get(currentSelectIndex).setBackgroundColor(Color.rgb(41, 41, 41));
				myText.get(index).setBackgroundColor(Color.rgb(30, 144, 255));
				myLinear.get(currentSelectIndex).setBackgroundColor(Color.rgb(41, 41, 41));
				myLinear.get(index).setBackgroundColor(Color.rgb(0, 0, 0));
				currentSelectIndex = index;
				switch(index) {
				case 0:
					surveyCenterClick();
					break;
				case 1:
					friendsGroupClick();
					break;
				case 2:
					settingClick();
					break;
				}
			}
		}
	}
	
	public void surveyCenterClick(){
//		bidirSldingLayout.scrollToContentFromLeftMenu();
//		HomeFragment home_frag = new HomeFragment(SurveyCenter.this);
//		MyFragmentManager.myFragment(getFragmentManager(), home_frag);
//		title.setText("调查中心");
//		titleRight.setText("消息列表");
	}
	
	public void friendsGroupClick(){
//		bidirSldingLayout.scrollToContentFromLeftMenu();
//		FriendsFragment friends_frag = new FriendsFragment(SurveyCenter.this);
//		MyFragmentManager.myFragment(getFragmentManager(), friends_frag);
//		title.setText("朋友圈");
//		titleRight.setText("好友列表");
	}
	
	public void settingClick(){
//		bidirSldingLayout.scrollToContentFromLeftMenu();
//		SettingFragment setting_frag = new SettingFragment(SurveyCenter.this);
//		MyFragmentManager.myFragment(getFragmentManager(), setting_frag);
//		title.setText("设置");
//		titleRight.setText("带扩展列表");
	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){  
            //DialogAPI.showExit(this);
        	if(bidirSldingLayout.isLeftLayoutVisible()) {
	            if(isexit == false){  
	                isexit = true;  
	                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();  
	                if(!hastask) {  
	                    texit.schedule(task, 2000);  
	                }  
	            }else{  
	                finish();  
	                System.exit(0);  
	            }  
	            return false; 
        	}else{
				bidirSldingLayout.initShowLeftState();
				bidirSldingLayout.scrollToLeftMenu();
				return false;
        	}
        }  
        return super.onKeyDown(keyCode, event);  
    }

	@Override
	public void onTouchListView() {
		// TODO Auto-generated method stub
		bidirSldingLayout.listViewOnTouch = true;
	}  

}
