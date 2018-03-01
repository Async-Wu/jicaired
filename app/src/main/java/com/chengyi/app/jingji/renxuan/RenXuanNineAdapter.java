package com.chengyi.app.jingji.renxuan;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.model.model.RenXuanNineData;
import com.chengyi.app.util.CaipiaoConst;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  RenXuanNineAdapter extends BaseAdapter {

	List<RenXuanNineData> list = new ArrayList<RenXuanNineData>();
	private String btnVaule;
	private int index;
	private Handler handler;
	private ArrayList<String> selectedList;
	boolean deleteMode = false;

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	private boolean isTouZhuEnsue = false;

	/**
	 * 是否投注确认
	 * */
	public boolean isTouZhuEnsue() {
		return isTouZhuEnsue;
	}

	public void setTouZhuEnsue(boolean isTouZhuEnsue) {
		this.isTouZhuEnsue = isTouZhuEnsue;
	}

	private int cpId;

	public int getCpId() {
		return cpId;
	}

	public void setCpId(int cpId) {
		this.cpId = cpId;
	}

	public ArrayList<String> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(ArrayList<String> selectedList) {
		this.selectedList = selectedList;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public List<RenXuanNineData> getList() {
		return list;
	}

	public void setList(List<RenXuanNineData> list) {
		this.list = list;
	}

	protected LayoutInflater mInflater;
	Context mContext;

	public RenXuanNineAdapter(Activity activity) {
		mInflater = LayoutInflater.from(activity);
		mContext = activity;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			if (!isTouZhuEnsue)
				convertView = mInflater.inflate(
						R.layout.new_item_shengfu14chang, parent, false);
			else
				convertView = mInflater.inflate(
						R.layout.new_item_sf14cquerenview, parent,false);
			holder = new ViewHolder();
			holder.loadViews(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final RenXuanNineData data = list.get(position);
		for (LinearLayout ln : holder.lnList) {
			if (isTouZhuEnsue) {
				ln.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (true)return;
						btnVaule = String.valueOf(v.getTag());
						v.setSelected(!v.isSelected());
						if (v.isSelected()) {
							data.getBuffer().append(btnVaule);
							data.setSelectedNum(data.getSelectedNum() + 1);
						}else{
							index = data.getBuffer().indexOf(btnVaule);
							if (index != -1) {
								data.getBuffer().deleteCharAt(index);
								data.setSelectedNum(data.getSelectedNum() - 1);
							}
						}
						handler.sendEmptyMessage(2);
					}
				}
				);
			} else {
				ln.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						btnVaule = String.valueOf(v.getTag());
						v.setSelected(!v.isSelected());
						if (v.isSelected()) {
							if (cpId == CaipiaoConst.ID_RENXUAN9CHANG
									&& selectedList.size() == 9
									&& !selectedList.contains((data.getNum() - 1)
											+ "")) {
								v.setSelected(false);
								handler.sendEmptyMessage(0);
								return;
							}
							data.getBuffer().append(btnVaule);
							data.setSelectedNum(data.getSelectedNum() + 1);
							if (1 == data.getSelectedNum()) {
								selectedList.add((data.getNum() - 1) + "");
							}
						} else {
							index = data.getBuffer().indexOf(btnVaule);
							if (index != -1) {
								data.getBuffer().deleteCharAt(index);
								data.setSelectedNum(data.getSelectedNum() - 1);
							}
							if (0 == data.getSelectedNum()) {
								if (selectedList.contains((data.getNum() - 1)
										+ ""))
									selectedList.remove((data.getNum() - 1)
											+ "");
							}
						}
						handler.sendEmptyMessage(1);
					}
				});
			}
			if (data.getBuffer().indexOf(String.valueOf(ln.getTag())) != -1) {
				ln.setSelected(true);
			} else
				ln.setSelected(false);
		}
		if (isTouZhuEnsue) {
			holder.danbtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					list.remove(position);
					RenXuanNineAdapter.this.notifyDataSetChanged();
					handler.sendEmptyMessage(0);
				}
			});
		}
		holder.fillView(data);
		return convertView;
	}

	class ViewHolder {
		TextView num, lianSai, dateText, timeText, hostText, guestText, sPeilv,
				pPeilv, fPeilv;
		ArrayList<LinearLayout> lnList = new ArrayList<LinearLayout>();
		LinearLayout sLayout, pLayout, fLayout;
		ImageView danbtn;

		public void loadViews(View convertView) {
			if (!isTouZhuEnsue) {
				num = (TextView) convertView.findViewById(R.id.num);
				lianSai = (TextView) convertView.findViewById(R.id.liansainame);
				dateText = (TextView) convertView.findViewById(R.id.date);
				timeText = (TextView) convertView.findViewById(R.id.time);
			} else
				danbtn = (ImageView) convertView.findViewById(R.id.danbtn);
			hostText = (TextView) convertView.findViewById(R.id.duiwu1);
			guestText = (TextView) convertView.findViewById(R.id.duiwu2);
			sPeilv = (TextView) convertView.findViewById(R.id.speilv);
			pPeilv = (TextView) convertView.findViewById(R.id.ppeilv);
			fPeilv = (TextView) convertView.findViewById(R.id.fpeilv);
			sLayout = (LinearLayout) convertView.findViewById(R.id.shengLy);
			sLayout.setTag("3");
			pLayout = (LinearLayout) convertView.findViewById(R.id.pingLy);
			pLayout.setTag("1");
			fLayout = (LinearLayout) convertView.findViewById(R.id.fuLy);
			fLayout.setTag("0");
			lnList.add(sLayout);
			lnList.add(pLayout);
			lnList.add(fLayout);
		}

		public void fillView(RenXuanNineData data) {
			if (!isTouZhuEnsue) {
				num.setText(data.getNum() + "");
//				if (!TextUtils.isEmpty(data.getColor())) {
//					num.setTextColor(Color.parseColor(data.getColor()));
//				}
				lianSai.setText(data.getLianSai());
				dateText.setText(data.getDate());
				timeText.setText(data.getTime() + "开赛");
			} else {
				if (deleteMode)
					danbtn.setVisibility(View.VISIBLE);
				else
					danbtn.setVisibility(View.INVISIBLE);
			}
			hostText.setText(data.getHostName());
			guestText.setText(data.getGuestname());
			sPeilv.setText("主胜" + data.getsPeilv());
			pPeilv.setText("平" + data.getpPeilv());
			fPeilv.setText("主负" + data.getfPeilv());
		}
	}

}
