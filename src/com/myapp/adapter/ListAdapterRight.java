package com.myapp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.R;
import com.myapp.model.ListRightInfo;

public class ListAdapterRight extends BaseAdapter {
	
	private LayoutInflater mInflater;
	public List<ListRightInfo> mList;
	
	public ListAdapterRight() {
		// TODO Auto-generated constructor stub
	}
	
	public ListAdapterRight(Context pContext, List<ListRightInfo> pList) {
		mInflater = LayoutInflater.from(pContext);
		if (pList != null) {
			mList = pList;
		} else {
			mList = new ArrayList<ListRightInfo>();
		}
	}
	
	private static class ViewItemHolder {
		private ImageView mImage;
		private TextView mName;
		private TextView mContent;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getCount() == 0) {
			return null;
		}
		// System.out.println("position = "+position);
		ViewItemHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_friends_item, null);

			holder = new ViewItemHolder();
			holder.mImage = (ImageView) convertView.findViewById(R.id.ivIcon);
			holder.mName = (TextView) convertView.findViewById(R.id.tvName);
			holder.mContent = (TextView) convertView.findViewById(R.id.tvContent);
			convertView.setTag(holder);
		} else {
			holder = (ViewItemHolder) convertView.getTag();
		}

		ListRightInfo ai = mList.get(position);
		holder.mImage.setImageBitmap(ai.getListRightIcon());
		holder.mName.setText(ai.getListRightName());
		holder.mContent.setText(ai.getListRightContent());
		return convertView;
	}

}
