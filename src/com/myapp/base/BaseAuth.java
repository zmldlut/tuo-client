package com.myapp.base;

import com.myapp.model.User;


public class BaseAuth {
	
	static public boolean isLogin () {
		User user = User.getInstance();
		if (user.isLogin() == true) {
			return true;
		}
		return false;
	}
	
	static public void setLogin (Boolean status) {
		User user = User.getInstance();
		user.setLogin(status);
	}
	
	static public void setUser (User mc) {
		User customer = User.getInstance();
		customer.setId(mc.getId());
		customer.setSid(mc.getSid());
		customer.setName(mc.getName());
		customer.setSign(mc.getSign());
		customer.setFace(mc.getFace());
	}
	
	static public User getUser () {
		return User.getInstance();
	}
}