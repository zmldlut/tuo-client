package com.myapp.model;

import java.io.Serializable;

import com.myapp.base.BaseModel;

public class Fans extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 964700345969804553L;
	
	// model columns
	public final static String COL_ID = "id";
	public final static String COL_NAME = "name";
	public final static String COL_SIGN = "sign";
	public final static String COL_FACE = "face";
	public final static String COL_FACEURL = "faceurl";
	public final static String COL_SEX = "sex";
	public final static String COL_BIRTHDAY = "birthday";
	public final static String COL_LOCATION = "location";
	public final static String COL_EIOCOUNT = "eiocount";
	public final static String COL_FANSCOUNT = "fanscount";
	public final static String COL_SCORE = "score";
	
	private String id;
	private String name;
	private String sign;
	private String face;
	private String faceurl;
	private String sex;
	private String birthday;
	private String location;
	private String eiocount;
	private String fanscount;
	private String score;
	
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getFaceurl() {
		return faceurl;
	}

	public void setFaceurl(String faceurl) {
		this.faceurl = faceurl;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEiocount() {
		return eiocount;
	}

	public void setEiocount(String eiocount) {
		this.eiocount = eiocount;
	}

	public String getFanscount() {
		return fanscount;
	}

	public void setFanscount(String fanscount) {
		this.fanscount = fanscount;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Fans() {
		// TODO Auto-generated constructor stub
	}

}
