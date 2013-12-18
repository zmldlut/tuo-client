package com.myapp.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.myapp.R;
import com.myapp.manager.MyFragmentManager;
import com.myapp.view.MicroBlogFragment;

@SuppressLint("NewApi")
public class UserHomepage extends Activity {
	private ImageButton last_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_homepage);
		last_view = (ImageButton)findViewById(R.id.b_last_view);
		
		last_view.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
		MicroBlogFragment microBlog_frag = new MicroBlogFragment(UserHomepage.this);
		MyFragmentManager.microBlogFragmentChange(getFragmentManager(),microBlog_frag);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_homepage, menu);
		return true;
	}

}
