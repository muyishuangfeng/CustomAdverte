package com.yk.adverte.widget.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yk.adverte.R;
import com.yk.adverte.base.BaseActivity;
import com.yk.adverte.util.ToastUtils;
import com.yk.adverte.view.title.CustomTitleBar;

public class ChargeDetailActivity extends BaseActivity implements CustomTitleBar.TitleClickListener {


    CustomTitleBar mTitleBar;
    TextView  mTxtYear, mTxtMonth, mTxtDay;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_charge_detail);
    }

    @Override
    public void initView() {
        mTitleBar = $(R.id.charge_title);
        mTxtDay = $(R.id.txt_charge_day);
        mTxtMonth = $(R.id.txt_charge_month);
        mTxtYear = $(R.id.txt_charge_year);
    }

    @Override
    public void initData() {
        mTxtDay.setText(Html.fromHtml(getString(R.string.text_charge_day)));
        mTxtMonth.setText(Html.fromHtml(getString(R.string.text_charge_month)));
        mTxtYear.setText(Html.fromHtml(getString(R.string.text_charge_year)));
    }

    @Override
    public void initClick(View view) {

    }

    @Override
    public void setListener() {
        mTitleBar.setTitleClickListener(this);
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
