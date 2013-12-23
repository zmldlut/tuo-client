package com.myapp.adapter;

import java.util.ArrayList;
import java.util.List;

import com.myapp.R;
import com.myapp.model.AppInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapterMicroBlog extends BaseAdapter {

	private LayoutInflater mInflater;
	public List<AppInfo> mList;
	
	public ListAdapterMicroBlog() {
		// TODO Auto-generated constructor stub
	}
	
	public ListAdapterMicroBlog(Context pContext, List<AppInfo> pList) {
		mInflater = LayoutInflater.from(pContext);
		if (pList != null) {
			mList = pList;
		} else {
			mList = new ArrayList<AppInfo>();
		}
	}
	
	private static class ViewHolder {
		private ImageView mImage;
		private TextView mName;
		private TextView mVer;
		private TextView mSize;
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
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getCount() == 0) {
			return null;
		}
		// System.out.println("position = "+position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);

			holder = new ViewHolder();
			holder.mImage = (ImageView) convertView
					.findViewById(R.id.ivIcon);
			holder.mName = (TextView) convertView.findViewById(R.id.tvName);
			holder.mVer = (TextView) convertView.findViewById(R.id.tvVer);
			holder.mSize = (TextView) convertView.findViewById(R.id.tvSize);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		AppInfo ai = mList.get(position);
		holder.mImage.setImageBitmap(ai.getAppIcon());
		holder.mName.setText(ai.getAppName());
		holder.mVer.setText(ai.getAppVer());
		holder.mSize.setText(ai.getAppSize());

		return convertView;
	}
}
