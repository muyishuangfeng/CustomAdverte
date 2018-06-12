package com.yk.adverte.widget.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.yk.adverte.R;
import com.yk.adverte.base.BaseFragment;
import com.yk.adverte.common.Constants;
import com.yk.adverte.common.MsgBox;
import com.yk.adverte.impl.GpsThread;
import com.yk.adverte.impl.PermissionResultListener;
import com.yk.adverte.model.bean.JsonBean;
import com.yk.adverte.net.upload.FileUploadObserver;
import com.yk.adverte.net.upload.RetrofitClient;
import com.yk.adverte.util.JsonParseUtils;
import com.yk.adverte.util.LogUtils;
import com.yk.adverte.util.StatusBarUtils;
import com.yk.adverte.util.ToastUtils;
import com.yk.adverte.view.FullyGridLayoutManager;
import com.yk.adverte.widget.activity.ChargeDetailActivity;
import com.yk.adverte.widget.adapter.ImageAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

import static com.yk.adverte.common.MsgBox.PER_REQUEST_CODE;


/**
 * Created by Silence on 2018/3/27.
 */

public class HomeFragment extends BaseFragment {

    TextView mTxtAddress, mTxtCharge;
    private ArrayList<JsonBean> mProvinceList = new ArrayList<>();
    private ArrayList<ArrayList<String>> mCityList = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> mCountryList = new ArrayList<>();
    private Thread thread;
    private boolean isLoaded = false;
    //预约代驾
    LinearLayout mLytAddress;
    EditText mEdtRemark, mEdtContent;
    RecyclerView mRlvHome;
    Button mBtnSure;
    ImageAdapter mAdapter;
    FullyGridLayoutManager mManager;
    List<LocalMedia> mList = new ArrayList<>();
    private int themeId;
    private static final int REQUEST_CODE = 0;
    File mFile;
    String[] mItems = {"按天收费", "按月收费", "按年收费"};
    MaterialSpinner mSpnHome;
    String mCharge = "";
    boolean isSelected = false;

    @Override
    public int setFragmentLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        themeId = R.style.picture_default_style;
        mTxtAddress = view.findViewById(R.id.txt_address);
        mLytAddress = view.findViewById(R.id.lyt_home_address);
        mEdtRemark = view.findViewById(R.id.edt_home_remark);
        mSpnHome = view.findViewById(R.id.spn_home);
        mEdtContent = view.findViewById(R.id.edt_content);
        mRlvHome = view.findViewById(R.id.rlv_home);
        mBtnSure = view.findViewById(R.id.btn_home_sure);
        mTxtCharge = view.findViewById(R.id.txt_charge_detail);
        //initPageAdapter();
        File file = new File(Constants.IMAGE_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        setSpinner();
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        mHandler.sendEmptyMessage(MsgBox.MSG_LOAD_DATA);
        setAdapter();

    }

    @Override
    public void initClick(View view) {
        switch (view.getId()) {
            case R.id.lyt_home_address: {//地址
                if (isLoaded)
                    showPickerView();
                else
                    ToastUtils.showMessage(mActivity,
                            getResources().getString(R.string.load_error));
                break;
            }
            case R.id.btn_home_sure: {//确认发布
                upload();
                break;
            }
            case R.id.txt_charge_detail: {//详情
                if (!TextUtils.isEmpty(mCharge)) {
                    Intent intent = new Intent(mActivity, ChargeDetailActivity.class);
                    intent.putExtra("charge", mCharge);
                    startActivity(intent);
                }
                break;
            }
        }
    }

    @Override
    protected void initListener() {
        mLytAddress.setOnClickListener(this);
        mBtnSure.setOnClickListener(this);
        mTxtCharge.setOnClickListener(this);
    }


