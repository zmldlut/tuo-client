package com.myapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.R;
import com.myapp.manager.MyFragmentManager;

@SuppressLint({ "ValidFragment", "NewApi" })
public class HomeFragment extends Fragment {
	private Context context;
	private View view; 
	
	private FragmentManager fragmentManager;
	
	public void setFragmentManager(FragmentManager fragmentManager) {
		this.fragmentManager = fragmentManager;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		SurveyFragment survey_frag = new SurveyFragment(context);
		MyFragmentManager.surveyFragmentChange(fragmentManager,survey_frag);
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
