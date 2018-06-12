package com.yk.adverte.widget.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yalantis.ucrop.UCrop;
import com.yk.adverte.R;
import com.yk.adverte.base.BaseActivity;
import com.yk.adverte.common.Constants;
import com.yk.adverte.model.bean.EventBean;
import com.yk.adverte.net.rx.RxBus;
import com.yk.adverte.util.RxPhotoTool;
import com.yk.adverte.util.SPUtil;
import com.yk.adverte.util.ToastUtils;
import com.yk.adverte.view.dialog.RxDialogChooseImage;
import com.yk.adverte.view.glide.GlideUtils;
import com.yk.adverte.view.title.CustomTitleBar;


import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.yk.adverte.common.Constants.USER_PIC_NAME;


public class SettingActivity extends BaseActivity implements
        CustomTitleBar.TitleClickListener {

    CustomTitleBar mTitle;
    LinearLayout mLytIcon, mLytNickName;
    TextView mTxtNickName;
    CircleImageView mImgUser;
    SPUtil mSPUtils;
    Uri mCutUri;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initView() {
        mSPUtils = new SPUtil(this);
        mTitle = $(R.id.myself_title);
        mLytIcon = $(R.id.lyt_myself_icon);
        mLytNickName = $(R.id.lyt_myself_nickname);
        mTxtNickName = $(R.id.txt_myself_nickname);
        mImgUser = $(R.id.img_myself_icon);
    }

    @Override
    public void initData() {
        setUserIcon();
        if (!TextUtils.isEmpty(mSPUtils.getUsername(Constants.USER_NAME).trim())) {
            mTxtNickName.setText(mSPUtils.getUsername(Constants.USER_NAME).trim());
        } else {
            mTxtNickName.setText(getResources().getString(R.string.text_default));
        }
    }



    @Override
    public void initClick(View view) {
        switch (view.getId()) {
            case R.id.lyt_myself_icon: {//头像
                RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(this,
                        RxDialogChooseImage.LayoutType.TITLE);
                dialogChooseImage.show();
                break;
            }


        }
    }

    @Override
    public void setListener() {
        mTitle.setTitleClickListener(this);
        mLytNickName.setOnClickListener(this);
        mLytIcon.setOnClickListener(this);
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
     * 设置用户头像
     */
    protected void setUserIcon() {
        GlideUtils.loadLocalFile(this,
                new File(USER_PIC_NAME),
                R.drawable.errorview,
                R.drawable.errorview,
                DiskCacheStrategy.NONE,
                mImgUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
                    RxPhotoTool.initUCrop(this, data.getData());
                }

                break;
            case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    // 裁剪图片
                    RxPhotoTool.initUCrop(this, RxPhotoTool.imageUriFromCamera);
                }

                break;
            case RxPhotoTool.CROP_IMAGE://普通裁剪后的处理
                GlideUtils.loadUri(this,
                        RxPhotoTool.cropImageUri,
                        R.drawable.errorview,
                        R.drawable.errorview,
                        DiskCacheStrategy.NONE,
                        mImgUser);
                RxBus.getInstance().post(new EventBean(Constants.UPDATE_PHOTO_FLAG));
                break;

            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    mCutUri = UCrop.getOutput(data);
                    RxBus.getInstance().post(new EventBean(Constants.UPDATE_PHOTO_FLAG));
                    RxPhotoTool.loadImageView(this, mCutUri, mImgUser);
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                    ToastUtils.showMessage(this, cropError.toString());
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                ToastUtils.showMessage(this, cropError.toString());
                break;
            default:
                break;
        }

    }




}
