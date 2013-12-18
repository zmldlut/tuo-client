package com.myapp.view;

import com.myapp.R;
import com.myapp.manager.MyFragmentManager;
import com.myapp.ui.SurveyCenter;
import com.myapp.ui.UserHomepage;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint({ "ValidFragment", "NewApi" })
public class FriendsFragment extends Fragment {

	private Context context;
	private View view;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		MicroBlogFragment microBlog_frag = new MicroBlogFragment(context);
		MyFragmentManager.microBlogFriendsFragmentChange(getFragmentManager(),microBlog_frag);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.friends_fragment, container, false);
		return view;
	}	

	public FriendsFragment(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
}
