package com.myapp.ui;


import java.util.Calendar;

import android.app.Activity;
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

public class Client extends Activity implements OnClickListener {
	
	private Button nickname;
	private Button sign;
	private Button area;
	private Button sex;
	private Button birth;
	private ImageButton myReturn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client);
		
		getWidgetId();
		setClickEvent();
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
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch(v.getId()){
		case R.id.ib_client_to_setting:
			myReturn.setImageResource(R.drawable.arrow_left_pressed);
			finish();
			break;
		case R.id.b_nickname:

			intent.setClass(this, ModifyInformation.class);
			startActivity(intent);
			break;
		case R.id.b_my_sign:

			intent.setClass(this, ModifyInformation.class);
			startActivity(intent);
			break;
		case R.id.b_area:

			intent.setClass(this, ModifyInformation.class);
			startActivity(intent);
			break;
		case R.id.b_sex:

			intent.setClass(this, ModifyInformation.class);
			startActivity(intent);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client, menu);
		return true;
	}

}
