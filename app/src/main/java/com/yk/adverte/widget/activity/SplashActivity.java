package com.yk.adverte.widget.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yk.adverte.R;
import com.yk.adverte.base.BaseActivity;
import com.yk.adverte.common.AppStatusConstant;
import com.yk.adverte.common.AppStatusManager;
import com.yk.adverte.common.Constants;
import com.yk.adverte.net.download.DownLoadService;
import com.yk.adverte.view.glide.GlideUtils;

import java.io.File;

public class SplashActivity extends BaseActivity {

    ImageView mImgSplash;
    Button mBtnJump;
    String url="http://www.baidu.com";


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void initView() {
        mImgSplash = $(R.id.img_splash);
        mBtnJump = $(R.id.btn_jump);
    }

    @Override
    public void initData() {
        File file = new File(Constants.SDCARD_PATH + Constants.DOWNLOAD_PATH
                + "splash.png");
        if (!file.exists()) {
            Intent intent = new Intent(this, DownLoadService.class);
            intent.putExtra("url", url);
            intent.putExtra("params", "splash");
            startService(intent);
            GlideUtils.loadResource(this,
                    R.drawable.ic_guide_4,
                    R.drawable.errorview,
                    R.drawable.errorview,
                    DiskCacheStrategy.NONE,
                    mImgSplash);
        } else {
            GlideUtils.loadLocalFile(this,
                    file,
                    R.drawable.errorview,
                    R.drawable.errorview,
                    DiskCacheStrategy.NONE,
                    mImgSplash);
        }
        mCountDownTimer.start();
    }


    @Override
    public void initClick(View view) {
        switch (view.getId()) {
            case R.id.btn_jump: {//跳过
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
                startActivity(MainActivity.class);
                finish();
                break;
            }
        }
    }

    @Override
    public void setListener() {
        mBtnJump.setOnClickListener(this);
    }

    @Override
    protected void initIntentParam(Intent intent) {

    }

    /**
     * 由于CountDownTimer有一定的延迟，所以这里设置3400
     */
    private CountDownTimer mCountDownTimer = new CountDownTimer(3400,
            1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mBtnJump.setText("剩余" + millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            mBtnJump.setText("完成");
            startActivity(MainActivity.class);
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }
}
