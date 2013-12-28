package com.myapp.ui;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.base.BaseAuth;
import com.myapp.base.BaseMessage;
import com.myapp.base.BaseUi;
import com.myapp.base.C;
import com.myapp.model.User;

@SuppressLint("NewApi")
public class MainActivity extends BaseUi {

	private String user_account;
	private String user_password;
	
	private EditText account;
	private EditText password;
	private Button register;
	private Button login;
	private TextView forget_password;
	
	private MyListener myListener;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// check if login
		if (BaseAuth.isLogin()) {
			this.forward(SurveyCenter.class);
		}
		setContentView(R.layout.activity_main);
		
		this.getWidget();
		this.setEvent();
	}
	
	void getWidget(){
		this.account = (EditText)findViewById(R.id.autotv_account);
		this.password = (EditText)findViewById(R.id.et_password);
		this.register = (Button)findViewById(R.id.b_register);
		this.login = (Button)findViewById(R.id.b_login);
		this.forget_password = (TextView)findViewById(R.id.tv_forget_password);
		
		this.myListener = new MyListener();
		
		this.user_account = this.account.getText().toString();
		this.user_password = this.password.getText().toString();
	}
	
	void setEvent(){
		this.register.setOnClickListener(myListener);
		this.login.setOnClickListener(myListener);
		this.forget_password.setOnClickListener(myListener);
	}
	
	class MyListener implements OnClickListener {
		public void onClick(View v) {
			int id = v.getId();
			
			switch (id) {
			case R.id.tv_forget_password:
				break;
			case R.id.b_login:
				doTaskLogin();

				break;
			case R.id.b_register:
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,Register.class);
				startActivity(intent);
				break;

			}
		}
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void doTaskLogin() {
		app.setLong(System.currentTimeMillis());
		this.user_account = this.account.getText().toString();
		this.user_password = this.password.getText().toString();
		if (this.user_account.isEmpty() || this.user_password.isEmpty()) {
			Toast.makeText(this, "The account or password is Empty!", Toast.LENGTH_SHORT).show();
		}else{
			HashMap<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("name", this.user_account);
			urlParams.put("pass", this.user_password);
			try {
				this.doTaskAsync(C.task.login, C.api.login, urlParams);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		switch (taskId) {
			case C.task.login:
				User user = null;
				// login logic
				try {
					user = (User) message.getResult("User");
					// login success
					if (user.getName() != null) {
						BaseAuth.setUser(user);
						BaseAuth.setLogin(true);
					// login fail
					} else {
						BaseAuth.setUser(user); // set sid
						BaseAuth.setLogin(false);
						toast(this.getString(R.string.msg_loginfail));
					}
				} catch (Exception e) {
					e.printStackTrace();
					toast(e.getMessage());
				}
				// login complete
				long startTime = app.getLong();
				long loginTime = System.currentTimeMillis() - startTime;
				Log.w("LoginTime", Long.toString(loginTime));
				// turn to index
				if (BaseAuth.isLogin()) {
					// start service
//					BaseService.start(this, NoticeService.class);
					// turn to index
					forward(SurveyCenter.class);
				}
				break;
		}
	}
	
	@Override
	public void onNetworkError (int taskId) {
		super.onNetworkError(taskId);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// other methods
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doFinish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
