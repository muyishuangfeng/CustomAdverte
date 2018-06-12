package com.yk.adverte.base;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yk.adverte.R;
import com.yk.adverte.impl.PermissionResultListener;
import com.yk.adverte.view.dialog.LoadingDialog;


/**
 * Created by Silence on 2017/8/15.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    //视图是否创建
    protected boolean isViewCreated = false;
    //数据是否加载完成
    private boolean isLoadDataCompleted;
    private PermissionResultListener mListener;
    public Activity mActivity;
    View mRootView;
    private int count;//记录开启进度条的情况 只能开一个
    //当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
    private boolean isFragmentVisible;
    //是否是第一次开启网络加载
    public boolean isFirst;

    private int mRequestCode;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = inflater.inflate(setFragmentLayoutID(), container, false);
            initView(mRootView);
            initListener();
            isViewCreated = true;
        }
        return mRootView;
    }

    public abstract int setFragmentLayoutID();

    protected abstract void initView(View view);


    /**
     * [View点击]
     */
    public abstract void initClick(View view);

    protected abstract void initListener();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            lazyLoadData();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        initClick(view);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRootView != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isViewCreated && !isLoadDataCompleted) {
            lazyLoadData();
        } else {
            isLoadDataCompleted = false;
            isViewCreated = false;
        }

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    /**
     * 懒加载
     */
    public void lazyLoadData() {
        isLoadDataCompleted = true;
    }

    /**
     * 线程
     *
     * @param r
     */
    public void runOnUIThread(Runnable r) {
        final Activity activity = getActivity();
        if (activity != null && r != null)
            activity.runOnUiThread(r);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 开启加载进度条
     */
    public void startProgressDialog() {
        count++;
        if (count == 1) {
            LoadingDialog.showDialogForLoading(getActivity());
        }
    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(getActivity(), msg, true);

    }

    /**
     * 停止加载进度条
     */
    public void stopProgressDialog() {
        count--;
        if (count == 0) {
            LoadingDialog.cancelDialogForLoading();
        }

    }

    /**
     * 网络访问错误提醒
     */
    public void showNetErrorTip() {

    }

    public void showNetErrorTip(String error) {

    }

    public void showLoading() {

    }

    public void stopLoading() {

    }

    /**
     * 权限
     *
     * @param desc
     * @param permissions
     * @param requestCode
     * @param listener
     */
    protected void performRequestPermissions(String desc, String[] permissions, int requestCode,
                                             PermissionResultListener listener) {
        if (permissions == null || permissions.length == 0) return;
        mRequestCode = requestCode;
        mListener = listener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkEachSelfPermission(permissions)) {// 检查是否声明了权限
                requestEachPermissions(desc, permissions, requestCode);
            } else {// 已经申请权限
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            }
        } else {
            if (mListener != null) {
                mListener.onPermissionGranted();
            }
        }
    }

    private void requestEachPermissions(String desc, String[] permissions, int requestCode) {
        if (shouldShowRequestPermissionRationale(permissions)) {// 需要再次声明
            showRationaleDialog(desc, permissions, requestCode);
        } else {
            requestPermissions(permissions, requestCode);
        }
    }

    private void showRationaleDialog(String desc, final String[] permissions, final int requestCode) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.tips))
                .setMessage(desc)
                .setPositiveButton(getResources().getString(R.string.sure), new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestPermissions(permissions, requestCode);
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                .setCancelable(false)
                .show();
    }


    /**
     * 再次申请权限时，是否需要声明
     *
     * @param permissions
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检察每个权限是否申请
     *
     * @param permissions
     * @return true 需要申请权限,false 已申请权限
     */

    private boolean checkEachSelfPermission(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode) {
            if (checkEachPermissionsGranted(grantResults)) {
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            } else {// 用户拒绝申请权限
                if (mListener != null) {
                    mListener.onPermissionDenied();
                }
            }
        }
    }

    private boolean checkEachPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


}
