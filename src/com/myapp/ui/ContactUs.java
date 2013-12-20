package com.myapp.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.myapp.R;

public class ContactUs extends Activity implements OnClickListener {
	private ImageButton myReturn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_us);
		
		getWidgetId();
		setClickEvent();
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch(v.getId()){
		case R.id.ib_contactus_to_setting:
			myReturn.setImageResource(R.drawable.arrow_left_pressed);
			finish();
			break;

		}
	}
	public void getWidgetId(){
		myReturn = (ImageButton)findViewById(R.id.ib_contactus_to_setting);
	}
	public void setClickEvent() {
		myReturn.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_us, menu);
		return true;
	}

}
