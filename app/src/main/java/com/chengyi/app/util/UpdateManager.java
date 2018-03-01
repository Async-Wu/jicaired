package com.chengyi.app.util;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import com.chengyi.app.home.home.HomeActivity;
import com.chengyi.app.start.Update_model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class UpdateManager {

    Activity mContext;
    String currentTempFilePath;
    private String fileEx = "";
    private String fileNa = "";
    private DownloadManager download;
    private long enqueue;

    public UpdateManager(Activity mContext) {
        this.mContext = mContext;
    }

    public void getDataSource(String appPath) {
        URL myURL;
        try {
            fileEx = appPath.substring(appPath.lastIndexOf(".") + 1,
                    appPath.length()).toString();
            fileNa = appPath.substring(appPath.lastIndexOf("/") + 1,
                    appPath.length()).toString();
            myURL = new URL(appPath);

            HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
            conn.connect();

            InputStream is = conn.getInputStream();
            if (is == null) {
                return;
            }


            File tempDir = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "down");
            tempDir.mkdirs();

            File myTempFile = File.createTempFile(fileNa, "." + fileEx, tempDir);
            // get the path of the tem file
            currentTempFilePath = myTempFile.getAbsolutePath();
            fos = new FileOutputStream(myTempFile);
            byte buf[] = new byte[1024 * 4];
            do {
                int numread = is.read(buf);
                if (numread <= 0) {
                    break;
                }
                fos.write(buf, 0, numread);
            } while (true);
            // open app file
            openFile(myTempFile);
            try {
                is.close();
            } catch (Exception ex) {
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private String currentFilePath = "";

    public void getFile(final String strPath) {
        try {
            if (strPath.equals(currentFilePath)) {
                getDataSource(strPath);
            }
            currentFilePath = strPath;
            Runnable r = new Runnable() {

                @Override
                public void run() {
                    try {
                        getDataSource(strPath);
                    } catch (Exception e) {
                    }
                }
            };

            new Thread(r).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler();
    private FileOutputStream fos;

    private void openFile(final File f) {


        handler.post(new Runnable() {

            @Override
            public void run() {
                try {
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setAction(Intent.ACTION_VIEW);
                    String type = "application/vnd.android.package-archive";
                    intent.setDataAndType(Uri.fromFile(f), type);
                    mContext.startActivity(intent);
                    HomeActivity acb = (HomeActivity) mContext;
                    acb.hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * use  android sys  get  file download
     *
     * @param model
     */
    public void downLoad(Update_model  model) {


        download = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(model.getAndroid_update_url()));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle(model.getUpdate_desc());
        request.setVisibleInDownloadsUi(true);
        request.setDescription(model.getUpdate_desc());
        enqueue = download.enqueue(request);

    }



    public void installApk(long id) {
        if (id==enqueue) {
            final Uri uri = download.getUriForDownloadedFile(id);
            handler.post(new Runnable() {

                @Override
                public void run() {
                    try {
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction(Intent.ACTION_VIEW);
                        String type = "application/vnd.android.package-archive";
                        intent.setDataAndType(uri, type);
                        mContext.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }


}
