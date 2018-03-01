package com.chengyi.app.jingji.basket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;
import com.chengyi.R;
import com.chengyi.app.base.BaseFragment;
import com.chengyi.app.net.control.Control;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.L;
import com.chengyi.app.util.YOUMENG_EVENT;
import com.chengyi.app.view.scoller.MyExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BasketballSFC extends BaseFragment {

	String Tag = "BasketballSFC";

	Button ensureBtn ;

	private TextView bottomTipsTextView, bottomLeftTextView,
	/*bottomChoosedLeftTextView,*/ bottomChoosedNumTextView/*,
            bottomChoosedRightTextView*/;
	private boolean isEmpty = false;

	 BasketballSFCAdapter adapterSFC;
	MyExpandableListView listView;
	private FrameLayout indicatorGroup;

	private int indicatorGroupId = -1;
	private int indicatorGroupHeight;
	private LayoutInflater mInflater;

	BasketBall parentActivity;

	private ProgressDialog progressDialog;

	int currentWanfaguan;
	HashMap<String, String> map = new HashMap<String, String>();

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentActivity = (BasketBall) getActivity();

	}
	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_lottery_basketball_mainpart,
						container, false);

		setupView(view);
		setupListener();
		currentWanfaguan = Control.getInstance().getBasketballManager()
				.getCurrentWanfaGuan();
		addHttpCallback(currentWanfaguan);

		int isRequestHttp = Control.getInstance().getBasketballManager()
				.requestLotteryData(currentWanfaguan);
		if (isRequestHttp == 1) {
			if (progressDialog == null) {
				progressDialog = ProgressDialog.show(parentActivity, "",
						"正在加载", true, true);
			} else {
				progressDialog.show();
			}
		}

		return view;
	}

	public void onResume() {
		super.onResume();
		L.d("chongxinshipei", "jinxing");
		if (listView != null && adapterSFC != null) {
			int size = adapterSFC.getGroupCount();
			adapterSFC.notifyDataSetChanged();
			for (int i = 0; i < size; i++) {
				listView.collapseGroup(i);
				listView.expandGroup(i);
			}
			caculateBottomSelectedSize();
		}

	}


	public void onPause() {
		super.onPause();
		L.e(Tag, "onPause");

		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	private void setupView(View view) {

		bottomLeftTextView = (TextView) view.findViewById(R.id.qingkong);
		bottomTipsTextView = (TextView) view
				.findViewById(R.id.yixuantishi);

		bottomChoosedNumTextView = (TextView) view
				.findViewById(R.id.numbisai);


		ensureBtn = (Button) view.findViewById(R.id.ensurebtn);

		// bottom bar end

		indicatorGroup = (FrameLayout) view.findViewById(R.id.topGroup);
		indicatorGroup.setVisibility(View.INVISIBLE);
		mInflater = (LayoutInflater) parentActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInflater.inflate(
				R.layout.fragment_lottery_football_listview_groupview,
				indicatorGroup, true);

		// listview start
		listView = (MyExpandableListView) view
				.findViewById(R.id.expandableListView);
		listView.setDivider(null);
		listView.setGroupIndicator(null);
		listView.setChildDivider(null);
		listView.setChildIndicator(null);
		listView.setCacheColorHint(0x000000);
		listView.setSelector(R.drawable.translucent_background);

		
		 adapterSFC = new  BasketballSFCAdapter(parentActivity,BasketballSFC.this);

		listView.setAdapter(adapterSFC);

	}

	private void setupListener() {
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				listView.setFirstItemIndex(firstVisibleItem);
				final ExpandableListView listview = (ExpandableListView) view;
				int npos = view.pointToPosition(0, 0);// 其实就是firstVisibleItem
				if (npos == AdapterView.INVALID_POSITION)// 如果第一个位置值无效
					return;

				long pos = listview.getExpandableListPosition(npos);
				int childPos = ExpandableListView.getPackedPositionChild(pos);// 获取第一行child的id
				int groupPos = ExpandableListView.getPackedPositionGroup(pos);// 获取第一行group的id

				if (childPos == AdapterView.INVALID_POSITION
						&& firstVisibleItem == 0) {// 第一行不是显示child,就是group,此时没必要显示指示器
					indicatorGroup.setVisibility(View.GONE);// 隐藏指示器
				} else {
					indicatorGroup.setVisibility(View.VISIBLE);// 滚动到第一行是child，就显示指示器
					indicatorGroupHeight = indicatorGroup.getMeasuredHeight();// 获取group的高度
				}

				// 如果指示器显示的不是当前group
				if (firstVisibleItem != 0) {// 如果指示器显示的不是当前group
					// +++++++++++++++++++++++++
					adapterSFC.getGroupView(groupPos,
							listview.isGroupExpanded(groupPos),
							indicatorGroup.getChildAt(0), null);// 将指示器更新为当前group
					indicatorGroupId = groupPos;
					indicatorGroup.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {

							listview.collapseGroup(indicatorGroupId);
						}
					});
				}
				if (indicatorGroupId == -1) // 如果此时grop的id无效，则返回
					return;
				int showHeight = indicatorGroupHeight;
				int nEndPos = listview.pointToPosition(0, indicatorGroupHeight);// 第二个item的位置
				if (nEndPos == AdapterView.INVALID_POSITION)// 如果无效直接返回
					return;
				long pos2 = listview.getExpandableListPosition(nEndPos);
				int groupPos2 = ExpandableListView.getPackedPositionGroup(pos2);// 获取第二个group的id

				if (groupPos2 != indicatorGroupId) {// 如果不等于指示器当前的group
					View viewNext = listview.getChildAt(nEndPos
							- listview.getFirstVisiblePosition());
					showHeight = viewNext.getTop();
				}
				// update group position
				MarginLayoutParams layoutParams = (MarginLayoutParams) indicatorGroup
						.getLayoutParams();
				layoutParams.topMargin = -(indicatorGroupHeight - showHeight);
				indicatorGroup.setLayoutParams(layoutParams);
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {


			}
		});

		listView.setonRefreshListener(new MyExpandableListView.OnRefreshListener() {
			public void onRefresh() {

				// isReflesh = true;
				// clearData();
				// handler.postDelayed(getData, 1000);
				map.clear();
				map.put("操作类型", "下拉刷新数据");
				CaipiaoUtil.youmengEvent(parentActivity,
						YOUMENG_EVENT.new_basketball, map);
				Control.getInstance().getBasketballManager()
						.requestLotteryData(currentWanfaguan);

			}
		});


		ensureBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				SparseArray<BasketballOneGame> sparse = Control.getInstance()
						.getBasketballManager().selectedLotteryGameArray
						.get(currentWanfaguan);
				if (sparse == null) {
					return;
				}
				if(Control.getInstance().getBasketballManager().getDanorguo()==0){
					if (currentWanfaguan - 200 >= 0 && sparse.size() < 2) {
						Toast.makeText(parentActivity, "请至少选择2场比赛",
								Toast.LENGTH_SHORT).show();
						return;
					}
				}
				startActivityForResult(new Intent(parentActivity,
						BasketballCartSFC.class),10);

				map.put("操作类型", "投注确认");
				CaipiaoUtil.youmengEvent(parentActivity,
						YOUMENG_EVENT.new_basketball, map);
			}

		});



		bottomLeftTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Control.getInstance().getBasketballManager()
						.clearSelected(currentWanfaguan);
				adapterSFC.notifyDataSetChanged();
				caculateBottomSelectedSize();

			}

		});

	}

	/**
	 * 底部已选场次提示
	 */
	private int caculateBottomSelectedSize() {
		SparseArray<BasketballOneGame> array = Control.getInstance()
				.getBasketballManager().selectedLotteryGameArray
				.get(currentWanfaguan);
		if (array == null) {
			bottomTipsTextView.setText("请至少选择");

			if (Control.getInstance().getBasketballManager().getDanorguo() == 0)
				bottomChoosedNumTextView.setText("2");
			else
				bottomChoosedNumTextView.setText("1");

			isEmpty = true;
			return 0;
		}
		int size = array.size();
		if (size > 0) {

			bottomTipsTextView.setText("已选择");
			bottomChoosedNumTextView.setText("" + size);

			isEmpty = false;
		} else {
			bottomTipsTextView.setText("请至少选择");

			if (Control.getInstance().getBasketballManager().getDanorguo() == 0)
				bottomChoosedNumTextView.setText("2");
			else
				bottomChoosedNumTextView.setText("1");

			isEmpty = true;

		}
		return size;
	}

	public void addHttpCallback(final int currentWanfaGuan) {

		Control.getInstance().getBasketballManager().dataCallbackArray.put(
				currentWanfaGuan, new BasketballManager.OnBasketballDataCallback() {

					@Override
					public void onSuccess(ArrayList<BasketballOneDay> list) {
						listView.onRefreshComplete();
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						if (list == null) {
							parentActivity.showNoGameTip();
							return;
						}

						caculateBottomSelectedSize();
						adapterSFC.setData(list, currentWanfaGuan);
						adapterSFC.notifyDataSetChanged();

						listView.setAdapter(adapterSFC);
						int size = adapterSFC.getGroupCount();
						for (int i = 0; i < size; i++) {
							listView.collapseGroup(i);
							listView.expandGroup(i);
						}

					}

					@Override
					public void onFailure(String info) {

						listView.onRefreshComplete();
						Toast.makeText(parentActivity, "网络有些异常，请稍后再试",
								Toast.LENGTH_SHORT).show();
					}

				});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==10){
			onResume();
		}
	}
}
