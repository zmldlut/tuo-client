package com.myapp.ui;


import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.myapp.R;
import com.myapp.base.BaseAuth;
import com.myapp.base.BaseMessage;
import com.myapp.base.BaseUi;
import com.myapp.base.C;
import com.myapp.model.User;

public class MainActivity extends BaseUi {

	private AutoCompleteTextView account;
	private EditText passwd;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// check if login
		if (BaseAuth.isLogin()) {
			this.forward(Register.class);
		}
	
		setContentView(R.layout.activity_main);
		
		account = (AutoCompleteTextView)findViewById(R.id.autotv_account);
		passwd = (EditText)findViewById(R.id.et_password);
		
		Button register = (Button)findViewById(R.id.b_register);
		Button switch_user = (Button)findViewById(R.id.b_switch_user);
		Button login = (Button)findViewById(R.id.b_login);
		Button forget_password = (Button)findViewById(R.id.b_forget_password);
		
		ButtonListener buttonListener = new ButtonListener();
		
		register.setOnClickListener(buttonListener);
		switch_user.setOnClickListener(buttonListener);
		login.setOnClickListener(buttonListener);
		forget_password.setOnClickListener(buttonListener);
	}
	
	class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			int id = v.getId();
			Intent intent = new Intent();
			
			switch (id) {
			case R.id.b_forget_password:
				break;
			case R.id.b_login:	
				doTaskLogin();
				break;
			case R.id.b_register:
				intent.setClass(MainActivity.this,Register.class);
				startActivity(intent);
				finish();
				break;
			case R.id.b_switch_user:
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
		if (account.length() > 0 && passwd.length() > 0) {
			HashMap<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("name", account.getText().toString());
			urlParams.put("pass", passwd.getText().toString());
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
					forward(Register.class);
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
