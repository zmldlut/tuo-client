package com.myapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.myapp.R;

public class ModifyInformation extends Activity implements OnClickListener {
	private TextView title;
	private Button save;
	private ImageButton myReturn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_information);
		
		getWidgetId();
		setClickEvent();
	}
	

	
	public void getWidgetId(){
		title = (TextView)findViewById(R.id.tv_modify_title);
		save = (Button)findViewById(R.id.b_modify_save);
		myReturn = (ImageButton)findViewById(R.id.ib_modify_to_setting);
	}
	public void setClickEvent() {
		title.setOnClickListener(this);
		save.setOnClickListener(this);
		myReturn.setOnClickListener(this);
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch(v.getId()){
		case R.id.ib_modify_to_setting:
			myReturn.setImageResource(R.drawable.arrow_left_pressed);
			finish();
			break;
		case R.id.b_modify_save:
			finish();
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
