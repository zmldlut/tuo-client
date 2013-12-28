package com.myapp.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.base.BaseMessage;
import com.myapp.base.BaseUi;
import com.myapp.base.C;
import com.myapp.model.User;

@SuppressLint("NewApi")
public class Register extends BaseUi {
	private Button r_submit;
	private EditText r_account; 
	private EditText r_password1;
	private EditText r_password2;
	
	private RadioGroup radioGroup;
	private RadioButton rdb_boy;
	private RadioButton rdb_gril;
//	private Button btn_date;
//	private EditText edit_verify;
	
	private ImageButton r_cancel;
	private Button r_date;
	private ImageButton r_verify;
	
	ButtonListener buttonListener;
	
	private int[] image = {R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,
			R.drawable.pic6,R.drawable.pic7,R.drawable.pic8,R.drawable.pic9,R.drawable.pic10};
	
	private String r_user_account;
	private String r_user_password1;
	private String r_user_password2;
	private String r_user_sex = "1";
	private User newUser;
	
	public static List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		newUser = new User();
		
		getWidget();
		setEvent();
	}
	
	public void getWidget(){
		r_submit = (Button)findViewById(R.id.b_submit);
		r_account = (EditText)findViewById(R.id.et_account);
		r_password1 = (EditText)findViewById(R.id.et_password1);
		r_password2 = (EditText)findViewById(R.id.et_password2);
		r_cancel = (ImageButton)findViewById(R.id.img_b_cancel);
		
		radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
		rdb_boy = (RadioButton)findViewById(R.id.r_boy);
		rdb_gril = (RadioButton)findViewById(R.id.r_girl);
		
		r_date = (Button)findViewById(R.id.b_date);
		r_verify = (ImageButton)findViewById(R.id.ib_verify);
		
		buttonListener = new ButtonListener();
	}
	
	public void setEvent() {
		r_submit.setOnClickListener(buttonListener);
		r_cancel.setOnClickListener(buttonListener);
		r_verify.setOnClickListener(buttonListener);
		r_date.setOnClickListener(buttonListener);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
             @Override
             public void onCheckedChanged(RadioGroup group, int checkedId) {
            	 // TODO Auto-generated method stub 
                 if(checkedId==rdb_boy.getId())
                 { 
                	 r_user_sex = "1";
                 } 
                 else if(checkedId==rdb_gril.getId()) 
                 { 
                	 r_user_sex = "0";
                 }   
             }
         });
	}
	
	@SuppressLint("NewApi")
	class ButtonListener implements OnClickListener {
		
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.b_date:
				operateDate();
				break;
			case R.id.img_b_cancel:
				r_cancel.setImageResource(R.drawable.arrow_left_pressed);
				finish();
				break;
			case R.id.ib_verify:
				int i = (int)(Math.random()*10);
				r_verify.setImageResource(image[i]);
				break;
			case R.id.b_submit:
				doTaskRegister();
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
							r_date.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
						}
			};
			
			Dialog dateDialog = new DatePickerDialog(Register.this,
					dateListener,
					cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
			dateDialog.show();
		}
	}
	
	public void doTaskRegister(){
		r_user_account = r_account.getText().toString();
		r_user_password1 = r_password1.getText().toString();
		r_user_password2 = r_password2.getText().toString();
		if(r_user_password1.isEmpty() || r_user_password2.isEmpty() || r_user_account.isEmpty() ) {
			Toast.makeText(Register.this, "注册信息不能为空", Toast.LENGTH_SHORT).show();
		}else {
			if(r_user_password1.equals(r_user_password2) ){
				if(r_user_password1.equals(r_user_account)) {
					Toast.makeText(Register.this, "账号密码不能相同", Toast.LENGTH_SHORT).show();
				}else {
					newUser.setName(r_user_account);
					newUser.setPass(r_user_password1);
					newUser.setSign("a");
					newUser.setFace("a");
					newUser.setSex(r_user_sex);
					newUser.setBirthday(r_date.getText().toString());
//					Toast.makeText(Register.this, r_date.getText().toString(), Toast.LENGTH_SHORT).show();
					
					newUser.setLocation("a");
					newUser.setEiocount("0");
					newUser.setFanscount("0");
					newUser.setScore("0");
					
//					HashMap<String, HashMap<String, String>> registerParams = new HashMap<String, HashMap<String, String>>();
					HashMap<String, String> urlParams = new HashMap<String, String>();
					urlParams.put("name", newUser.getName());
					urlParams.put("pass", newUser.getPass());
					urlParams.put("sign", newUser.getSign());
					urlParams.put("face", newUser.getFace());
					urlParams.put("sex", newUser.getSex());
					urlParams.put("birthday", newUser.getBirthday());
					urlParams.put("location", newUser.getLocation());
					urlParams.put("eiocount", newUser.getEiocount());
					urlParams.put("fanscount", newUser.getFanscount());
					urlParams.put("score", newUser.getScore());
//					registerParams.put("user", urlParams);
					
					try {
						this.doTaskAsync(C.task.register, C.api.register, urlParams);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else{
				Toast.makeText(Register.this, "密码不一致", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		switch (taskId) {
			case C.task.register:
				if(message.getCode().equals("10000")){
					Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
					finish();
				}
				break;
		}
	}
	
	@Override
	public void onNetworkError (int taskId) {
		super.onNetworkError(taskId);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
}
