package com.myapp.model;

import com.myapp.base.BaseModel;

public class Classify extends BaseModel {
	
	public final static String COL_ID = "id";
	public final static String COL_NAME = "name";
	public final static String COL_ICON = "icon";
	public final static String COL_UPTIME = "uptime";
	
	private String id;
	private String name;
	private String icon;
	private String uptime;
	
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getIcon() {
		return icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}



	public String getUptime() {
		return uptime;
	}



	public void setUptime(String uptime) {
		this.uptime = uptime;
	}



	public Classify() {
		// TODO Auto-generated constructor stub
	}

}
