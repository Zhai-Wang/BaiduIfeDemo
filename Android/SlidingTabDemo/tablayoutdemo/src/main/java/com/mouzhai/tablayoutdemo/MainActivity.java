package com.mouzhai.tablayoutdemo;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mouzhai.tablayoutdemo.adapter.MyPagerAdapter;

public class MainActivity extends AppCompatActivity {

    public static final int TAB_COUNT = 10;  // tab 选项数目
    public static final int TAB_1 = 0;       // 代表 tab 中各 item 的位置
    public static final int TAB_2 = 1;
    public static final int TAB_3 = 2;
    public static final int TAB_4 = 3;
    public static final int TAB_5 = 4;
    public static final int TAB_6 = 5;
    public static final int TAB_7 = 6;
    public static final int TAB_8 = 7;
    public static final int TAB_9 = 8;
    public static final int TAB_10 = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        //  初始化界面
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }
}
