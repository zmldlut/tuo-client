package com.myapp.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myapp.R;


/**
 * ListView下拉刷新和加载更�?p>
 * 
 * <strong>变更说明:</strong>
 * <p>默认如果设置了OnRefreshListener接口和OnLoadMoreListener接口�?br>并且不为null，则打开这两个功能了�?
 * <p>剩余三个Flag�?
 * <br>mIsAutoLoadMore(是否自动加载更多)
 * <br>mIsMoveToFirstItemAfterRefresh(下拉刷新后是否显示第�?��Item)
 * <br>mIsDoRefreshOnWindowFocused(当该ListView�?��的控件显示到屏幕上时，是否直接显示正在刷�?..)
 * 
 * <p><strong>有改进意见，请发送到俺的邮箱哈~ 多谢各位小伙伴了！^_^</strong>
 * 
 * @date 2013-11-11 下午10:09:26
 * @change JohnWatson 
 * @mail xxzhaofeng5412@gmail.com
 * @version 1.0
 */
public class SingleLayoutListView extends ListView implements OnScrollListener {

	/**  显示格式化日期模�?  */
	private final static String DATE_FORMAT_STR = "yyyy年MM月dd�?HH:mm";
	
	/**  实际的padding的距离与界面上偏移距离的比例   */
	private final static int RATIO = 3;
	//===========================以下4个常量为 下拉刷新的状态标�?==============================
	/**  松开刷新   */
	private final static int RELEASE_TO_REFRESH = 0;
	/**  下拉刷新   */
	private final static int PULL_TO_REFRESH = 1;
	/**  正在刷新   */
	private final static int REFRESHING = 2;
	/**  刷新完成   or �?��都没做，恢复原状态�?  */
	private final static int DONE = 3;
	//===========================以下3个常量为 加载更多的状态标�?==============================
	/**  加载�?  */
	private final static int ENDINT_LOADING = 1;
	/**  手动完成刷新   */
	private final static int ENDINT_MANUAL_LOAD_DONE = 2;
	/**  自动完成刷新   */
	private final static int ENDINT_AUTO_LOAD_DONE = 3;
	
	/**
	 * <strong>下拉刷新HeadView的实时状态flag</strong>
	 *     
	 * <p> 0 : RELEASE_TO_REFRESH;
	 * <p> 1 : PULL_To_REFRESH;
	 * <p> 2 : REFRESHING;
	 * <p> 3 : DONE;
	 * 
	 */
	private int mHeadState;
	/**  
	 * <strong>加载更多FootView（EndView）的实时状�?flag</strong>
	 * 
	 * <p> 0 : 完成/等待刷新 ;
	 * <p> 1 : 加载�? 
	 */
	private int mEndState;
	
	// ================================= 功能设置Flag ================================
	
	/**  可以加载更多�?  */
	private boolean mCanLoadMore = false;
	/**  可以下拉刷新�?  */
	private boolean mCanRefresh = false;
	/**  可以自动加载更多吗？（注意，先判断是否有加载更多，如果没有，这个flag也没有意义）   */
	private boolean mIsAutoLoadMore = false;
	/**  下拉刷新后是否显示第�?��Item    */
	private boolean mIsMoveToFirstItemAfterRefresh = false;
	/**  当该ListView�?��的控件显示到屏幕上时，是否直接显示正在刷�?..   */
	private boolean mIsDoRefreshOnUIChanged = false;

	public boolean isCanLoadMore() {
		return mCanLoadMore;
	}
	
	public void setCanLoadMore(boolean pCanLoadMore) {
		mCanLoadMore = pCanLoadMore;
		if(mCanLoadMore && getFooterViewsCount() == 0){
			addFooterView();
		}
	}
	
	public boolean isCanRefresh() {
		return mCanRefresh;
	}
	
	public void setCanRefresh(boolean pCanRefresh) {
		mCanRefresh = pCanRefresh;
	}
	
	public boolean isAutoLoadMore() {
		return mIsAutoLoadMore;
	}

