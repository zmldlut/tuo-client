package com.myapp.ui;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.myapp.R;
import com.myapp.base.BaseMessage;
import com.myapp.base.BaseUi;
import com.myapp.base.C;

public class ModifyInformation extends BaseUi implements OnClickListener {
	private EditText content;
	private Button save;
	private ImageButton myReturn;
	private String str;
	private String key;
	private int code;
	
	private static final int codeNickName = 0;
	private static final int codeSign = 1;
	private static final int codeLocation = 2;
	private static final int codeSex = 3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_information);
		
		getWidgetId();
		setClickEvent();
		initView();
	}
	
	public void getWidgetId(){
		content = (EditText)findViewById(R.id.et_modify);
		save = (Button)findViewById(R.id.b_modify_save);
		myReturn = (ImageButton)findViewById(R.id.ib_modify_to_setting);
	}
	public void setClickEvent() {
		save.setOnClickListener(this);
		myReturn.setOnClickListener(this);
	}
	
	public void initView(){
		Bundle bundle = this.getIntent().getExtras();
		code = bundle.getInt("code");
		switch(code){
		case codeNickName:
			str = bundle.getString("nickName");
			key = "name";
			break;
		case codeSign:
			str = bundle.getString("sign");
			key = "sign";
			break;
		case codeLocation:
			str = bundle.getString("location");
			key = "location";
			break;
		case codeSex:
			str = bundle.getString("sex");
			key = "sex";
			break;
		}
		content.setText(str);
	}
	
	public void doTaskUpdateUserInfo(){
		HashMap<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("key", key);
		urlParams.put("val", content.getText().toString());
		str = content.getText().toString();
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
		switch(v.getId()){
		case R.id.ib_modify_to_setting:
			myReturn.setImageResource(R.drawable.arrow_left_pressed);
			Intent data=new Intent();  
            data.putExtra(""+ code, str);  
            //请求代码可以自己设置，这里设置成20  
            setResult(code, data); 
			finish();
			break;
		case R.id.b_modify_save:
			doTaskUpdateUserInfo();
			break;
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modify_information, menu);
		return true;
	}
}
