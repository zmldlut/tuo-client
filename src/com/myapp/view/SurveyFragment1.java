package com.myapp.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.myapp.R;
import com.myapp.adapter.ListAdapterMicroBlog;
import com.myapp.adapter.ViewPagerAdapterSurveyImage;
import com.myapp.base.BaseFragment;
import com.myapp.base.BaseMessage;
import com.myapp.base.C;
import com.myapp.model.AppInfo;
import com.myapp.model.Classify;
import com.myapp.model.Eio;
import com.myapp.view.SingleLayoutListView.OnLoadMoreListener;
import com.myapp.view.SingleLayoutListView.OnRefreshListener;

@SuppressLint({ "NewApi", "ValidFragment" })
public class SurveyFragment1 extends BaseFragment implements OnPageChangeListener{

	private Context context;
	private View view; 
	private Classify classify = new Classify();
	private List<Eio> eioList = new ArrayList<Eio>();
	
	private static final String TAG = "SingleFragment";
	
	private static final int LOAD_DATA_FINISH = 10;
	private static final int REFRESH_DATA_FINISH = 11;

	private List<AppInfo> mList = new ArrayList<AppInfo>();
	private ListAdapterMicroBlog mAdapter;
	private SingleLayoutListView mListView;

	private int currentPage = 1;
	
	//四张图片的数据
	//////////////////////////////////////////////////////////
	private ViewPager vp;
    private ViewPagerAdapterSurveyImage sViewAdapter;
    private List<View> views;
    
    private ImageView[] dots;
    
    private int currentIndex;
    private int currentState;
    
 
	static SurveyFragment1 newInstance(Classify classify, Context context) {
		SurveyFragment1 newFragment = new SurveyFragment1(context, classify);
        return newFragment;
    }
	
	public SurveyFragment1(Context context, Classify classify) {
		// TODO Auto-generated constructor stub
		super(context);
		Log.i(TAG, TAG+"-----SurveyFragment1");
		
		this.context = context;
		this.classify.setIcon(classify.getIcon());
		this.classify.setId(classify.getId());
		this.classify.setName(classify.getName());
		this.classify.setUptime(classify.getUptime());
	}
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, TAG+"-----onActivityCreated");

		doTaskGetEioList(currentPage++);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, TAG+"-----onCreateView");
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.survey_fragment1, container, false);
		return view;
		
	}	
	
	@Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "SurveyFragment1-----onDestroy");
    }
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onTaskComplete(int taskId, BaseMessage message){
		switch (taskId) {
		case C.task.eioList:
			try {
				eioList.clear();
				eioList = (ArrayList<Eio>) message.getResultList("Eio");
				if(eioList.size()==0||eioList==null){
					currentPage--;
				}
				buildAppData(eioList);
				initView();
				initViewPagers();
				initDots();
				doTaskFinish();
			} catch (Exception e) {
				e.printStackTrace();
				toast(e.getMessage());
			}
			break;
		}
	}
	
	private void initView() {
		mAdapter = new ListAdapterMicroBlog(context, mList);
		mListView = (SingleLayoutListView) view.findViewById(R.id.mListView1);
		/////////////////////////////////////////////////////////////////////////////
		mListView.setAdapter(mAdapter);

		mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO 下拉刷新
				Log.e(TAG, "onRefresh");
				currentPage = 1;

				eioList.clear();
				mList.clear();
				doTaskGetEioList(currentPage++);
				currentState = REFRESH_DATA_FINISH;
			}
		});

		mListView.setOnLoadListener(new OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				// TODO 加载更多
				Log.e(TAG, "onLoad");
				doTaskGetEioList(currentPage++);
				currentState = LOAD_DATA_FINISH;
			}
		});
		

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 此处传回来的position和mAdapter.getItemId()获取的一致;
				Log.e(TAG, "click position:" + position);
				// Log.e(TAG,
				// "__ mAdapter.getItemId() = "+mAdapter.getItemId(position));
			}
		});		
		mListView.setCanLoadMore(true);
		mListView.setCanRefresh(true);
		mListView.setAutoLoadMore(true);
		mListView.setMoveToFirstItemAfterRefresh(true);
		mListView.setDoRefreshOnUIChanged(true);
	}
	 private void initViewPagers() {
	        LayoutInflater inflater = LayoutInflater.from(context);

	        views = new ArrayList<View>();
	        // 初始化引导图片列表
	        views.add(inflater.inflate(R.layout.one, null));
	        views.add(inflater.inflate(R.layout.one, null));
	        views.add(inflater.inflate(R.layout.one, null));
	        views.add(inflater.inflate(R.layout.one, null));

	        // 初始化Adapter
	        sViewAdapter = new ViewPagerAdapterSurveyImage(views);

	        vp = (ViewPager) view.findViewById(R.id.viewpager);
	        vp.setAdapter(sViewAdapter);
	        // 绑定回调
	        vp.setOnPageChangeListener(this);
	    }
	
	private void initDots() {
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);

        dots = new ImageView[views.size()];

        // 循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);
        currentIndex = position;
    }
	
	public void doTaskGetEioList(int pageId) {

		HashMap<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("classifyId", this.classify.getId());
		urlParams.put("pageId", ""+pageId);
		
		try {
			this.doTaskAsync(C.task.eioList, C.api.eioList, urlParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doTaskFinish(){
		switch(currentState){
		case REFRESH_DATA_FINISH:
			if (mAdapter != null) {
				mAdapter.mList = mList;
				mAdapter.notifyDataSetChanged();
			}
			mListView.onRefreshComplete(); // 下拉刷新完成
			break;
		case LOAD_DATA_FINISH:
			if (mAdapter != null) {
				mAdapter.mList = mList;
				mAdapter.notifyDataSetChanged();
			}
			mListView.onLoadMoreComplete(); // 加载更多完成
			break;
		}
	}
	
	private void buildAppData(List<Eio> eioList) {
		for (int i = 0; i < eioList.size(); i++) {
			AppInfo ai = new AppInfo();
			ai.setAppIcon(BitmapFactory.decodeResource(getResources(),
					R.drawable.eio_icon));
			ai.setAppName(eioList.get(i).getTitle());
			ai.setAppVer(eioList.get(i).getAuthor());
			ai.setAppSize(eioList.get(i).getPublishtime());
			mList.add(ai);
		}
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setCurrentDot(arg0);
	}
}
