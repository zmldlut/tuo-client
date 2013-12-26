package com.myapp.base;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.myapp.util.AppUtil;

public class BaseFragmentHandler extends Handler {
	
	protected BaseFragment fragment;
	
	public BaseFragmentHandler (BaseFragment fragment) {
		this.fragment = fragment;
	}
	
	public BaseFragmentHandler (Looper looper) {
		super(looper);
	}
	
	@Override
	public void handleMessage(Message msg) {
		try {
			int taskId;
			String result;
			switch (msg.what) {
				case BaseTask.TASK_COMPLETE:
					fragment.hideLoadBar();
					taskId = msg.getData().getInt("task");
					result = msg.getData().getString("data");
					if (result != null) {
						fragment.onTaskComplete(taskId, AppUtil.getMessage(result));
					} else if (!AppUtil.isEmptyInt(taskId)) {
						fragment.onTaskComplete(taskId);
					} else {
						fragment.toast(C.err.message);
					}
					break;
				case BaseTask.NETWORK_ERROR:
					fragment.hideLoadBar();
					taskId = msg.getData().getInt("task");
					fragment.onNetworkError(taskId);
					break;
				case BaseTask.SHOW_LOADBAR:
					fragment.showLoadBar();
					break;
				case BaseTask.HIDE_LOADBAR:
					fragment.hideLoadBar();
					break;
				case BaseTask.SHOW_TOAST:
					fragment.hideLoadBar();
					result = msg.getData().getString("data");
					fragment.toast(result);
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			fragment.hideLoadBar();
			fragment.toast(e.getMessage());
		}
	}
	
}