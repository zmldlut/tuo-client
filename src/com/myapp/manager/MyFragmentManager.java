package com.myapp.manager;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.myapp.R;


@SuppressLint("NewApi")
public class MyFragmentManager {

	public MyFragmentManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static void myChangeFragment(FragmentManager fragmanager,Fragment fm){
		fragmanager.beginTransaction().replace(R.id.fragment_layout, fm).commit();
	}

	public static void myFragment(FragmentManager fragmanager,Fragment fm){
		fragmanager.beginTransaction().replace(R.id.fragment_layout, fm).commit();
	}
	
	public static void surveyFragmentChange(FragmentManager fragmentManager,Fragment fm){
		fragmentManager.beginTransaction().replace(R.id.home_fragment_layout, fm).commit();
	}
	
	public static void microBlogFragmentChange(FragmentManager fragmanager,Fragment fm){
		fragmanager.beginTransaction().replace(R.id.user_fragment_layout, fm).commit();
	}	
	
	public static void microBlogFriendsFragmentChange(FragmentManager fragmanager,Fragment fm){
		fragmanager.beginTransaction().replace(R.id.friends_fragment_layout, fm).commit();
	}

}
