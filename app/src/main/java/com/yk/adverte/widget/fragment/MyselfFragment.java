package com.yk.adverte.widget.fragment;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yk.adverte.R;
import com.yk.adverte.base.BaseFragment;
import com.yk.adverte.common.Constants;
import com.yk.adverte.impl.PermissionResultListener;
import com.yk.adverte.model.bean.EventBean;
import com.yk.adverte.net.rx.RxBus;
import com.yk.adverte.util.AppUtils;
import com.yk.adverte.util.FileUtils;
import com.yk.adverte.util.SPUtil;
import com.yk.adverte.util.ToastUtils;
import com.yk.adverte.view.glide.GlideUtils;
import com.yk.adverte.widget.activity.LoginActivity;
import com.yk.adverte.widget.activity.SettingActivity;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.yk.adverte.common.MsgBox.PER_REQUEST_CODE;


/**
 * Created by Silence on 2018/3/27.
 */

public class MyselfFragment extends BaseFragment {

    CircleImageView mImgUser;
    TextView mTxtName, mTxtUpdate, mTxtAbout, mTxtLogin;
    AppBarLayout mAppBar;
    private String mLastVersion;
    // 当前版本名
    private String mVersionName;
    // 当前版本名
    private int mVersionCode;
    ImageView mImgNext;
    SPUtil mSPUtil;

    @Override
    public int setFragmentLayoutID() {
        return R.layout.fragment_myself;
    }

    @Override
    protected void initView(View view) {
        mSPUtil = new SPUtil(mActivity);
        mImgUser = view.findViewById(R.id.img_myself_user);
        mTxtName = view.findViewById(R.id.txt_myself_name);
        mTxtUpdate = view.findViewById(R.id.txt_myself_update);
        mTxtAbout = view.findViewById(R.id.txt_myself_about);
        mAppBar = view.findViewById(R.id.appbar_layout);
        mImgNext = view.findViewById(R.id.img_next);
        mTxtLogin = view.findViewById(R.id.txt_myself_login);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        mVersionName = AppUtils.getVerName(getActivity());
        mVersionCode = AppUtils.getVerCode(getActivity());
        setUserIcon();
        if (mSPUtil.getUserFlag(Constants.USER_FLAG).equals("1")) {
            mTxtLogin.setVisibility(View.GONE);
            mTxtName.setVisibility(View.VISIBLE);
        } else {
            mTxtName.setVisibility(View.GONE);
            mTxtLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initBus();
    }


    @Override
    public void initClick(View view) {
        switch (view.getId()) {
            case R.id.img_next: {//设置
                startActivity(SettingActivity.class);
                break;
            }
            case R.id.txt_myself_update: {//更新
                ToastUtils.showMessage(mActivity,
                        getResources().getString(R.string.lastet_version));
                break;
            }
            case R.id.txt_myself_about: {//关于我们
                break;
            }
            case R.id.txt_myself_login: {//登录
                startActivity(LoginActivity.class);
                break;
            }
        }
    }

    @Override
    protected void initListener() {
        mTxtLogin.setOnClickListener(this);
        mTxtUpdate.setOnClickListener(this);
        mTxtAbout.setOnClickListener(this);
        mImgNext.setOnClickListener(this);
    }

    /**
     * 设置用户头像
     */
    protected void setUserIcon() {
        performRequestPermissions(getString(R.string.permission_desc),
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PER_REQUEST_CODE,
                new PermissionResultListener() {
                    @Override
                    public void onPermissionDenied() {

                    }

                    @Override
                    public void onPermissionGranted() {
                        /**
                         * 设置用户头像
                         */
                        if (FileUtils.isFileExist(Constants.USER_PIC_NAME)) {
                            GlideUtils.loadLocalFile(mActivity,
                                    new File(Constants.USER_PIC_NAME),
                                    R.drawable.errorview,
                                    R.drawable.errorview,
                                    DiskCacheStrategy.NONE,
                                    mImgUser);
                        } else {
                            GlideUtils.loadResource(mActivity,
                                    R.drawable.errorview,
                                    R.drawable.errorview,
                                    R.drawable.errorview,
                                    DiskCacheStrategy.NONE,
                                    mImgUser);
                        }


                    }
                });

    }


    /**
     * 初始化
     */
    private void initBus() {
        if (RxBus.getInstance().hasObservers())
            RxBus.getInstance().toObservable().map(new Function<Object, EventBean>() {
                @Override
                public EventBean apply(Object o) throws Exception {
                    return (EventBean) o;
                }
            }).subscribe(new Consumer<EventBean>() {
                @Override
                public void accept(EventBean eventMsg) throws Exception {
                    if (eventMsg != null && eventMsg.getResult().equals(Constants.UPDATE_PHOTO_FLAG)) {
                        // 从SD卡中找头像，转换成Bitmap
                        Bitmap bitmap = BitmapFactory.decodeFile(Constants.USER_PIC_NAME);
                        if (bitmap != null) {
                            mImgUser.setImageBitmap(bitmap);
                        } else {
                            mImgUser.setBackgroundResource(R.drawable.ic_user);
                        }
                    }
                }
            });
    }


}