	public void setAutoLoadMore(boolean pIsAutoLoadMore) {
		mIsAutoLoadMore = pIsAutoLoadMore;
	}
		
	public boolean isMoveToFirstItemAfterRefresh() {
		return mIsMoveToFirstItemAfterRefresh;
	}

	public void setMoveToFirstItemAfterRefresh(
			boolean pIsMoveToFirstItemAfterRefresh) {
		mIsMoveToFirstItemAfterRefresh = pIsMoveToFirstItemAfterRefresh;
	}
	
	public boolean isDoRefreshOnUIChanged() {
		return mIsDoRefreshOnUIChanged;
	}

	public void setDoRefreshOnUIChanged(boolean pIsDoRefreshOnWindowFocused) {
		mIsDoRefreshOnUIChanged = pIsDoRefreshOnWindowFocused;
	}
	// ============================================================================

	private LayoutInflater mInflater;

	private LinearLayout mHeadRootView;
	private TextView mTipsTextView;
	private TextView mLastUpdatedTextView;
	private ImageView mArrowImageView;
	private ProgressBar mProgressBar;
	
	private View mEndRootView;
	private ProgressBar mEndLoadProgressBar;
	private TextView mEndLoadTipsTextView;

	/**  headView动画   */
	private RotateAnimation mArrowAnim;
	/**  headView反转动画   */
	private RotateAnimation mArrowReverseAnim;
 
	/** 用于保证startY的�?在一个完整的touch事件中只被记录一�?   */
	private boolean mIsRecored;

	private int mHeadViewWidth;
	private int mHeadViewHeight;

	private int mStartY;
	private boolean mIsBack;
	
	private int mFirstItemIndex;
	private int mLastItemIndex;
	private int mCount;
	@SuppressWarnings("unused")
	private boolean mEnoughCount;//足够数量充满屏幕�?
	
	private OnRefreshListener mRefreshListener;
	private OnLoadMoreListener mLoadMoreListener;

	private String mLabel;
	
	public String getLabel() {
		return mLabel;
	}

	public void setLabel(String pLabel) {
		mLabel = pLabel;
	}

	public SingleLayoutListView(Context pContext) {
		super(pContext);
		init(pContext);
	}
	
	public SingleLayoutListView(Context pContext, AttributeSet pAttrs) {
		super(pContext, pAttrs);
		init(pContext);
	}

	public SingleLayoutListView(Context pContext, AttributeSet pAttrs, int pDefStyle) {
		super(pContext, pAttrs, pDefStyle);
		init(pContext);
	}

