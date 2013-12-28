package com.myapp.ui;


import java.util.HashMap;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.myapp.R;
import com.myapp.base.BaseAuth;
import com.myapp.base.BaseMessage;
import com.myapp.base.BaseUi;
import com.myapp.base.C;

public class ContactUs extends BaseUi implements OnClickListener {
	private ImageButton myReturn;
	private EditText edtFeedBack;
	private Button btnSub;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_us);
		
		getWidgetId();
		setClickEvent();
	}
	
	public void doTaskFeedBack(){
		HashMap<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("userid", BaseAuth.getUser().getId());
		urlParams.put("username", BaseAuth.getUser().getName());
		urlParams.put("content", edtFeedBack.getText().toString());
		
		try {
			this.doTaskAsync(C.task.feedBack, C.api.feedBack, urlParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message){
		switch (taskId) {
		case C.task.feedBack:
			try {
				String codeReturn = message.getCode();
				if(codeReturn.equals("10000")){
					toast("您的反馈意见我们意见收到，谢谢您的反馈！");
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
		switch(v.getId()){
		case R.id.ib_contactus_to_setting:
			myReturn.setImageResource(R.drawable.arrow_left_pressed);
			finish();
			break;
		case R.id.btn_sub:
			doTaskFeedBack();
		}
	}
	public void getWidgetId(){
		myReturn = (ImageButton)findViewById(R.id.ib_contactus_to_setting);
		edtFeedBack = (EditText)findViewById(R.id.editText_feedback);
		btnSub = (Button)findViewById(R.id.btn_sub);
	}
	public void setClickEvent() {
		myReturn.setOnClickListener(this);
		btnSub.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_us, menu);
		return true;
	}

}
