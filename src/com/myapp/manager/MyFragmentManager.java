package com.myapp.manager;

import com.myapp.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;


@SuppressLint("NewApi")
public class MyFragmentManager {

	public MyFragmentManager() {
		// TODO Auto-generated constructor stub
	}

	public static void myFragment(FragmentManager fragmanager,Fragment fm){
		fragmanager.beginTransaction().replace(R.id.fragment_layout, fm).commit();
	}
	
	public static void surveyFragmentChange(FragmentManager fragmanager,Fragment fm){
		fragmanager.beginTransaction().replace(R.id.home_fragment_layout, fm).commit();
	}
	
	public static void microBlogFragmentChange(FragmentManager fragmanager,Fragment fm){
		fragmanager.beginTransaction().replace(R.id.user_fragment_layout, fm).commit();
	}	
	
	public static void microBlogFriendsFragmentChange(FragmentManager fragmanager,Fragment fm){
		fragmanager.beginTransaction().replace(R.id.friends_fragment_layout, fm).commit();
	}	
}
