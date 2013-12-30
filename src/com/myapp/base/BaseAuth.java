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
		User user = User.getInstance();
		user.setId(mc.getId());
		user.setSid(mc.getSid());
		user.setName(mc.getName());
		user.setSign(mc.getSign());
		user.setFace(mc.getFace());
		user.setFaceurl(mc.getFaceurl());
		user.setSex(mc.getSex());
		user.setLocation(mc.getLocation());
		user.setBirthday(mc.getBirthday());
		user.setPass(mc.getPass());
		user.setScore(mc.getScore());
	}
	
	static public User getUser () {
		return User.getInstance();
	}
}