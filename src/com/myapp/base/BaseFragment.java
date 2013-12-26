package com.myapp.base;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Toast;


@SuppressLint("ValidFragment")
public class BaseFragment extends Fragment {

	protected BaseUi baseUi;
	protected BaseFragmentHandler handler;
	protected BaseTaskPool taskPool;
	
	public BaseFragment() {
		// TODO Auto-generated constructor stub
		
	}
	
	public BaseFragment(Context context) {
		// TODO Auto-generated constructor stub
		this.baseUi = (BaseUi) context;
		this.handler = new BaseFragmentHandler(this);
		this.taskPool = new BaseTaskPool(baseUi);
	}
	
	public void doTaskAsync (int taskId, int delayTime) {
		this.showLoadBar();
		taskPool.addTask(taskId, new BaseTask(){
			@Override
			public void onComplete () {
				sendMessage(BaseTask.TASK_COMPLETE, this.getId(), null);
			}
			@Override
			public void onError (String error) {
				sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
			}
		}, delayTime);
	}
	
	public void doTaskAsync (int taskId, String taskUrl) {
		this.showLoadBar();
		taskPool.addTask(taskId, taskUrl, new BaseTask(){
			@Override
			public void onComplete (String httpResult) {
				sendMessage(BaseTask.TASK_COMPLETE, this.getId(), httpResult);
			}
			@Override
			public void onError (String error) {
				sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
			}
		}, 0);
	}
	
	public void doTaskAsync (int taskId, String taskUrl, HashMap<String, String> taskArgs) {
		this.showLoadBar();
		taskPool.addTask(taskId, taskUrl, taskArgs, new BaseTask(){
			@Override
			public void onComplete (String httpResult) {
				sendMessage(BaseTask.TASK_COMPLETE, this.getId(), httpResult);
			}
			@Override
			public void onError (String error) {
				sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
			}
		}, 0);
	}
	
	public void sendMessage (int what, int taskId, String data) {
		Bundle b = new Bundle();
		b.putInt("task", taskId);
		b.putString("data", data);
		Message m = new Message();
		m.what = what;
		m.setData(b);
		handler.sendMessage(m);
	}
	
	public void onTaskComplete(int taskId, BaseMessage message){

	}
	
	public void onTaskComplete(int taskId){

	}
	
	public void onNetworkError (int taskId) {
		toast(C.err.network);
	}
	
	public void toast (String msg) {
		Toast.makeText(baseUi, msg, Toast.LENGTH_SHORT).show();
	}
	
	public void hideLoadBar(){
		baseUi.hideLoadBar();
	}
	
	public void showLoadBar(){
		baseUi.showLoadBar();
	}

}