	/**
	 * 初始化操�?
	 * @param pContext 
	 * @date 2013-11-20 下午4:10:46
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void init(Context pContext) {
//		final ViewConfiguration _ViewConfiguration = ViewConfiguration.get(pContext);
//		mTouchSlop = _ViewConfiguration.getScaledTouchSlop();
		
		setCacheColorHint(pContext.getResources().getColor(R.color.transparent));
		setOnLongClickListener(null);
		mInflater = LayoutInflater.from(pContext);

		addHeadView();
		
		setOnScrollListener(this);

		initPullImageAnimation(0);
	}

	/**
	 * 添加下拉刷新的HeadView 
	 * @date 2013-11-11 下午9:48:26
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void addHeadView() {
		mHeadRootView = (LinearLayout) mInflater.inflate(R.layout.pull_to_refresh_head, null);

		mArrowImageView = (ImageView) mHeadRootView
				.findViewById(R.id.head_arrowImageView);
		mArrowImageView.setMinimumWidth(70);
		mArrowImageView.setMinimumHeight(50);
		mProgressBar = (ProgressBar) mHeadRootView
				.findViewById(R.id.head_progressBar);
		mTipsTextView = (TextView) mHeadRootView.findViewById(
				R.id.head_tipsTextView);
		mLastUpdatedTextView = (TextView) mHeadRootView
				.findViewById(R.id.head_lastUpdatedTextView);

		measureView(mHeadRootView);
		mHeadViewHeight = mHeadRootView.getMeasuredHeight();
		mHeadViewWidth = mHeadRootView.getMeasuredWidth();
		
		mHeadRootView.setPadding(0, -1 * mHeadViewHeight, 0, 0);
		mHeadRootView.invalidate();

		Log.v("size", "width:" + mHeadViewWidth + " height:"
				+ mHeadViewHeight);

		addHeaderView(mHeadRootView, null, false);
		
		mHeadState = DONE;
		changeHeadViewByState();
	}
	
	/**
	 * 添加加载更多FootView
	 * @date 2013-11-11 下午9:52:37
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void addFooterView() {
		mEndRootView = mInflater.inflate(R.layout.pull_to_refresh_load_more, null);
		mEndRootView.setVisibility(View.VISIBLE);
		mEndLoadProgressBar = (ProgressBar) mEndRootView
				.findViewById(R.id.pull_to_refresh_progress);
		mEndLoadTipsTextView = (TextView) mEndRootView.findViewById(R.id.load_more);
		mEndRootView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mCanLoadMore){
					if(mCanRefresh){
						// 当可以下拉刷新时，如果FootView没有正在加载，并且HeadView没有正在刷新，才可以点击加载更多�?
						if(mEndState != ENDINT_LOADING && mHeadState != REFRESHING){
							mEndState = ENDINT_LOADING;
							onLoadMore();
						}
					}else if(mEndState != ENDINT_LOADING){
						// 当不能下拉刷新时，FootView不正在加载时，才可以点击加载更多�?
						mEndState = ENDINT_LOADING;
						onLoadMore();
					}
				}
			}
		});
		
		addFooterView(mEndRootView);
		
		if(mIsAutoLoadMore){
			mEndState = ENDINT_AUTO_LOAD_DONE;
		}else{
			mEndState = ENDINT_MANUAL_LOAD_DONE;
		}
	}

	/**
	 * 实例化下拉刷新的箭头的动画效�?
	 * @param pAnimDuration 动画运行时长
	 * @date 2013-11-20 上午11:53:22
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void initPullImageAnimation(final int pAnimDuration) {
		
		int _Duration;
		
		if(pAnimDuration > 0){
			_Duration = pAnimDuration;
		}else{
			_Duration = 250;
		}
//		Interpolator _Interpolator;
//		switch (pAnimType) {
//		case 0:
//			_Interpolator = new AccelerateDecelerateInterpolator();
//			break;
//		case 1:
//			_Interpolator = new AccelerateInterpolator();
//			break;
//		case 2:
//			_Interpolator = new AnticipateInterpolator();
//			break;
//		case 3:
//			_Interpolator = new AnticipateOvershootInterpolator();
//			break;
//		case 4:
//			_Interpolator = new BounceInterpolator();
//			break;
//		case 5:
//			_Interpolator = new CycleInterpolator(1f);
//			break;
//		case 6:
//			_Interpolator = new DecelerateInterpolator();
//			break;
//		case 7:
//			_Interpolator = new OvershootInterpolator();
//			break;
//		default:
//			_Interpolator = new LinearInterpolator();
//			break;
//		}
		
		Interpolator _Interpolator = new LinearInterpolator();
		
		mArrowAnim = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mArrowAnim.setInterpolator(_Interpolator);
		mArrowAnim.setDuration(_Duration);
		mArrowAnim.setFillAfter(true);

		mArrowReverseAnim = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mArrowReverseAnim.setInterpolator(_Interpolator);
		mArrowReverseAnim.setDuration(_Duration);
		mArrowReverseAnim.setFillAfter(true);
	}

	/**
	 * 测量HeadView宽高(注意：此方法仅�?用于LinearLayout，请读�?自己测试验证�?
	 * @param pChild 
	 * @date 2013-11-20 下午4:12:07
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void measureView(View pChild) {
		ViewGroup.LayoutParams p = pChild.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;

		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		pChild.measure(childWidthSpec, childHeightSpec);
	}
	
	/**
	 *为了判断滑动到ListView底部�?
	 */
	@Override
	public void onScroll(AbsListView pView, int pFirstVisibleItem,
			int pVisibleItemCount, int pTotalItemCount) {
//		System.out.println("onScroll . pFirstVisibleItem = "+pFirstVisibleItem);
		mFirstItemIndex = pFirstVisibleItem;
		mLastItemIndex = pFirstVisibleItem + pVisibleItemCount - 2;
		mCount = pTotalItemCount - 2;
		if (pTotalItemCount > pVisibleItemCount ) {
			mEnoughCount = true;
//			endingView.setVisibility(View.VISIBLE);
		} else {
			mEnoughCount = false;
		}
	}

