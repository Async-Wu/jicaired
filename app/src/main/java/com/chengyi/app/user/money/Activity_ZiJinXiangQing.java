package com.chengyi.app.user.money;


import android.os.Bundle;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Activity_ZiJinXiangQing extends BaseActivity {
    
	private TextView timeTV,typeTv,detailTV;
//	ImageView  btnclose;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.new_activity_zijinxiangqing);

		setCusTomeTitle("资金明细详情");

  		setBack();

  		timeTV=(TextView)findViewById(R.id.time);
  		typeTv=(TextView)findViewById(R.id.type);
  		detailTV=(TextView)findViewById(R.id.detail);
  		timeTV.setText(this.getIntent().getStringExtra("time"));
  		typeTv.setText(this.getIntent().getStringExtra("type"));
  		detailTV.setText(this.getIntent().getStringExtra("detail"));
	}

}
