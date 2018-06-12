package com.yk.adverte.widget.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.yk.adverte.R;
import com.yk.adverte.base.BaseActivity;
import com.yk.adverte.common.Constants;
import com.yk.adverte.model.UserModel;
import com.yk.adverte.model.bean.UserBean;
import com.yk.adverte.net.impl.RequestImpl;
import com.yk.adverte.util.InputMethodUtils;
import com.yk.adverte.util.SPUtil;
import com.yk.adverte.util.ToastUtils;
import com.yk.adverte.view.title.CustomTitleBar;


public class RegisterActivity extends BaseActivity implements
        CustomTitleBar.TitleClickListener {

    //手机号、密码、验证码
    TextInputEditText mEdtPhone, mEdtPassword, mEdtCode;
    TextInputLayout mTxtPhone, mTxtPassword;
    //注册、验证码
    Button mBtnRegister, mBtnCode;
    private static final long WAIT_TIME = 60 * 1000;
    private CustomTitleBar mTitle;
    SPUtil mSPUTil;
    UserModel mModel;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initView() {
        mModel = new UserModel();
        mSPUTil = new SPUtil(this);
        mEdtPhone = $(R.id.edt_register_phone);
        mEdtCode = $(R.id.edt_register_code);
        mEdtPassword = $(R.id.edt_register_pass);
        mTxtPassword = $(R.id.txt_register_pass);
        mTxtPhone = $(R.id.txt_register_phone);
        mBtnRegister = $(R.id.btn_register);
        mBtnCode = $(R.id.btn_register_code);
        mTitle = $(R.id.register_title);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register: {//注册
                register();
                InputMethodUtils.hiddenInputMethod(this);
            }
            break;
            case R.id.btn_register_code: {//验证码
                getCode();
            }
            break;
        }
    }

    @Override
    public void setListener() {
        mBtnRegister.setOnClickListener(this);
        mTitle.setTitleClickListener(this);
        mBtnCode.setOnClickListener(this);
        mEdtCode.addTextChangedListener(mTextWatcher);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    /**
     * 由于CountDownTimer有一定的延迟，所以这里设置3400
     */
    private CountDownTimer mCountDownTimer = new CountDownTimer(WAIT_TIME,
            1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mBtnCode.setText("(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            mBtnCode.setText(getResources().getString(R.string.text_finish));
        }
    };

    /**
     *
     */
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBtnCode.setText(getResources().getString(R.string.text_finish));
                    }
                });
            }

        }
    };

    /**
     * 注册
     */
    private void register() {
        if (TextUtils.isEmpty(mEdtPhone.getText().toString().trim())) {
            ToastUtils.showMessage(RegisterActivity.this,
                    getResources().getString(R.string.input_phone));
            return;
        } else {
            mSPUTil.putUserTell(Constants.USER_TELL, mEdtPhone.getText().toString().trim());
        }
        if (TextUtils.isEmpty(mEdtPassword.getText().toString().trim())) {
            ToastUtils.showMessage(RegisterActivity.this,
                    getResources().getString(R.string.input_password));
            return;
        } else {
            mSPUTil.putPassWord(Constants.USER_PASS, mEdtPassword.getText().toString().trim());
        }
        if (TextUtils.isEmpty(mEdtCode.getText().toString().trim())) {
            ToastUtils.showMessage(RegisterActivity.this,
                    getResources().getString(R.string.input_verification_code));
            return;
        }
        mModel.setRegister("",
                mEdtPhone.getText().toString(),
                mEdtCode.getText().toString(),
                mEdtPassword.getText().toString());
        mModel.getRegister(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                UserBean userBean = (UserBean) object;
            }

            @Override
            public void loadFailed() {
                ToastUtils.showMessage(RegisterActivity.this,
                        getResources().getString(R.string.failed));
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        if (TextUtils.isEmpty(mEdtPhone.getText().toString().trim())) {
            ToastUtils.showMessage(RegisterActivity.this,
                    getResources().getString(R.string.input_phone));
            return;
        }
        mCountDownTimer.start();
        mModel.setCode("", mEdtPhone.getText().toString());
        mModel.getCode(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                String code = (String) object;
                mEdtCode.setText(code);
            }

            @Override
            public void loadFailed() {

            }
        });
    }

}