	/**
	 *这个方法，可能有点乱，大家多读几遍就明白了�?
	 */
	@Override
	public void onScrollStateChanged(AbsListView pView, int pScrollState) {
		if(mCanLoadMore){// 存在加载更多功能
			if (mLastItemIndex ==  mCount && pScrollState == SCROLL_STATE_IDLE) {
				//SCROLL_STATE_IDLE=0，滑动停�?
				if (mEndState != ENDINT_LOADING) {
					if(mIsAutoLoadMore){// 自动加载更多，我们让FootView显示 “更    多�?
						if(mCanRefresh){
							// 存在下拉刷新并且HeadView没有正在刷新时，FootView可以自动加载更多�?
							if(mHeadState != REFRESHING){
								// FootView显示 : �?   �? ---> 加载�?..
								mEndState = ENDINT_LOADING;
								onLoadMore();
								changeEndViewByState();
							}
						}else{// 没有下拉刷新，我们直接进行加载更多�?
							// FootView显示 : �?   �? ---> 加载�?..
							mEndState = ENDINT_LOADING;
							onLoadMore();
							changeEndViewByState();
						}
					}else{// 不是自动加载更多，我们让FootView显示 “点击加载�?
						// FootView显示 : 点击加载  ---> 加载�?..
						mEndState = ENDINT_MANUAL_LOAD_DONE;
						changeEndViewByState();
					}
				}
			}
		}else if(mEndRootView != null && mEndRootView.getVisibility() == VISIBLE){
			// 突然关闭加载更多功能之后，我们要移除FootView�?
			System.out.println("this.removeFooterView(endRootView);...");
			mEndRootView.setVisibility(View.GONE);
			this.removeFooterView(mEndRootView);
		}
	}

	/**
	 * 改变加载更多状�?
	 * @date 2013-11-11 下午10:05:27
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void  changeEndViewByState() {
		if (mCanLoadMore) {
			//允许加载更多
			switch (mEndState) {
			case ENDINT_LOADING://刷新�?
				
				// 加载�?..
				if(mEndLoadTipsTextView.getText().equals(
						R.string.p2refresh_doing_end_refresh)){
					break;
				}
				mEndLoadTipsTextView.setText(R.string.p2refresh_doing_end_refresh);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.VISIBLE);
				break;
			case ENDINT_MANUAL_LOAD_DONE:// 手动刷新完成
				
				// 点击加载
				mEndLoadTipsTextView.setText(R.string.p2refresh_end_click_load_more);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.GONE);
				
				mEndRootView.setVisibility(View.VISIBLE);
				break;
			case ENDINT_AUTO_LOAD_DONE:// 自动刷新完成
				
				// �?   �?
				mEndLoadTipsTextView.setText(R.string.p2refresh_end_load_more);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.GONE);
				
				mEndRootView.setVisibility(View.VISIBLE);
				break;
			default:
				// 原来的代码是为了�?当所有item的高度小于ListView本身的高度时�?
				// 要隐藏掉FootView，大家自己去原作者的代码参�?�?
				
//				if (enoughCount) {					
//					endRootView.setVisibility(View.VISIBLE);
//				} else {
//					endRootView.setVisibility(View.GONE);
//				}
				break;
			}
		}
	}
	/**
	 * *****五星注意事项�?此方法不适用于ViewPager中，因为viewpager默认实例化相邻的item的View
	 *  建议�?不嵌套的时�?，可以放在这个方法里使用，效果就是：进入界面直接刷新。具体刷新的控制条件，你自己决定�?
	 *  方法为：直接调用pull2RefreshManually();
	 */
	@Override
	public void onWindowFocusChanged(boolean pHasWindowFocus) {
		super.onWindowFocusChanged(pHasWindowFocus);
//		MyLogger.showLogWithLineNum(5, "mLabel = "+mLabel+"___onWindowFocusChanged... ___pHasWindowFocus = "+pHasWindowFocus);
		if(mIsDoRefreshOnUIChanged){
			if(pHasWindowFocus){
				pull2RefreshManually();
			}
		}
	}
	
