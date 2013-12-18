package com.myapp.view;

import com.myapp.R;
import com.myapp.manager.MyFragmentManager;
import com.myapp.ui.SurveyCenter;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

@SuppressLint({ "ValidFragment", "NewApi" })
public class HomeFragment extends Fragment {
	private Context context;
	private View view; 
	


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		SurveyFragment survey_frag = new SurveyFragment(context);
		MyFragmentManager.surveyFragmentChange(getFragmentManager(),survey_frag);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.home_fragment, container, false);
		return view;
	}	

	public HomeFragment(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
}
