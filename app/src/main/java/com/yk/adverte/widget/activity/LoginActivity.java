package com.yk.adverte.widget.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.yk.adverte.R;
import com.yk.adverte.base.BaseActivity;
import com.yk.adverte.model.UserModel;
import com.yk.adverte.model.bean.UserBean;
import com.yk.adverte.net.impl.RequestImpl;
import com.yk.adverte.util.InputMethodUtils;
import com.yk.adverte.util.SPUtil;
import com.yk.adverte.util.ToastUtils;
import com.yk.adverte.view.title.CustomTitleBar;

public class LoginActivity extends BaseActivity implements CustomTitleBar.TitleClickListener {

    CustomTitleBar mTitle;
    //用户名、密码
    private TextInputEditText mEdtUser, mEdtPass;
    //登录、注册
    private Button mBtnLogin, mBtnRegister;
    // 串码
    private SPUtil mSPUtl;
    //忘记密码
    private TextView mTxtForgetPassword;
    UserModel mModel;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        mModel = new UserModel();
        mSPUtl = new SPUtil(this);
        mTitle = $(R.id.login_titleBar);
        mBtnLogin = $(R.id.btn_login);
        mBtnRegister = $(R.id.btn_register);
        mEdtUser = $(R.id.edt_login_phone);
        mEdtPass = $(R.id.edt_login_pass);
        mTxtForgetPassword = $(R.id.txt_forget_password);

    }

    @Override
    public void initData() {
    }

    @Override
    public void initClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login: {//登录
                login();
                break;
            }
            case R.id.btn_register: {//注册
                startActivity(RegisterActivity.class);
                break;
            }
            case R.id.txt_forget_password: {//忘记密码
                InputMethodUtils.hiddenInputMethod(this);
                break;
            }

        }
    }

    @Override
    public void setListener() {
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mTitle.setTitleClickListener(this);
        mTxtForgetPassword.setOnClickListener(this);
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

    /**
     * 登录
     */
    private void login() {
        if (TextUtils.isEmpty(mEdtUser.getText().toString().trim())) {
            ToastUtils.showMessage(this, getResources().getString(R.string.input_phone));
            return;
        }
        if (TextUtils.isEmpty(mEdtPass.getText().toString().trim())) {
            ToastUtils.showMessage(this, getResources().getString(R.string.input_password));
            return;
        }

        mModel.setData("", mEdtUser.getText().toString(), mEdtPass.getText().toString());
        mModel.getData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                UserBean userBean= (UserBean) object;

            }

            @Override
            public void loadFailed() {
                ToastUtils.showMessage(LoginActivity.this,
                        getResources().getString(R.string.failed));
            }
        });
    }

}
