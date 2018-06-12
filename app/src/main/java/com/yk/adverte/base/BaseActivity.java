package com.yk.adverte.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yk.adverte.R;
import com.yk.adverte.common.AppStatusConstant;
import com.yk.adverte.common.AppStatusManager;
import com.yk.adverte.impl.PermissionResultListener;
import com.yk.adverte.util.InputMethodUtils;
import com.yk.adverte.widget.activity.MainActivity;
import com.yk.adverte.widget.activity.SplashActivity;


/**
 * Created by Silence on 2017/8/15.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    //静态变量，在 PicassoTarget 中修改 status 颜色时也动态修改
    public static int currentStatusColor;
    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = true;
    /**
     * 是否禁止旋转屏幕
     **/
    private boolean isAllowScreenRoate = false;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();
    //运行时权限接口
    private PermissionResultListener mListener;
    //请求码
    private int mRequestCode;
    protected ProgressDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView();
        //设置方向（纵向）
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        initSystemBarTint(false);
        initView();
        setListener();
        initData();
        initView(savedInstanceState);
    }

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint(boolean isSystemBarTranslucent) {
        if (isSystemBarTranslucent) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                SystemBarTintManager tintManager = new SystemBarTintManager(this);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintColor(getDarkColorPrimary());
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getColorPrimary());
        }
    }

    /**
     * 获取主题色
     *
     * @return
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }



    /**
     * 获取深主题色
     *
     * @return
     */
    public int getDarkColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.color.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract void setContentView();

    /**
     * [初始化控件]
     */
    public abstract void initView();

    /**
     * [初始化数据]
     */
    public abstract void initData();


    /**
     * [View点击]
     */
    public abstract void initClick(View view);

    /**
     * [设置监听]
     */
    public abstract void setListener();

    public void initView(Bundle savedInstanceState){

    }

    /**
     * [绑定控件]
     *
     * @param resId
     * @return
     */
    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    @Override
    public void onClick(View view) {
        initClick(view);
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }


    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    // 初始化传入的参数
    protected abstract void initIntentParam(Intent intent);


    protected void setToolbarStyle() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodUtils.hiddenInputMethod(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     * 其他 Activity 继承 BaseActivity 调用 performRequestPermissions 方法
     *
     * @param desc        首次申请权限被拒绝后再次申请给用户的描述提示
     * @param permissions 要申请的权限数组
     * @param requestCode 申请标记值
     * @param listener    实现的接口
     */
    protected void performRequestPermissions(String desc, String[] permissions,
                                             int requestCode,
                                             PermissionResultListener listener) {
        if (permissions == null || permissions.length == 0) {
            return;
        }
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

    /**
     * 申请权限前判断是否需要声明
     *
     * @param desc
     * @param permissions
     * @param requestCode
     */
    private void requestEachPermissions(String desc, String[] permissions, int requestCode) {
        if (shouldShowRequestPermissionRationale(permissions)) {// 需要再次声明
            showRationaleDialog(desc, permissions, requestCode);
        } else {
            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
        }
    }

    /**
     * 弹出声明的 Dialog
     *
     * @param desc
     * @param permissions
     * @param requestCode
     */
    private void showRationaleDialog(String desc, final String[] permissions,
                                     final int requestCode) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.tips))
                .setMessage(desc)
                .setPositiveButton(getResources().getString(R.string.sure),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(BaseActivity.this,
                                        permissions, requestCode);
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
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
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
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 申请权限结果的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
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

    /**
     * 检查回调结果
     *
     * @param grantResults
     * @return
     */
    private boolean checkEachPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(CharSequence title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.know, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(int title, int message) {
        showMessageDialog(getText(title), getText(message));
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(int title, CharSequence message) {
        showMessageDialog(getText(title), message);
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(CharSequence title, int message) {
        showMessageDialog(title, getText(message));
    }

    /**
     * 显示菊花
     */
    public void showProressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.show();
    }

    /**
     * 显示带信息的菊花
     */
    public void showProressDialog(String msg) {

        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.setMessage(msg);
        dialog.show();
    }

    /**
     * 隐藏菊花
     */
    public void hideProgressDialog() {
        if (dialog != null) {
            dialog.hide();
        }
    }


}
