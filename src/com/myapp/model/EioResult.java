package com.myapp.model;

import com.myapp.base.BaseModel;

public class EioResult extends BaseModel {

	public final static String COL_ID = "id";
	public final static String COL_EIOID = "eioid";
	public final static String COL_USERID = "userid";
	public final static String COL_RESULT = "result";
	public final static String COL_UPTIME = "uptime";
	
	private String id;
	private String eioid;
	private String userid;
	private String result;
	private String uptime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEioid() {
		return eioid;
	}

	public void setEioid(String eioid) {
		this.eioid = eioid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public EioResult() {
		// TODO Auto-generated constructor stub
	}

}
