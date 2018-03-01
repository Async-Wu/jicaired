package com.chengyi.app.web;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.util.L;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class WebViewActivity extends BaseActivity {
    private WebView wv;
    private String url;
    private LinearLayout ll_finsh_web, ll_back;

    private String title;
    private int flag;
    private String data;
    private boolean hidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hidden = getIntent().getBooleanExtra("hidden", false);


        if (hidden)
            setContentView(R.layout.activity_web);
        else
            setContentView(R.layout.activity_zst);

        wv = (WebView) findViewById(R.id.wv);
        flag = getIntent().getIntExtra("flag", flag);
        url = getIntent().getStringExtra("url");
        data = getIntent().getStringExtra("data");
        L.d("url", url);
        ll_finsh_web = (LinearLayout) findViewById(R.id.ll_finsh_web);
        ll_finsh_web.setOnClickListener(this);
        ll_finsh_web.setVisibility(View.VISIBLE);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        if (flag == 0) {
            wv.loadUrl(url);
        } else if (flag == 1) {
            wv.loadData(data, "", "text/html;utf-8");
        }
        title = getIntent().getStringExtra("title");


        if (!TextUtils.isEmpty(title)) {
            setCusTomeTitle(title);
        }
        showLoading("");
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
                    hideLoading();
                }

            }


        });
        wv.getSettings().setJavaScriptEnabled(true);

        wv.setWebViewClient(new WebViewClient() {
                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    if (flag == 0) {

                                        if (TextUtils.isEmpty(title) && !TextUtils.isEmpty(view.getTitle())) {
                                            setCusTomeTitle(view.getTitle());
                                        }


                                        wv.loadUrl(url);
                                    } else if (flag == 1)
                                        wv.loadData(data, "", "text/html;utf-8");
                                    return true;
                                }

                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    if (TextUtils.isEmpty(title)) {
                                        setCusTomeTitle(view.getTitle());
                                    } else {
                                        setCusTomeTitle(title);
                                    }
                                }


                            }


        );


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wv != null && wv.canGoBack()) {
                wv.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }


        } else {
            return super.onKeyDown(keyCode, event);
        }


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_finsh_web:
                finish();
                break;
            case R.id.ll_back:

                if (wv != null && wv.canGoBack()) {
                    wv.goBack();
                } else {
                    finish();
                }
                break;
        }
    }
}
