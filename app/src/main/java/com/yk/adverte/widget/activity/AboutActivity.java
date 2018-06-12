package com.yk.adverte.widget.activity;

import android.content.Intent;
import android.view.View;

import com.yk.adverte.R;
import com.yk.adverte.base.BaseActivity;
import com.yk.adverte.view.title.CustomTitleBar;

public class AboutActivity extends BaseActivity implements
        CustomTitleBar.TitleClickListener {

    CustomTitleBar mTitle;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_about);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick(View view) {

    }

    @Override
    public void setListener() {
        mTitle.setTitleClickListener(this);
    }

    @Override
    protected void initIntentParam(Intent intent) {

    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}