	/**
	 * 当该ListView�?��的控件显示到屏幕上时，直接显示正在刷�?..
	 * @date 2013-11-23 下午11:26:10
	 * @author JohnWatson
	 * @version 1.0
	 */
	public void pull2RefreshManually(){
		mHeadState = REFRESHING;
		changeHeadViewByState();
		onRefresh();
		
		mIsRecored = false;
		mIsBack = false;
	}
	
	/**
	 *原作者的，我没改动，请读者自行优化�?
	 */
	public boolean onTouchEvent(MotionEvent event) {
		
		if (mCanRefresh) {
			if(mCanLoadMore && mEndState == ENDINT_LOADING){
				// 如果存在加载更多功能，并且当前正在加载中，默认不允许下拉刷新，必须加载完毕后下拉刷新才能使用�?
				return super.onTouchEvent(event);
			}
			
			switch (event.getAction()) {
			
			case MotionEvent.ACTION_DOWN:
				
				if (mFirstItemIndex == 0 && !mIsRecored) {
					mIsRecored = true;
					mStartY = (int) event.getY();
//					MyLogger.showLogWithLineNum(5, "mFirstItemIndex == 0 && !mIsRecored mStartY = "+mStartY);
				}else if(mFirstItemIndex == 0 && mIsRecored){
					// 说明上次的Touch事件只执行了Down动作，然后直接被父类拦截了�?
					// 那么就要重新给mStartY赋�?啦�?
//					MyLogger.showLogWithLineNum(5, "mFirstItemIndex = "+mFirstItemIndex+"__!mIsRecored = "+!mIsRecored);
					mStartY = (int) event.getY();
				}

				break;

			case MotionEvent.ACTION_UP:
				
				if (mHeadState != REFRESHING) {
					
					if (mHeadState == DONE) {
						
					}
					if (mHeadState == PULL_TO_REFRESH) {
						// 在松手的时�?，如果HeadView显示下拉刷新，那就恢复原状�?�?
						mHeadState = DONE;
						changeHeadViewByState();
					}
					if (mHeadState == RELEASE_TO_REFRESH) {
						// 在松手的时�?，如果HeadView显示松开刷新，那就显示正在刷新�?
						mHeadState = REFRESHING;
						changeHeadViewByState();
						onRefresh();
					}
				}

				mIsRecored = false;
				mIsBack = false;
				
				break;

			case MotionEvent.ACTION_MOVE:
				
				int _TempY = (int)event.getY();

				if (!mIsRecored && mFirstItemIndex == 0) {
					mIsRecored = true;
					mStartY = _TempY;
//					MyLogger.showLogWithLineNum(4, "!mIsRecored && mFirstItemIndex == 0 and __mStartY = "+mStartY);
				}

				if (mHeadState != REFRESHING && mIsRecored) {

					// 保证在设置padding的过程中，当前的位置�?��是在head�?
					// 否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚�?
					// 可以松手去刷新了
					if (mHeadState == RELEASE_TO_REFRESH) {

						setSelection(0);
						
						// �?��推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
						if (((_TempY - mStartY) / RATIO < mHeadViewHeight)
								&& (_TempY - mStartY) > 0) {
							mHeadState = PULL_TO_REFRESH;
							changeHeadViewByState();
						}
						// �?��子推到顶�?
						else if (_TempY - mStartY <= 0) {
							mHeadState = DONE;
							changeHeadViewByState();
						}
						// �?��拉了，或者还没有上推到屏幕顶部掩盖head的地�?
					}
					// 还没有到达显示松�?��新的时�?,DONE或�?是PULL_To_REFRESH状�?
					if (mHeadState == PULL_TO_REFRESH) {

						setSelection(0);

						// 下拉到可以进入RELEASE_TO_REFRESH的状�?
						if ((_TempY - mStartY) / RATIO >= mHeadViewHeight) {
							mHeadState = RELEASE_TO_REFRESH;
							mIsBack = true;
							changeHeadViewByState();
						} else if (_TempY - mStartY <= 0) {
//							System.out.println("mHeadState == PULL_TO_REFRESH _TempY = "+_TempY+"__mStartY = "+mStartY);
							mHeadState = DONE;
							changeHeadViewByState();
						}
					}

					if (mHeadState == DONE) {
						if (_TempY - mStartY > 0) {
//							System.out.println("mHeadState == DONE ... _TempY - mStartY = "+(_TempY - mStartY));
							mHeadState = PULL_TO_REFRESH;
							changeHeadViewByState();
						}
					}

					if (mHeadState == PULL_TO_REFRESH) {
						mHeadRootView.setPadding(0, -1 * mHeadViewHeight
								+ (_TempY - mStartY) / RATIO, 0, 0);

					}

					if (mHeadState == RELEASE_TO_REFRESH) {
						mHeadRootView.setPadding(0, (_TempY - mStartY) / RATIO
								- mHeadViewHeight, 0, 0);
					}
				}
				break;
			}
		}

		return super.onTouchEvent(event);
	}
	