    /**
     * 解析数据
     */
    private void initJsonData() {
        //获取assets目录下的json文件数据
        String JsonData = JsonParseUtils.getJson(mActivity, "province.json");
        ArrayList<JsonBean> jsonBean = JsonParseUtils.parseData(JsonData, mHandler,
                MsgBox.MSG_LOAD_FAILED);//用Gson 转成实体
        mProvinceList = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            //添加城市数据
            mCityList.add(CityList);
            //添加地区数据
            mCountryList.add(Province_AreaList);
        }
        mHandler.sendEmptyMessage(MsgBox.MSG_LOAD_SUCCESS);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }

    }

    /**
     * 城市选择
     */
    private void showPickerView() {// 弹出选择器
        OptionsPickerView mOptions = new OptionsPickerBuilder(mActivity,
                new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String mTxtContent = mProvinceList.get(options1).getPickerViewText() +
                                mCityList.get(options1).get(options2) +
                                mCountryList.get(options1).get(options2).get(options3);
                        mTxtAddress.setText(mTxtContent);
                    }
                })
                .setTitleText(getResources().getString(R.string.choose_city))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        //三级选择器
        mOptions.setPicker(mProvinceList, mCityList, mCountryList);
        mOptions.show();
    }

    /**
     * Handler
     */
    private Handler mHandler = new Handler(Looper.myLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MsgBox.MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MsgBox.MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MsgBox.MSG_LOAD_FAILED:
                    ToastUtils.showMessage(mActivity, getResources().getString(R.string.load_error));
                    break;
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    if (data != null) {
                        mList = PictureSelector.obtainMultipleResult(data);
                        for (int i = 0; i < mList.size(); i++) {
                            mFile = new File(mList.get(i).getCompressPath());
                        }
                        mAdapter.setList(mList);
                        mAdapter.notifyDataSetChanged();
                        isSelected = true;
                    }
                    break;
            }
        }
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        mManager = new FullyGridLayoutManager(mActivity, 4,
                GridLayoutManager.VERTICAL, false);
        mRlvHome.setLayoutManager(mManager);
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
                        PictureSelector.create(HomeFragment.this).themeStyle(themeId)
                                .openExternalPreview(position, mList);
                        break;
                    case 2:
                        // 预览视频
                        PictureSelector.create(HomeFragment.this).externalPictureVideo(media.getPath());
                        break;
                    case 3:
                        // 预览音频
                        PictureSelector.create(HomeFragment.this).externalPictureAudio(media.getPath());
                        break;
                }
            }
        });
        mRlvHome.setAdapter(mAdapter);
    }


    private ImageAdapter.onAddPicClickListener onAddPicClickListener =
            new ImageAdapter.onAddPicClickListener() {
                @Override
                public void onAddPicClick() {
                    // 进入相册 以下是例子：不需要的api可以不写
                    PictureSelector.create(HomeFragment.this)
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

    /**
     * 上传文件
     */
    private void upload() {
        if (mList.size() > 0 && !isSelected) {
            Log.e("上传地址", mFile.getAbsolutePath());
            RetrofitClient.getInstance().upLoadFile("", mFile,
                    new FileUploadObserver<ResponseBody>() {
                        @Override
                        public void onUpLoadSuccess(ResponseBody responseBody) {
                            if (responseBody == null) {
                                LogUtils.e("responseBody null");
                                return;
                            }
                            try {
                                JSONObject jsonObject = new JSONObject(responseBody.string());
                                ArrayList<String> fileIds = new ArrayList<>();
                                fileIds.add(jsonObject.getString("fileId"));

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onUpLoadFail(Throwable e) {

                        }

                        @Override
                        public void onProgress(int progress) {
                            LogUtils.d(String.valueOf(progress));
                        }
                    });
        }else {
            ToastUtils.showMessage(mActivity,getResources().getString(R.string.choose_picture));
        }

    }

    /**
     * 设置spinner
     */
    private void setSpinner() {
        mSpnHome.setItems(mItems);
        mSpnHome.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                mCharge = (String) item;

            }
        });

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        deleteCache();
    }

}



