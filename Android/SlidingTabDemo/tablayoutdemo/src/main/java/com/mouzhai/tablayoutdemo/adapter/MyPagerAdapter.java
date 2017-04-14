package com.mouzhai.tablayoutdemo.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mouzhai.tablayoutdemo.MainActivity;
import com.mouzhai.tablayoutdemo.fragment.MyFragment;

/**
 * 自定义 PagerAdapter
 * <p>
 * Created by Mouzhai on 2017/4/14.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    // tab 标题
    private static final String[] TAB_TITLES = new String[]{"tab1", "tab2", "tab3", "tab4", "tab5",
            "tab6", "tab7", "tab8", "tab9", "tab10"};

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        boolean flag = true;
        switch (position) {
            case MainActivity.TAB_1:
                args.putString(MyFragment.STATUS, MyFragment.STATUS_FIRST);
                break;
            case MainActivity.TAB_2:
                args.putString(MyFragment.STATUS, MyFragment.STATUS_SECOND);
                break;
            case MainActivity.TAB_3:
                args.putString(MyFragment.STATUS, MyFragment.STATUS_THIRD);
                break;
            case MainActivity.TAB_4:
                args.putString(MyFragment.STATUS, MyFragment.STATUS_FORTH);
                break;
            case MainActivity.TAB_5:
                args.putString(MyFragment.STATUS, MyFragment.STATUS_FIFTH);
                break;
            case MainActivity.TAB_6:
                args.putString(MyFragment.STATUS, MyFragment.STATUS_SIXTH);
                break;
            case MainActivity.TAB_7:
                args.putString(MyFragment.STATUS, MyFragment.STATUS_SEVENTH);
                break;
            case MainActivity.TAB_8:
                args.putString(MyFragment.STATUS, MyFragment.STATUS_EIGHTH);
                break;
            case MainActivity.TAB_9:
                args.putString(MyFragment.STATUS, MyFragment.STATUS_NINTH);
                break;
            case MainActivity.TAB_10:
                args.putString(MyFragment.STATUS, MyFragment.STATUS_TENTH);
                break;
            default:
                flag = false;
                break;
        }
        if (flag)
            return MyFragment.getInstance(args);    // 返回对应的 Fragment 页面
        return null;
    }

    @Override
    public int getCount() {
        return MainActivity.TAB_COUNT;
    }

    // 设置 tab 标题
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
