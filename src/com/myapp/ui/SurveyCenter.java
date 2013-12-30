package com.myapp.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.myapp.R;
import com.myapp.adapter.ListAdapterRight;
import com.myapp.base.BaseAuth;
import com.myapp.base.BaseMessage;
import com.myapp.base.BaseUi;
import com.myapp.base.C;
import com.myapp.manager.MyFragmentManager;
import com.myapp.model.Fans;
import com.myapp.model.ListRightInfo;
import com.myapp.model.Notice;
import com.myapp.view.BidirSlidingLayout;
import com.myapp.view.CenterLinearLayout.OnTouchListViewListener;
import com.myapp.view.FriendsFragment;
import com.myapp.view.HomeFragment1;
import com.myapp.view.SettingFragment;

@SuppressLint("NewApi")
public class SurveyCenter extends BaseUi implements OnTouchListViewListener{
	
	private static final String TAG = "SurveyCenter";
	///
	///左侧控件
	///
	private ImageView userImage;
	private TextView userName;
	
	//左侧菜单控件 ，例如：拖后腿、朋友圈、设置
	private List<View> myLinear;
	//左侧菜单控件 ，例如：拖后腿、朋友圈、设置的左侧点击后特效
	private List<TextView> myText;
	
	///
	///中间控件
	///
	private ImageButton showLeftButton;
	private ImageButton showRightButton;
	private LinearLayout content;
	private TextView titleCenter;
	
	///
	///右侧控件
	///
	private TextView titleRight;
	private ListView listViewRight;
	private ListAdapterRight listAdapterNotice;
	private ListAdapterRight listAdapterFriends;
	private List<ListRightInfo> listNoticeInfo = new ArrayList<ListRightInfo>();
	private List<ListRightInfo> listFriendsInfo = new ArrayList<ListRightInfo>();
	private List<Notice> listNotice = new ArrayList<Notice>();
	private List<Fans> listFriends = new ArrayList<Fans>();
	
	//////////////////////////////////////////////////////////////////////////////////////

	
	
	private int currentSelectIndex = 0;

	public BidirSlidingLayout bidirSldingLayout;
	
	private boolean isexit = false;   
	private boolean hastask = false;
	
	Timer texit = new Timer();  
	TimerTask task = new TimerTask() {  
        public void run() {  
        isexit = false;  
        hastask = true;  
        }  
    };  
	
    //0:代表tap是“拖后腿”； 1:代表tap是“朋友圈”；2:代表tap是“设置”
    private static int currentTapMenu = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.survey_center);
        getWidget();
    	setEvent();
    	setHomeFrag();