	/**
	 * 当HeadView状�?改变时�?，调用该方法，以更新界面
	 * @date 2013-11-20 下午4:29:44
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void changeHeadViewByState() {
		switch (mHeadState) {
		case RELEASE_TO_REFRESH:
//			MyLogger.showLogWithLineNum(3, "changeHeaderViewByState ===>  RELEASE_TO_REFRESH");
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
			mTipsTextView.setVisibility(View.VISIBLE);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);

			mArrowImageView.clearAnimation();
			mArrowImageView.startAnimation(mArrowAnim);
			// 松开刷新
			mTipsTextView.setText(R.string.p2refresh_release_refresh);

			break;
		case PULL_TO_REFRESH:
//			MyLogger.showLogWithLineNum(3, "changeHeaderViewByState ===>  PULL_TO_REFRESH");
			mProgressBar.setVisibility(View.GONE);
			mTipsTextView.setVisibility(View.VISIBLE);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.VISIBLE);
			// 是由RELEASE_To_REFRESH状�?转变来的
			if (mIsBack) {
				mIsBack = false;
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mArrowReverseAnim);
				// 下拉刷新
				mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
			} else {
				// 下拉刷新
				mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
			}
			break;

		case REFRESHING:
//			MyLogger.showLogWithLineNum(3, "changeHeaderViewByState ===>  REFRESHING");
			
			changeHeaderViewRefreshState();
			break;
		case DONE:
//			MyLogger.showLogWithLineNum(3, "changeHeaderViewByState ===>  DONE");
			
			mHeadRootView.setPadding(0, -1 * mHeadViewHeight, 0, 0);
			
			mProgressBar.setVisibility(View.GONE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setImageResource(R.drawable.arrow);
			// 下拉刷新
			mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);
			
			break;
		}
	}

	/**
	 * 改变HeadView在刷新状态下的显�?
	 * @date 2013-11-23 下午10:49:00
	 * @author JohnWatson
	 * @version 1.0
	 */
	private void changeHeaderViewRefreshState(){
		mHeadRootView.setPadding(0, 0, 0, 0);
		
		// 华生的建议： 实际上这个的setPadding可以用动画来代替。我没有试，但是我见过�?其实有的人也用Scroller可以实现这个效果�?
		// 我没时间研究了，后期再扩展，这个工作交给小伙伴你们啦~ 如果改进了记得发到我邮箱噢~
		// 本人邮箱�?xxzhaofeng5412@gmail.com
		
		mProgressBar.setVisibility(View.VISIBLE);
		mArrowImageView.clearAnimation();
		mArrowImageView.setVisibility(View.GONE);
		// 正在刷新...
		mTipsTextView.setText(R.string.p2refresh_doing_head_refresh);
		mLastUpdatedTextView.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 下拉刷新监听接口
	 * @date 2013-11-20 下午4:50:51
	 * @change JohnWatson
	 * @version 1.0
	 */
	public interface OnRefreshListener {
		public void onRefresh();
	}
	
	/**
	 * 加载更多监听接口
	 * @date 2013-11-20 下午4:50:51
	 * @change JohnWatson
	 * @version 1.0
	 */
	public interface OnLoadMoreListener {
		public void onLoadMore();
	}
	
	public void setOnRefreshListener(OnRefreshListener pRefreshListener) {
		if(pRefreshListener != null){
			mRefreshListener = pRefreshListener;
			mCanRefresh = true;
		}
	}

	public void setOnLoadListener(OnLoadMoreListener pLoadMoreListener) {
		if(pLoadMoreListener != null){
			mLoadMoreListener = pLoadMoreListener;
			mCanLoadMore = true;
			if(mCanLoadMore && getFooterViewsCount() == 0){
				addFooterView();
			}
		}
	}
	
	/**
	 * 正在下拉刷新
	 * @date 2013-11-20 下午4:45:47
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void onRefresh() {
		if (mRefreshListener != null) {
			mRefreshListener.onRefresh();
		}
	}
	
	/**
	 * 下拉刷新完成
	 * @date 2013-11-20 下午4:44:12
	 * @change JohnWatson
	 * @version 1.0
	 */
	public void onRefreshComplete() {
				
		mHeadState = DONE;
		// �?��更新: Time
		mLastUpdatedTextView.setText(
				getResources().getString(R.string.p2refresh_refresh_lasttime) + 
				new SimpleDateFormat(DATE_FORMAT_STR, Locale.CHINA).format(new Date()));
		changeHeadViewByState();
		
		// 下拉刷新后是否显示第�?��Item
		if (mIsMoveToFirstItemAfterRefresh) {
			mFirstItemIndex = 0;
			setSelection(0);
		}
	}

	/**
	 * 正在加载更多，FootView显示 �?加载�?..
	 * @date 2013-11-20 下午4:35:51
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void onLoadMore() {
		if (mLoadMoreListener != null) {
			// 加载�?..
			mEndLoadTipsTextView.setText(R.string.p2refresh_doing_end_refresh);
			mEndLoadTipsTextView.setVisibility(View.VISIBLE);
			mEndLoadProgressBar.setVisibility(View.VISIBLE);
			
			mLoadMoreListener.onLoadMore();
		}
	}

	/**
	 * 加载更多完成 
	 * @date 2013-11-11 下午10:21:38
	 * @change JohnWatson
	 * @version 1.0
	 */
	public void onLoadMoreComplete() {
		if(mIsAutoLoadMore){
			mEndState = ENDINT_AUTO_LOAD_DONE;
		}else{
			mEndState = ENDINT_MANUAL_LOAD_DONE;
		}
		changeEndViewByState();
	}
	
	/**
	 * 主要更新�?��刷新时间啦！
	 * @param adapter
	 * @date 2013-11-20 下午5:35:51
	 * @change JohnWatson
	 * @version 1.0
	 */
	public void setAdapter(BaseAdapter adapter) {
		// �?��更新: Time
		mLastUpdatedTextView.setText(
				getResources().getString(R.string.p2refresh_refresh_lasttime) + 
				new SimpleDateFormat(DATE_FORMAT_STR, Locale.CHINA).format(new Date()));
		super.setAdapter(adapter);
	}
	
}
