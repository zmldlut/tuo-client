package com.myapp.ui;


import java.util.Calendar;
import java.util.HashMap;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.myapp.R;
import com.myapp.base.BaseAuth;
import com.myapp.base.BaseMessage;
import com.myapp.base.BaseUi;
import com.myapp.base.C;
import com.myapp.model.User;

public class Client extends BaseUi implements OnClickListener {
	
	private Button nickname;
	private Button sign;
	private Button area;
	private Button sex;
	private Button birth;
	private ImageButton myReturn;
	private User user;
	
	private static final int codeNickName = 0;
	private static final int codeSign = 1;
	private static final int codeLocation = 2;
	private static final int codeSex = 3;
	
	private String strDate="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client);
		
		user = BaseAuth.getUser();
		
		getWidgetId();
		setClickEvent();
		initView();
	}
	
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		Bundle bundle;
		String content;
		switch(requestCode){
		case codeNickName:

			bundle = data.getExtras();
	        content = bundle.getString(""+codeNickName);
	        nickname.setText("昵称："+ content);
			break;
		case codeSign:

			bundle = data.getExtras();
	        content = bundle.getString(""+codeSign);
	        sign.setText("个性签名："+ content);
			break;
		case codeLocation:
			
			bundle = data.getExtras();
	        content = bundle.getString(""+codeLocation);
	        area.setText("地区："+ content);
			break;
		case codeSex:
			
			bundle = data.getExtras();
	        content = bundle.getString(""+codeSex);
	        sex.setText("性别："+ content);
			break;
		}
	 }
	
	public void getWidgetId(){
		nickname = (Button)findViewById(R.id.b_nickname);
		sign = (Button)findViewById(R.id.b_my_sign);
		area = (Button)findViewById(R.id.b_area);
		sex = (Button)findViewById(R.id.b_sex);
		birth = (Button)findViewById(R.id.b_birth);
		myReturn = (ImageButton)findViewById(R.id.ib_client_to_setting);
	}
	public void setClickEvent() {
		nickname.setOnClickListener(this);
		sign.setOnClickListener(this);
		area.setOnClickListener(this);
		sex.setOnClickListener(this);
		birth.setOnClickListener(this);
		myReturn.setOnClickListener(this);
	}
	
	public void initView(){
		String nameStr = nickname.getText() + user.getName();
		String signStr = sign.getText() + user.getSign();
		String areaStr = area.getText() + user.getLocation();
		String sexStr  = sex.getText() + user.getSex();
		String birthStr = birth.getText() + user.getBirthday();
		nickname.setText(nameStr);
		sign.setText(signStr);
		area.setText(areaStr);
		sex.setText(sexStr);
		birth.setText(birthStr);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		switch(v.getId()){
		case R.id.ib_client_to_setting:
			myReturn.setImageResource(R.drawable.arrow_left_pressed);
			finish();
			break;
		case R.id.b_nickname:
			
			intent.setClass(this, ModifyInformation.class);
			bundle.clear();
			bundle.putInt("code", codeNickName);
			bundle.putString("nickName", user.getName());
			intent.putExtras(bundle);
			startActivityForResult(intent, codeNickName);
			break;
		case R.id.b_my_sign:
			
			intent.setClass(this, ModifyInformation.class);
			bundle.clear();
			bundle.putInt("code", codeSign);
			bundle.putString("sign", user.getSign());
			intent.putExtras(bundle);
			startActivityForResult(intent, codeSign);
			break;
		case R.id.b_area:

			intent.setClass(this, ModifyInformation.class);
			bundle.clear();
			bundle.putInt("code", codeLocation);
			bundle.putString("location", user.getLocation());
			intent.putExtras(bundle);
			startActivityForResult(intent, codeLocation);
			break;
		case R.id.b_sex:

			intent.setClass(this, ModifyInformation.class);
			bundle.clear();
			bundle.putInt("code", codeSex);
			bundle.putString("sex", user.getSex());
			intent.putExtras(bundle);
			startActivityForResult(intent, codeSex);
			break;
		case R.id.b_birth:
			operateDate();
			break;	
		}
	}
	
	public void operateDate(){
		Calendar cal = Calendar.getInstance();
		DatePickerDialog.OnDateSetListener dateListener = 
			new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO Auto-generated method stub
					monthOfYear+=1;
					doTaskUpdateUserInfo(year + "-" + monthOfYear + "-" + dayOfMonth);
					birth.setText("出生日期：" + year + "-" + monthOfYear + "-" + dayOfMonth);
				}
		};
		
		Dialog dateDialog = new DatePickerDialog(Client.this,
				dateListener,
				cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		dateDialog.show();
	}
	
	public void doTaskUpdateUserInfo(String date){
		HashMap<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("key", "birthday");
		urlParams.put("val", date);
		strDate = date;
		try {
			this.doTaskAsync(C.task.updateUserInfo, C.api.updateUserInfo, urlParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message){
		switch (taskId) {
		case C.task.updateUserInfo:
			try {
				String codeReturn = message.getCode();
				if(codeReturn.equals("10000")){
					toast("数据修改成功！");
					birth.setText("出生日期：" + strDate);
				}
			} catch (Exception e) {
				e.printStackTrace();
				toast(e.getMessage());
			}
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client, menu);
		return true;
	}
}
