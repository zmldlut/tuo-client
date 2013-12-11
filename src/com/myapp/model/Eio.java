package com.myapp.model;

import com.myapp.base.BaseModel;

public class Eio extends BaseModel {

	public final static String COL_ID = "id";
	public final static String COL_TYPEID = "typeid";
	public final static String COL_CLASSIFYID = "classifyid";
	public final static String COL_ICON = "icon";
	
	public final static String COL_TITLE = "title";
	public final static String COL_AUTHOR = "author";
	public final static String COL_QUESTIONCOUNT = "questioncount";
	
	public final static String COL_LEVELA = "levelA";
	public final static String COL_LEVELB = "levelB";
	public final static String COL_LEVELC = "levelC";
	public final static String COL_LEVELD = "levelD";
	
	public final static String COL_DIDCOUNT = "didcount";
	public final static String COL_PRAISECOUNT = "praisecount";
	public final static String COL_STAMPCOUNT = "stampcount";
	public final static String COL_PUBLISHTIME = "publishtime";
	
	private String id;
	private String typeid;
	private String classifyid;
	private String icon;
	private String title;
	private String author;
	private String questioncount;
	private String levelA;
	private String levelB;
	private String levelC;
	private String levelD;
	private String didcount;
	private String praisecount;
	private String stampcount;
	private String publishtime;
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTypeid() {
		return typeid;
	}


	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}


	public String getClassifyid() {
		return classifyid;
	}


	public void setClassifyid(String classifyid) {
		this.classifyid = classifyid;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getQuestioncount() {
		return questioncount;
	}


	public void setQuestioncount(String questioncount) {
		this.questioncount = questioncount;
	}


	public String getLevelA() {
		return levelA;
	}


	public void setLevelA(String levelA) {
		this.levelA = levelA;
	}


	public String getLevelB() {
		return levelB;
	}


	public void setLevelB(String levelB) {
		this.levelB = levelB;
	}


	public String getLevelC() {
		return levelC;
	}


	public void setLevelC(String levelC) {
		this.levelC = levelC;
	}


	public String getLevelD() {
		return levelD;
	}


	public void setLevelD(String levelD) {
		this.levelD = levelD;
	}


	public String getDidcount() {
		return didcount;
	}


	public void setDidcount(String didcount) {
		this.didcount = didcount;
	}


	public String getPraisecount() {
		return praisecount;
	}


	public void setPraisecount(String praisecount) {
		this.praisecount = praisecount;
	}


	public String getStampcount() {
		return stampcount;
	}


	public void setStampcount(String stampcount) {
		this.stampcount = stampcount;
	}


	public String getPublishtime() {
		return publishtime;
	}


	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}


	public Eio() {
		// TODO Auto-generated constructor stub
	}

}
