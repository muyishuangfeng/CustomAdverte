package com.yk.adverte.widget.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yk.adverte.R;
import com.yk.adverte.widget.fragment.DayFragment;
import com.yk.adverte.widget.fragment.MonthFragment;
import com.yk.adverte.widget.fragment.YearFragment;


/**
 * Created by Silence on 2017/11/6.
 */

public class HomeAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] mTitles;

    public HomeAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.mTitles = context.getResources().getStringArray(R.array.charge_list);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new MonthFragment();
        }else if (position==2){
            return new YearFragment();
        }
        return new DayFragment();
    }

    @Override
    public int getCount() {
         return mTitles.length;
    }
    /**
     * ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
