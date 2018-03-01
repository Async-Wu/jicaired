package com.chengyi.app.util;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chengyi.R;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  ImgUtil {

    public static void SHOW(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load((url)).into(imageView);
    }

    public static void SHOW(Context activity, String url, ImageView imageView) {
        Glide.with(activity).load((url)).into(imageView);
    }
    public static void SHOW(Context ctx, String url, ImageView imageView,int pos) {
       switch (pos){
           case  0:
               Glide.with(ctx).load((url)).placeholder(R.drawable.banner).error(R.drawable.banner).into(imageView);
               break;
           case  1:
               Glide.with(ctx).load((url)).placeholder(R.drawable.banner00).error(R.drawable.banner00).into(imageView);
               break;
           case  2:
               Glide.with(ctx).load((url)).placeholder(R.drawable.banner000).error(R.drawable.banner000).into(imageView);
               break;
           default:
               Glide.with(ctx).load((url)).placeholder(R.drawable.banner).error(R.drawable.banner).into(imageView);
               break;


        }



    }


}
