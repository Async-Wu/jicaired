package com.chengyi.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.num.lottery.Activity_Touzhuqueren;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */

public class  TouzhuquerenAdapter extends BaseAdapter {
	List<TouzhuquerenData> list = new ArrayList<TouzhuquerenData>();
	protected LayoutInflater mInflater;
	Context mContext;
	public static final int TOUZHU_TYPE = 0;
	public static final int GOUMAIQUEREN_TYPE = 1;
	public static final int GOUCAIJILUXIANGQING_TYPE = 2;
	private int  length=0;   ///玩法名称最大的长度
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	private int type = TOUZHU_TYPE;
	
	public void setType(int type) {
		this.type = type;
	}

	public void setList(List<TouzhuquerenData> list) {
		this.list = list;
	}

	private boolean deleteMode = false;

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public TouzhuquerenAdapter(Activity activity) {
		mInflater = LayoutInflater.from(activity);
		mContext = activity;
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		}
		return list.size() + (moreNotShow ? 1 : 0);
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	// 是否提示更多内容未显示，如购买列表最多只显示10个，如果实际大于10个，则在第11行显示，更多未显示
	private boolean moreNotShow = false;

	public boolean isMoreNotShow() {
		return moreNotShow;
	}

	public void setMoreNotShow(boolean moreNotShow) {
		this.moreNotShow = moreNotShow;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (moreNotShow && position == list.size()) {
			TextView tv = (TextView) mInflater.inflate(R.layout.item_oneitem,
					null);
			tv.setText(String.format(
					mContext.getString(R.string.zuiduoxianshintiao),
					CaipiaoConst.GENGDUO_MAX_NUM + ""));
			return tv;
		}
		ViewHolder holder;
		final int p = position;
		if (convertView == null) {
			if (type == TOUZHU_TYPE) {
				convertView = mInflater.inflate(R.layout.new_item_touzhuqueren,
						parent,false);
			} else {
				convertView = mInflater.inflate(R.layout.new_item_goumaiqueren,
						parent,false);
			}
			holder = new ViewHolder();
			holder.loadViews(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				convertView = mInflater.inflate(R.layout.new_item_touzhuqueren,
						null);
				holder = new ViewHolder();
				holder.loadViews(convertView);
				convertView.setTag(holder);
			}
		}
	convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (mContext instanceof Activity_Touzhuqueren) {
						Activity_Touzhuqueren touzhuqueren = (Activity_Touzhuqueren) mContext;
						touzhuqueren.moveToEdit(p);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});






		if (isDeleteMode()) {
			holder.deleteLayout.setVisibility(View.VISIBLE);
			holder.img.setVisibility(View.INVISIBLE);
			//删除item
			holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						list.remove(p);
						notifyDataSetChanged();
						if (mContext instanceof Activity_Touzhuqueren) {
							((Activity_Touzhuqueren) mContext).setBottomValues();
							if(list.size()==0){
								deleteMode = false;
							    ((Activity_Touzhuqueren) mContext).LeftMenuReselected();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});


		} else {
			if (holder.deleteLayout != null) {
				holder.deleteLayout.setVisibility(View.INVISIBLE);
			}
			if(holder.img!=null)
			holder.img.setVisibility(View.VISIBLE);
		}
		TouzhuquerenData data = list.get(position);
		fillItem(data, holder);
		return convertView;
	}

	private int price = CaipiaoConst.PRICE;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	

	private void fillItem(TouzhuquerenData data, ViewHolder holder) {
		
		if (type != TOUZHU_TYPE) {// 购彩记录详情和合买确认页面都是这个类型
			holder.wfname.setWidth( (int)holder.wfname.getPaint().getTextSize()*length);
			if(type == GOUCAIJILUXIANGQING_TYPE){
				holder.wfname.setText(data.getType());
			    holder.haoma.setText(CaipiaoUtil.getTouzhuSpan(data.getNumber(), 100));
			}else{
				holder.wfname.setText(data.getName());
				holder.haoma.setText(CaipiaoUtil.getTouzhuSpan(data.getTouzhuhaoma(), 100));
			}
		}
		if (type == TOUZHU_TYPE) {
			holder.haoma.setText(CaipiaoUtil.getTouzhuSpan(data.getTouzhuhaoma(), 100));
			holder.money.setText(data.getZhushu()*price+"元");
			holder.count.setText(data.getZhushu()+"注");
			holder.wanfa.setText(data.getName()+":");
			holder.count.setVisibility(View.VISIBLE);
			holder.wanfa.setVisibility(View.VISIBLE);
		} else {
			if (holder.count != null) {
				holder.count.setVisibility(View.GONE);
			}
			if (holder.wanfa != null) {
				holder.wanfa.setVisibility(View.GONE);
			}
		}
	}

	class ViewHolder {
		TextView haoma;
		TextView wanfa;
		TextView count;
		TextView money;
		TextView wfname;
		ImageView deleteLayout,img;
		View editlayout;

		public void loadViews(View convertView) {
			haoma = (TextView) convertView.findViewById(R.id.haoma);
			wanfa = (TextView) convertView.findViewById(R.id.wanfa);
			money= (TextView) convertView.findViewById(R.id.money);
			deleteLayout = (ImageView) convertView.findViewById(R.id.delete);
			img = (ImageView) convertView.findViewById(R.id.img);
			count= (TextView) convertView
					.findViewById(R.id.count);
			editlayout = convertView.findViewById(R.id.editlayout);
			wfname=(TextView) convertView.findViewById(R.id.wfname);
		}
	}

	public String getSubmitString() {
		String result = "";
		for (TouzhuquerenData d : list) {
			if (!TextUtils.isEmpty(result)) {
				result = result + ";";
			}
			result = result + d.getSubmitString().trim();
		}

		return result;
	}

}
