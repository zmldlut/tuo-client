package com.myapp.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.myapp.R;

public class StartActivity extends Activity {
	boolean isFirstIn;

    private static final int GO_HOME = 0;
    private static final int GO_GUIDE = 1;
    private static final long SPLASH_DELAY_MILLIS = 3000;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    @SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
        	Intent intent = new Intent();
            switch (msg.what) {
            case GO_HOME:
                intent.setClass(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case GO_GUIDE:
            	intent.setClass(StartActivity.this, Guide.class);
                startActivity(intent);
                finish();
                break;
            }
            super.handleMessage(msg);
        }
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		init();
	}

	private void init() {
		SharedPreferences preferences = getSharedPreferences(
                SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		if (!isFirstIn) {
           
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
        }
	}
}
