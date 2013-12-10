package com.myapp.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.myapp.R;

public class Register extends Activity {
	private Button r_submit;
	private EditText r_account; 
	private EditText r_password1;
	private EditText r_password2;
	private ImageButton r_cancel;
	private Spinner r_year;
	private Spinner r_month;
	private Spinner r_day;
	private ImageButton r_verify;
	
	private int[] image = {R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,
			R.drawable.pic6,R.drawable.pic7,R.drawable.pic8,R.drawable.pic9,R.drawable.pic10};
	private final String[] year = {"1999","2000","2001"};
	private final String[] month = {"1","2","3"};
	private final String[] day ={"1","2","3"};
	private ArrayAdapter<String> adapter_year;
	private ArrayAdapter<String> adapter_month;
	private ArrayAdapter<String> adapter_day;
	
	private String r_user_account;
	private String r_user_password1;
	private String r_user_password2;
	
	public static List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
//		r_submit = (Button)findViewById(R.id.b_submit);
//		r_account = (EditText)findViewById(R.id.et_account);
//		r_password1 = (EditText)findViewById(R.id.et_password1);
//		r_password2 = (EditText)findViewById(R.id.et_password2);
//		r_cancel = (ImageButton)findViewById(R.id.img_b_cancel);
//		r_year = (Spinner)findViewById(R.id.s_year);
//		r_month = (Spinner)findViewById(R.id.s_month);
//		r_day = (Spinner)findViewById(R.id.s_day);
//		r_verify = (ImageButton)findViewById(R.id.ib_verify);
//		
//		ButtonListener buttonListener = new ButtonListener();
//		
//
//		
//		r_submit.setOnClickListener(buttonListener);
//		r_cancel.setOnClickListener(buttonListener);
//		r_verify.setOnClickListener(buttonListener);
//
//		
//		
//		spinnerEvent();
		
//		setEvent();

	}
	
//	public void setEvent() {
//		for(int i=1;i<=12;i++) {
//			year[i-1]=;
//		}
//	}
	
	public void spinnerEvent() {
		adapter_year = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				year);
		adapter_month = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				month);
		adapter_day = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				day);
		
		r_year.setAdapter(adapter_year);
		r_month.setAdapter(adapter_month);
		r_day.setAdapter(adapter_day);
		
		r_year.setVisibility(View.VISIBLE);
		r_month.setVisibility(View.VISIBLE);
		r_day.setVisibility(View.VISIBLE);
	}

	
	class ButtonListener implements OnClickListener {
		@SuppressLint("NewApi")
		public void onClick(View v) {
			int id = v.getId();
			Intent intent = new Intent();
			switch (id) {
			case R.id.img_b_cancel:
				r_cancel.setImageResource(R.drawable.arrow_left_pressed);
				intent.setClass(Register.this,MainActivity.class);
				startActivity(intent);
				finish();
				break;
			case R.id.ib_verify:
				int i = (int)(Math.random()*10);
				r_verify.setImageResource(image[i]);
				break;
			case R.id.b_submit:
				r_user_account = r_account.getText().toString();
				r_user_password1 = r_password1.getText().toString();
				r_user_password2 = r_password2.getText().toString();
				if(r_user_password1.isEmpty() || r_user_password2.isEmpty() || r_user_account.isEmpty() ) {
					new Builder(Register.this).setMessage("��Ϣ����Ϊ��").show();
				}else {
					if(r_user_password1.equals(r_user_password2) ){
						if(r_user_password1.equals(r_user_account)) {
							new Builder(Register.this).setMessage("�˺����벻��һ��").show();
						}else {
							
							addList();
							
							Toast.makeText(Register.this, "ע��ɹ�", Toast.LENGTH_SHORT).show();
							intent.setClass(Register.this,MainActivity.class);
							startActivity(intent);
							finish();
						}
					}else{
					new Builder(Register.this).setMessage("���벻һ��").show();
					}
				}
				break;
				
			}
		}
	}
	
	public void addList() {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("userAccount", r_user_account);
		map.put("userPassword", r_user_password1);
		dataList.add(map);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
}
