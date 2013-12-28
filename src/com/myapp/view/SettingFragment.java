package com.myapp.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.myapp.R;
import com.myapp.ui.About;
import com.myapp.ui.Client;
import com.myapp.ui.ContactUs;


@SuppressLint({ "ValidFragment", "NewApi" })
public class SettingFragment extends Fragment implements OnClickListener {
	private Context context;
	private View view;
	
	private Button user_information;
	private Button about;
	private Button contact;
	private Button logout;
	
	private FragmentManager fragmentManager;
	
	public void setFragmentManager(FragmentManager fragmentManager) {
		this.fragmentManager = fragmentManager;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		getWidgetId();
		setClickEvent();

	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch(v.getId()){
		case R.id.b_contact:

			intent.setClass(getActivity(), ContactUs.class);
			startActivity(intent);
			break;
		case R.id.b_about:

			intent.setClass(getActivity(), About.class);
			startActivity(intent);
			break;
		case R.id.b_user_information:

			intent.setClass(getActivity(), Client.class);
			startActivity(intent);
			break;
		case R.id.b_logout:
			
			getActivity().finish();
			break;
		}
	}
	
	public void getWidgetId(){
		contact = (Button)view.findViewById(R.id.b_contact);
		user_information = (Button)view.findViewById(R.id.b_user_information);
		about = (Button)view.findViewById(R.id.b_about);
		logout = (Button)view.findViewById(R.id.b_logout);
	}
	public void setClickEvent() {
		logout.setOnClickListener(this);
		contact.setOnClickListener(this);
		user_information.setOnClickListener(this);
		about.setOnClickListener(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.view = inflater.inflate(R.layout.setting_fragment, container, false);
		return view;
	}

	public SettingFragment(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
}
