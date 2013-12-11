package com.myapp.model;

import com.myapp.base.BaseModel;

public class Notice extends BaseModel {

	public final static String COL_ID = "id";
	public final static String COL_fromuserid = "fromuserid";
	public final static String COL_FROMNAME = "fromname";
	public final static String COL_USERID = "userid";
	public final static String COL_USERNAME = "username";
	public final static String COL_CONTENT = "content";
	public final static String COL_TYPE = "type";
	public final static String COL_STATUS = "status";
	public final static String COL_UPTIME = "uptime";
	
	private String id;
	private String fromuserid;
	private String fromname;
	private String userid;
	private String username;
	private String content;
	private String type;
	private String status;
	private String uptime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromuserid() {
		return fromuserid;
	}

	public void setFromuserid(String fromuserid) {
		this.fromuserid = fromuserid;
	}

	public String getFromname() {
		return fromname;
	}

	public void setFromname(String fromname) {
		this.fromname = fromname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public Notice() {
		// TODO Auto-generated constructor stub
	}

}
