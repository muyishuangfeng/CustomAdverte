package com.yk.adverte.widget.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.yk.adverte.R;
import com.yk.adverte.base.BaseActivity;
import com.yk.adverte.common.Constants;
import com.yk.adverte.common.MsgBox;
import com.yk.adverte.impl.GpsThread;
import com.yk.adverte.impl.PermissionResultListener;
import com.yk.adverte.model.bean.GpsBean;
import com.yk.adverte.util.AMapUtil;
import com.yk.adverte.util.GpsUtils;
import com.yk.adverte.util.SPUtil;
import com.yk.adverte.util.StatusBarUtils;
import com.yk.adverte.util.ToastUtils;
import com.yk.adverte.view.CustomInfoWindowAdapter;
import com.yk.adverte.view.title.CustomTitleBar;

import java.io.File;

import static com.yk.adverte.common.MsgBox.PER_REQUEST_CODE;


public class DetailActivity extends BaseActivity implements
        CustomTitleBar.TitleClickListener, AMap.OnCameraChangeListener,
        GeocodeSearch.OnGeocodeSearchListener, AMap.OnMarkerClickListener {

    CustomTitleBar mTitle;
    public MapView mMapView;
    GpsThread mThread;
    private LatLonPoint mLatLonPoint;
    Marker mCenterMarker = null;
    AMap mAMap;
    UiSettings mUISetting;
    SPUtil mSPUTil;
    private GeocodeSearch mCoderSearch;
    private String address;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_detail);
    }

    @Override
    public void initView() {
        mSPUTil = new SPUtil(this);
        mTitle = findViewById(R.id.detail_title);
        mMapView = findViewById(R.id.map);
        mTitle.setTitleClickListener(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected void initIntentParam(Intent intent) {

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            mUISetting = mAMap.getUiSettings();
            mUISetting.setZoomControlsEnabled(false);
        }
        if (mSPUTil.contains(this, Constants.LATITUDE) &&
                mSPUTil.contains(this, Constants.LONGITUDE)) {
            initMap(mSPUTil.getLat(Constants.LATITUDE),
                    mSPUTil.getLong(Constants.LONGITUDE));
        } else if (!GpsUtils.checkGPSIsOpen(this)) {
            openGPSSettings();
        }
    }

    @Override
    public void initClick(View view) {

    }

    @Override
    public void setListener() {

    }


    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }

    /**
     * Handler
     */
    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case MsgBox.MSG_LOCATION_SUCCESS: {//定位成功
                    GpsBean mBean = (GpsBean) msg.obj;
                    if (mBean != null) {
                        setAddress(mBean.getAddress(), mBean.getCityName(),
                                mBean.getLatitude(), mBean.getLongitude());
                        initMap(String.valueOf(mBean.getLatitude()),
                                String.valueOf(mBean.getLongitude()));
                    }
                    break;
                }
                case MsgBox.MSG_LOCATION_FAILED: {//定位失败
                    ToastUtils.showMessage(DetailActivity.this, "定位失败");
                    break;
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (mThread != null) {
            mThread = null;
        }
    }

    /**
     * 设置详细信息
     *
     * @param address
     */
    private void setAddress(String address, String city, double mLat, double mLong) {
        mSPUTil.putCity(Constants.CITY_NAME, city);
        mSPUTil.putAddress(Constants.ADDRESS_DETAIL, address);
        mSPUTil.putLat(Constants.LATITUDE, mLat + "");
        mSPUTil.putLong(Constants.LONGITUDE, mLong + "");
    }

    /**
     * 初始化地图
     */
    private void initMap(String mLat, String mLong) {
        if (mCenterMarker != null) {
            mCenterMarker.destroy();
        }
        mLatLonPoint = new LatLonPoint(Double.valueOf(mLat), Double.valueOf(mLong));
        mCoderSearch = new GeocodeSearch(this);
        mCoderSearch.setOnGeocodeSearchListener(this);
        mAMap.setOnCameraChangeListener(this);
        mAMap.setOnMarkerClickListener(this);
        mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                AMapUtil.convertToLatLng(mLatLonPoint), 12));
    }

    /**
     * 在屏幕中心添加一个Marker
     */
    private void addMarkerInScreenCenter(String address) {
        if (mCenterMarker != null) {
            mCenterMarker.destroy();
        }
        LatLng latLng = mAMap.getCameraPosition().target;
        Point screenPosition = mAMap.getProjection().toScreenLocation(latLng);
        mCenterMarker = mAMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .title(address)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)));
        mAMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this, address));
        mCenterMarker.showInfoWindow();
        //设置Marker在屏幕上,不跟随地图移动
        mCenterMarker.setPositionByPixels(screenPosition.x, screenPosition.y);

    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 10,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，
        // 第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        mCoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                address = result.getRegeocodeAddress().getFormatAddress();
                addMarkerInScreenCenter(address);
            } else {
                ToastUtils.showMessage(DetailActivity.this,
                        getResources().getString(R.string.no_result));
            }
        } else {
            ToastUtils.showMessage(this, rCode + "");
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        //屏幕中心的Marker跳动
        mLatLonPoint = new LatLonPoint(cameraPosition.target.latitude,
                cameraPosition.target.longitude);
        addMarkerInScreenCenter(mSPUTil.getAddress(Constants.CITY_NAME));
        //getAddress(mLatLonPoint);

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        ToastUtils.showMessage(this, marker.getTitle());
        return false;
    }

    /**
     * 跳转GPS设置
     */
    private void openGPSSettings() {
        //没有打开则弹出对话框
        new AlertDialog.Builder(this)
                .setTitle(R.string.notifyTitle)
                .setMessage(R.string.gpsNotifyMsg)
                // 拒绝, 退出应用
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })

                .setPositiveButton(R.string.setting,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //跳转GPS设置界面
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(intent, MsgBox.GPS_REQUEST_CODE);
                            }
                        })

                .setCancelable(false)
                .show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MsgBox.GPS_REQUEST_CODE) {
            //做需要做的事情，比如再次检测是否打开GPS了 或者定位
            GpsThread mThread = new GpsThread(this, mHandler);
            mThread.start();
        }

    }

}



