package com.chengyi.app.home.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.chengyi.app.util.ImgUtil;

import java.util.List;

/**
 * Created by lishangfan on 2016/10/11.
 */
public class BannerAdapter extends PagerAdapter {
    private List<String> urls;
    private  int[] ivs;
    private Context context;

    public BannerAdapter(List<String> urls, int[] ivs, Context context) {
        this.urls = urls;
        this.ivs = ivs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return urls!=null&&!urls.isEmpty()&&urls.size()>0?urls.size():ivs.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        ImageView imageView = new ImageView(context);


        if (urls!=null&&!urls.isEmpty()&&urls.size()>0){
            ImgUtil.SHOW(context,urls.get(position),imageView,position);
        }else {
            imageView.setBackgroundResource(ivs[position]);
        }


        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }
}
