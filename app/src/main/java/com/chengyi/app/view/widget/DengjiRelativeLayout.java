package com.chengyi.app.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.chengyi.R;

import java.lang.reflect.Field;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  DengjiRelativeLayout extends RelativeLayout {
	LayoutInflater mInflater;
	ImageView dengjiiconview,dengjinumimageView;
	public DengjiRelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInflater.inflate(R.layout.new_dengji_layout, this, true);
		dengjiiconview=(ImageView) findViewById(R.id.dengjiiconview);
		dengjinumimageView=(ImageView) findViewById(R.id.dengjinumimageView);
	}

	public DengjiRelativeLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public DengjiRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
    public void  fillView(int res,int num){
    	dengjiiconview.setImageResource(res);
    	if(num>1){
	    	Class<R.drawable> draw = R.drawable.class;
	    	String pic="num_"+num;
	    	int id;
	    	 try {
	    	   Field field = draw.getDeclaredField(pic);
	    	   id=field.getInt(pic);
	    	  } catch (SecurityException e) {
	    		  id=R.drawable.num_1;
	    	  } catch (NoSuchFieldException e) {
	    		  id=R.drawable.num_1;
	    	  } catch (IllegalArgumentException e) {
	    		  id=R.drawable.num_1;
	    	  } catch (IllegalAccessException e) {
	    		  id=R.drawable.num_1;
	    	  }
			dengjinumimageView.setImageResource(id);
		}
    }
}
