package com.myapp.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class CenterLinearLayout extends LinearLayout {


    View.OnTouchListener mGestureListener;  
  
    private String TAG = "CenterLinearLayout";

    public boolean mIsBeingDragged = false;
    private float mLastMotionX = 0.0f;
    private float mLastMotionY = 0.0f;
    private static float mTouchSlop = 20.0f;
    private Context context;
    private OnTouchListViewListener mOnTouchLister;
  

	public CenterLinearLayout(Context context) {
		super(context);
		this.context = context;
		try {
			mOnTouchLister = (OnTouchListViewListener)this.context;
		} catch (ClassCastException e) {
			throw new ClassCastException(((Activity)context).toString() + "must implement OnbtnSendClickListener");//这条表示，你不在Activity里实现这个接口的话，我就要抛出异常咯。知道下一步该干嘛了吧？
		}
		
		// TODO Auto-generated constructor stub
//		mGestureDetector = new GestureDetector(new MySimpleGesture());  
	}

	public CenterLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		try {
			mOnTouchLister = (OnTouchListViewListener)this.context;
		} catch (ClassCastException e) {
			throw new ClassCastException(((Activity)context).toString() + "must implement OnbtnSendClickListener");//这条表示，你不在Activity里实现这个接口的话，我就要抛出异常咯。知道下一步该干嘛了吧？
		}
		// TODO Auto-generated constructor stub
//		mGestureDetector = new GestureDetector(new MySimpleGesture());  
	}

	public CenterLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		try {
			mOnTouchLister = (OnTouchListViewListener)this.context;
		} catch (ClassCastException e) {
			throw new ClassCastException(((Activity)context).toString() + "must implement OnbtnSendClickListener");//这条表示，你不在Activity里实现这个接口的话，我就要抛出异常咯。知道下一步该干嘛了吧？
		}
		// TODO Auto-generated constructor stub
//		mGestureDetector = new GestureDetector(new MySimpleGesture());  
	}
	
	public interface OnTouchListViewListener {
		public void onTouchListView();//接口中定义一个方法
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		final int action = ev.getAction();
		final float x = ev.getRawX();
		final float y = ev.getRawY();
		Log.i(TAG, "CenterLinearLayout onInterceptTouchEvent  "+action);
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
			mLastMotionY = y;
			mIsBeingDragged = false;
			break;

		case MotionEvent.ACTION_MOVE:
			final float dx = x - mLastMotionX;
			final float xDiff = Math.abs(dx);
			final float yDiff = Math.abs(y - mLastMotionY);
			if (xDiff > mTouchSlop && xDiff > yDiff) {
				mIsBeingDragged = true;
				mLastMotionX = x;
				mOnTouchLister.onTouchListView();
			}
			break;
		}
		return mIsBeingDragged;
	}

 	@Override
 	public boolean onTouchEvent(MotionEvent event) {
 		Log.i(TAG, "CenterLinearLayout onTouchEvent  "+event.getAction());
 		Log.i(TAG, "CenterLinearLayout onTouchEvent  "+super.onTouchEvent(event));
 		return super.onTouchEvent(event);
 	}
}
