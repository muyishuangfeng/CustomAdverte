package com.yk.adverte.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yk.adverte.R;
import com.yk.adverte.util.StatusBarUtils;
import com.yk.adverte.view.BottomTabView;
import com.yk.adverte.widget.activity.MainActivity;

import java.util.List;

/**
 * Created by Silence on 2017/8/15.
 */

public abstract class BaseBottomActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomTabView bottomTabView;
    FragmentPagerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base_bottom_tab);
        initSystemBarTint(false);
        viewPager = (ViewPager) findViewById(R.id.vpg_title);
        bottomTabView = (BottomTabView) findViewById(R.id.bottomTabView);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getFragments().get(position);
            }

            @Override
            public int getCount() {
                return getFragments().size();
            }
        };

        viewPager.setAdapter(adapter);

        if (getCenterView() == null) {
            bottomTabView.setTabItemViews(getTabViews());
        } else {
            bottomTabView.setTabItemViews(getTabViews(), getCenterView());
        }

        bottomTabView.setUpWithViewPager(viewPager);

    }


    protected abstract List<BottomTabView.TabItemView> getTabViews();

    protected abstract List<Fragment> getFragments();

    protected View getCenterView() {
        return null;
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
}
