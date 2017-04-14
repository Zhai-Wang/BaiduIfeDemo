package com.mouzhai.slidingtabdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mouzhai.slidingtabdemo.adapter.MyFragmentAdapter;
import com.mouzhai.slidingtabdemo.fragment.FirstFragment;
import com.mouzhai.slidingtabdemo.fragment.FirthFragment;
import com.mouzhai.slidingtabdemo.fragment.SecondFragment;
import com.mouzhai.slidingtabdemo.fragment.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager viewPager;
    private TextView tvFirst;
    private TextView tvSecond;
    private TextView tvThird;
    private TextView tvFirth;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化控件，设置其点击事件，以及设置 FragmentPagerAdapter
     */
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tvFirst = (TextView) findViewById(R.id.tv_first);
        tvSecond = (TextView) findViewById(R.id.tv_second);
        tvThird = (TextView) findViewById(R.id.tv_third);
        tvFirth = (TextView) findViewById(R.id.tv_firth);

        viewPager.addOnPageChangeListener(this);
        tvFirst.setOnClickListener(this);
        tvSecond.setOnClickListener(this);
        tvThird.setOnClickListener(this);
        tvFirth.setOnClickListener(this);

        FirstFragment firstFragment = new FirstFragment();
        SecondFragment secondFragment = new SecondFragment();
        ThirdFragment thirdFragment = new ThirdFragment();
        FirthFragment firthFragment = new FirthFragment();

        fragments.add(firstFragment);
        fragments.add(secondFragment);
        fragments.add(thirdFragment);
        fragments.add(firthFragment);

        MyFragmentAdapter myFragmentAdapter =
                new MyFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myFragmentAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 点击 tab 选项，调转到对应的页面
            case R.id.tv_first:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_second:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_third:
                viewPager.setCurrentItem(2);
                break;
            case R.id.tv_firth:
                viewPager.setCurrentItem(3);
                break;
        }
    }

    // 这里重写的三个方法可以用来监听 ViewPager 的滑动事件
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 当页面改变之后，改变对应的 tab 字样颜色
        switch (position) {
            case 0:
                tvFirst.setTextColor(Color.rgb(0, 0, 0));
                tvSecond.setTextColor(Color.rgb(255, 255, 255));
                tvThird.setTextColor(Color.rgb(255, 255, 255));
                tvFirth.setTextColor(Color.rgb(255, 255, 255));
                break;
            case 1:
                tvFirst.setTextColor(Color.rgb(255, 255, 255));
                tvSecond.setTextColor(Color.rgb(0, 0, 0));
                tvThird.setTextColor(Color.rgb(255, 255, 255));
                tvFirth.setTextColor(Color.rgb(255, 255, 255));
                break;
            case 2:
                tvFirst.setTextColor(Color.rgb(255, 255, 255));
                tvSecond.setTextColor(Color.rgb(255, 255, 255));
                tvThird.setTextColor(Color.rgb(0, 0, 0));
                tvFirth.setTextColor(Color.rgb(255, 255, 255));
                break;
            case 3:
                tvFirst.setTextColor(Color.rgb(255, 255, 255));
                tvSecond.setTextColor(Color.rgb(255, 255, 255));
                tvThird.setTextColor(Color.rgb(255, 255, 255));
                tvFirth.setTextColor(Color.rgb(0, 0, 0));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
