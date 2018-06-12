package com.yk.adverte.widget.activity;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.yk.adverte.R;
import com.yk.adverte.base.BaseBottomActivity;
import com.yk.adverte.util.StatusBarUtils;
import com.yk.adverte.util.ToastUtils;
import com.yk.adverte.view.BottomTabView;
import com.yk.adverte.widget.fragment.CarFragment;
import com.yk.adverte.widget.fragment.HomeFragment;
import com.yk.adverte.widget.fragment.MyselfFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2018/5/16.
 */

public class MainActivity extends BaseBottomActivity {

    //最后返回时间
    private long lastBackTime = 0;
    //时间间隔
    private static final long TIME_INTERVAL = 2 * 1000;

    @Override
    protected List<BottomTabView.TabItemView> getTabViews() {
        List<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
        tabItemViews.add(new BottomTabView.TabItemView(this,
                getResources().getString(R.string.nagv_home), R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        tabItemViews.add(new BottomTabView.TabItemView(this,
                getResources().getString(R.string.nagv_car), R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        tabItemViews.add(new BottomTabView.TabItemView(this,
                getResources().getString(R.string.nagv_myself), R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        return tabItemViews;
    }

    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CarFragment());
        fragments.add(new MyselfFragment());
        return fragments;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this instanceof MainActivity) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                long currentBackTime = System.currentTimeMillis();
                if (currentBackTime - lastBackTime > TIME_INTERVAL) {
                    ToastUtils.showMessage(this,
                            getResources().getString(R.string.press_back));
                    lastBackTime = currentBackTime;
                } else {
                    finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
