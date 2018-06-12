package com.yk.adverte.net.download;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yk.adverte.R;
import com.yk.adverte.common.Constants;
import com.yk.adverte.util.FileUtils;
import com.yk.adverte.view.glide.GlideUtils;

import java.io.File;

import static com.yk.adverte.util.FileUtils.getNameFromUrl;


public class DownLoadService extends IntentService {
    public String DOWNLOAD_URL = "";

    public DownLoadService() {
        super("DownLoadService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            DOWNLOAD_URL = intent.getStringExtra("url");
            String action = intent.getStringExtra("params");
            if (action.equals("splash")) {
                doDownload(DOWNLOAD_URL);
            }
        }
    }

    /**
     * Download
     */
    private void doDownload(final String url) {
        File file = new File(Environment
                .getExternalStorageDirectory(), Constants.DOWNLOAD_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        if (FileUtils.isFileExist(Constants.SDCARD_PATH + Constants.DOWNLOAD_PATH
                +  "splash.png")) {
            FileUtils.deleteFile(Constants.SDCARD_PATH + Constants.DOWNLOAD_PATH
                    + "splash.png");
        }

        DownloadUtil.get().download(url, Constants.DOWNLOAD_PATH,
                new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess() {
                        FileUtils.reNameFile(Constants.SDCARD_PATH + Constants.DOWNLOAD_PATH
                                + getNameFromUrl(url), Constants.SDCARD_PATH
                                + Constants.DOWNLOAD_PATH
                                + "splash.png");
                        if (FileUtils.isFileExist(Constants.SDCARD_PATH + Constants.DOWNLOAD_PATH
                                +  getNameFromUrl(url))) {
                            FileUtils.deleteFile(Constants.SDCARD_PATH + Constants.DOWNLOAD_PATH
                                    + getNameFromUrl(url));
                        }
                    }

                    @Override
                    public void onDownloading(int progress) {

                    }

                    @Override
                    public void onDownloadFailed() {


                    }
                });

    }
}
