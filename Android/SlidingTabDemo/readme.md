# 实现一个TAB布局

## 概述

实现常见的标签tab，并且点击tab跳转至对应页面
实现页面滑动效果

## 实现方式

<img src="https://github.com/Zhai-Wang/BaiduIfeDemo/blob/master/Android/SlidingTabDemo/screenshots/1.gif"/>

要实现滑动选项卡，使用 Fragment 与 ViewPager 即可。
我们需要完成的功能，无非是：点击 tab 选项，跳转到对应界面；滑动页面，tab 选项做出响应。
点击事件可以为 tab 中的控件设置，而 ViewPager 可以实现滑动事件的监听。
Fragment 需要自定义 FragmentPagerAdapter，不过这里并没有什么复杂的逻辑，略去不表。

    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:background="#3F51B5"
        android:gravity="center"
        android:orientation="horizontal">

        <TextView
            android:id="@+id/tv_first"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:gravity="center"
            android:text="@string/first"
            android:textColor="#000000"
            android:textSize="20sp" />

        <TextView
            android:id="@+id/tv_second"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:gravity="center"
            android:text="@string/second"
            android:textColor="#ffffff"
            android:textSize="20sp" />

        <TextView
            android:id="@+id/tv_third"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:gravity="center"
            android:text="@string/third"
            android:textColor="#ffffff"
            android:textSize="20sp" />

        <TextView
            android:id="@+id/tv_firth"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:gravity="center"
            android:text="@string/firth"
            android:textColor="#ffffff"
            android:textSize="20sp" />
    </LinearLayout>

如上，为自定义的 tab 布局，其中简单地设置了四个 TextView。为它们设置点击事件，实现点击后调转页面：

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
    
对于 ViewPager，引入 ViewPager.OnPageChangeListener 后，通过重写以下方法，达到改变 tab 选项颜色的目的：

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
                tvForth.setTextColor(Color.rgb(255, 255, 255));
                break;
            case 1:
                tvFirst.setTextColor(Color.rgb(255, 255, 255));
                tvSecond.setTextColor(Color.rgb(0, 0, 0));
                tvThird.setTextColor(Color.rgb(255, 255, 255));
                tvForth.setTextColor(Color.rgb(255, 255, 255));
                break;
            case 2:
                tvFirst.setTextColor(Color.rgb(255, 255, 255));
                tvSecond.setTextColor(Color.rgb(255, 255, 255));
                tvThird.setTextColor(Color.rgb(0, 0, 0));
                tvForth.setTextColor(Color.rgb(255, 255, 255));
                break;
            case 3:
                tvFirst.setTextColor(Color.rgb(255, 255, 255));
                tvSecond.setTextColor(Color.rgb(255, 255, 255));
                tvThird.setTextColor(Color.rgb(255, 255, 255));
                tvForth.setTextColor(Color.rgb(0, 0, 0));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    
这样，一个最简易的 Demo 就完成了。
当然了，这里只实现了基本的功能，实际要求可能比较复杂，需要使用下划线来提高辨识度、tab 的形式不是文字而是图片等等，但是思路是一样的，都是在这几个方法上做文章。
此外，有可能会出现 tab 选项过多，依靠滑动才能展现完全的情况，可以通过使用一些开源库来轻松地解决，例如 [TabLayoutDemo](https://github.com/Zhai-Wang/BaiduIfeDemo/tree/master/Android/SlidingTabDemo/tablayoutdemo/src/main)，参见[使用TabLayout制作下划线能滑动的Tab标签页](http://blog.csdn.net/zhuwentao2150/article/details/52279133)。

## 参考资料

* [用ViewPager与FragmentPagerAdapter实现可以滑动的Tab](http://blog.csdn.net/mouzhai/article/details/52304196)
