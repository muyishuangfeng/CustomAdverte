package com.yk.adverte.widget.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.yk.adverte.R;
import com.yk.adverte.base.BaseFragment;
import com.yk.adverte.impl.PermissionResultListener;
import com.yk.adverte.view.FullyGridLayoutManager;
import com.yk.adverte.widget.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MonthFragment extends BaseFragment {

    RecyclerView mRlvMonth;
    TextView mTxtCharge;
    Button mBtnSure;
    EditText mEdtRemark, mEdtSlogan;
    ImageAdapter mAdapter;
    FullyGridLayoutManager mManager;
    List<LocalMedia> mList = new ArrayList<>();
    private int themeId;
    private static final int REQUEST_CODE = 1;

    @Override
    public int setFragmentLayoutID() {
        return R.layout.fragment_month;
    }

    @Override
    protected void initView(View view) {
        themeId = R.style.picture_default_style;
        mRlvMonth = view.findViewById(R.id.rlv_month);
        mTxtCharge = view.findViewById(R.id.txt_month_charge);
        mBtnSure = view.findViewById(R.id.btn_month_sure);
        mEdtRemark = view.findViewById(R.id.edt_month_remark);
        mEdtSlogan = view.findViewById(R.id.edt_month_slogan);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        setAdapter();
        hideKeyboard();
    }

    @Override
    public void initClick(View view) {
        switch (view.getId()) {
            case R.id.btn_month_sure: {//确定
                break;
            }
        }
    }

    @Override
    protected void initListener() {
        mBtnSure.setOnClickListener(this);
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mManager = new FullyGridLayoutManager(mActivity, 4,
                GridLayoutManager.VERTICAL, false);
        mRlvMonth.setLayoutManager(mManager);
        mAdapter = new ImageAdapter(mActivity, onAddPicClickListener);
        mAdapter.setList(mList);
        mAdapter.setSelectMax(9);

        mAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                LocalMedia media = mList.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片
                        PictureSelector.create(MonthFragment.this).themeStyle(themeId)
                                .openExternalPreview(position, mList);
                        break;
                    case 2:
                        // 预览视频
                        PictureSelector.create(MonthFragment.this).externalPictureVideo(media.getPath());
                        break;
                    case 3:
                        // 预览音频
                        PictureSelector.create(MonthFragment.this).externalPictureAudio(media.getPath());
                        break;
                }
            }
        });
        mRlvMonth.setAdapter(mAdapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    if (data != null) {
                        mList = PictureSelector.obtainMultipleResult(data);
                        mAdapter.setList(mList);
                        Log.e("data", mList.size() + "");
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }

    private ImageAdapter.onAddPicClickListener onAddPicClickListener =
            new ImageAdapter.onAddPicClickListener() {
                @Override
                public void onAddPicClick() {
                    // 进入相册 以下是例子：不需要的api可以不写
                    PictureSelector.create(MonthFragment.this)
                            .openGallery(PictureMimeType.ofAll())
                            .theme(themeId)
                            .maxSelectNum(9)
                            .minSelectNum(1)
                            .isCamera(true)
                            .compress(true)
                            .selectionMedia(mList)
                            .forResult(PictureConfig.CHOOSE_REQUEST);

                }
            };

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        deleteCache();

    }

    /**
     * 隐藏软键盘
     *
     * @return
     */
    private boolean hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) mActivity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive(mEdtRemark)) {
            //因为是在fragment下，所以用了getView()获取view，也可以用findViewById（）来获取父控件
            getView().requestFocus();
            //使其它view获取焦点.这里因为是在fragment下,所以便用了getView(),可以指定任意其它view
            inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        }
        return false;
    }

    /**
     * 清除缓存
     */
    private void deleteCache() {
        performRequestPermissions(getResources().getString(R.string.permission_desc),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE,
                new PermissionResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
                        PictureFileUtils.deleteCacheDirFile(mActivity);
                    }

                    @Override
                    public void onPermissionDenied() {

                    }
                });
    }
}