//    	surveyCenterClick();
    	doTaskGetRightList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onTaskComplete(int taskId, BaseMessage message){
		switch (taskId) {
		case C.task.fansList:
			try {
				listFriends.clear();
				listFriends = (ArrayList<Fans>) message.getResultList("Fans");
				buildAppData1(listFriends);
			} catch (Exception e) {
				e.printStackTrace();
				toast(e.getMessage());
			}
			break;
		case C.task.noticeList:
			try {
				listNotice.clear();
				listNotice = (ArrayList<Notice>) message.getResultList("Notice");
				buildAppData(listNotice);
			} catch (Exception e) {
				e.printStackTrace();
				toast(e.getMessage());
			}
			break;
		}
		initView();
	}
	
	public void doTaskGetRightList(){
		try {
			this.doTaskAsync(C.task.noticeList, C.api.noticeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.doTaskAsync(C.task.fansList, C.api.fansList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buildAppData(List<Notice> list_notice){
		for (int i = 0; i < list_notice.size(); i++) {
			ListRightInfo ai = new ListRightInfo();
			ai.setListRightIcon(BitmapFactory.decodeResource(getResources(),
					R.drawable.eio_icon));
			ai.setListRightName(list_notice.get(i).getFromname());
			ai.setListRightContent(list_notice.get(i).getContent());
			listNoticeInfo.add(ai);
		}
	}
	
	public void buildAppData1(List<Fans> list_friends){
		for (int i = 0; i < list_friends.size(); i++) {
			ListRightInfo ai = new ListRightInfo();
			ai.setListRightIcon(BitmapFactory.decodeResource(getResources(),
					R.drawable.eio_icon));
			ai.setListRightName(list_friends.get(i).getName());
			ai.setListRightContent(list_friends.get(i).getScore());
			listFriendsInfo.add(ai);
		}
	}
	
	public void getWidget(){

		///左侧控件
		userImage = (ImageView)findViewById(R.id.img_user);
		userName = (TextView)findViewById(R.id.s_tv_user_name);
		
		myLinear = new ArrayList<View>();
		myLinear.add((View) findViewById(R.id.s_linear_survey_center));
		myLinear.add((View) findViewById(R.id.s_linear_friends));
		myLinear.add((View) findViewById(R.id.s_linear_setting));
		
		myText = new ArrayList<TextView>();
		myText.add((TextView) findViewById(R.id.tv_survey_color));
		myText.add((TextView) findViewById(R.id.tv_friends_color));
		myText.add((TextView) findViewById(R.id.tv_setting_color));
		
		///中间控件
		showLeftButton = (ImageButton) findViewById(R.id.show_left_button);
		showRightButton = (ImageButton) findViewById(R.id.show_right_button);
		
		titleCenter = (TextView)findViewById(R.id.tv_title);
		content = (LinearLayout)findViewById(R.id.content);
		
		///右侧控件
		bidirSldingLayout = (BidirSlidingLayout) findViewById(R.id.bidir_sliding_layout);  
		titleRight = (TextView)findViewById(R.id.tv_title_right);
		listViewRight = (ListView)findViewById(R.id.s_lv_right);
	}
	
	public void setEvent() {
		userImage.setOnClickListener(new myListener());
		for (int i = 0; i < this.myLinear.size(); i++) 
			myLinear.get(i).setOnClickListener(new myListener());
		showLeftButton.setOnClickListener(new myListener());
		showRightButton.setOnClickListener(new myListener());
		bidirSldingLayout.setScrollEvent(content);
		
		listViewRight.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 此处传回来的position和mAdapter.getItemId()获取的一致;
				Log.e(TAG, "click position:" + position);
//				toast("click position:" + position);
				switch(currentTapMenu){
				case 0:
					break;
				case 1:
					Fans friend= listFriends.get(position);
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putString("id", friend.getId());
					bundle.putString("name", friend.getName());
					bundle.putString("face", friend.getFace());
					bundle.putString("faceUrl", friend.getFaceurl());
					intent.putExtras(bundle);
					intent.setClass(SurveyCenter.this, UserHomepage.class);
					startActivity(intent);
					break;
				case 2:
					break;
				}
			}
		});
	}
	
	public void setHomeFrag() {
//		HomeFragment home_frag = new HomeFragment(SurveyCenter.this);
//		home_frag.setFragmentManager(getSupportFragmentManager());
//		MyFragmentManager.myChangeFragment(getSupportFragmentManager(),home_frag);
		HomeFragment1 home_frag = new HomeFragment1(SurveyCenter.this);

		home_frag.setFragmentManager(getSupportFragmentManager());
		MyFragmentManager.myChangeFragment(getSupportFragmentManager(),home_frag);
	}
	
	public void initView(){
		String name = BaseAuth.getUser().getName(); 
		userName.setText(name);
		titleCenter.setText("调查中心");
		titleRight.setText("消息列表");
		listAdapterNotice = new ListAdapterRight(SurveyCenter.this, listNoticeInfo);
		listAdapterFriends = new ListAdapterRight(SurveyCenter.this, listFriendsInfo);
		listViewRight.setAdapter(listAdapterNotice);
	}
	
	class myListener implements OnClickListener {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int index = -1;
			switch (v.getId()) {
			case R.id.img_user:
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("id", BaseAuth.getUser().getId());
				bundle.putString("name", BaseAuth.getUser().getName());
				bundle.putString("face", BaseAuth.getUser().getFace());
				bundle.putString("faceUrl", BaseAuth.getUser().getFaceurl());
				intent.putExtras(bundle);
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
					currentTapMenu = 0;
					surveyCenterClick();
					break;
				case 1:
					currentTapMenu = 1;
					friendsGroupClick();
					break;
				case 2:
					currentTapMenu = 2;
					settingClick();
					break;
				}
			}
		}
	}
	
	public void surveyCenterClick(){
		HomeFragment1 home_frag = new HomeFragment1(SurveyCenter.this);
		
		home_frag.setFragmentManager(getSupportFragmentManager());
		MyFragmentManager.myChangeFragment(getSupportFragmentManager(), home_frag);
		titleCenter.setText("调查中心"); 
		titleRight.setText("消息列表");
		listViewRight.setAdapter(listAdapterNotice);
		listAdapterNotice.notifyDataSetChanged(); 
		bidirSldingLayout.scrollToContentFromLeftMenu();
	}
	
	public void friendsGroupClick(){
		bidirSldingLayout.scrollToContentFromLeftMenu();
		FriendsFragment friends_frag = new FriendsFragment(SurveyCenter.this);
		friends_frag.setFragmentManager(getSupportFragmentManager());
		MyFragmentManager.myChangeFragment(getSupportFragmentManager(), friends_frag);
		titleCenter.setText("朋友圈");
		titleRight.setText("好友列表");
		listViewRight.setAdapter(listAdapterFriends);
		listAdapterFriends.notifyDataSetChanged(); 
	}
	
	public void settingClick(){
		bidirSldingLayout.scrollToContentFromLeftMenu();

		SettingFragment setting_frag = new SettingFragment(SurveyCenter.this);
		setting_frag.setFragmentManager(getSupportFragmentManager());
		MyFragmentManager.myChangeFragment(getSupportFragmentManager(), setting_frag);
		titleCenter.setText("设置");
		titleRight.setText("带扩展列表");
		listViewRight.setAdapter(null);
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

	@SuppressWarnings("static-access")
	@Override
	public void onTouchListView() {
		// TODO Auto-generated method stub
		bidirSldingLayout.listViewOnTouch = true;
	}  

}
