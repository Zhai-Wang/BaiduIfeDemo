package com.mouzhai.tablayoutdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mouzhai.tablayoutdemo.R;

/**
 * 自定义 Fragment，由于 Demo 中布局类似，使用一个通用的即可
 * <p>
 * Created by Mouzhai on 2017/4/14.
 */

public class MyFragment extends Fragment {

    public static final String STATUS = "status";
    public static final String STATUS_FIRST = "first fragment";
    public static final String STATUS_SECOND = "second fragment";
    public static final String STATUS_THIRD = "third fragment";
    public static final String STATUS_FORTH = "forth fragment";
    public static final String STATUS_FIFTH = "fifth fragment";
    public static final String STATUS_SIXTH = "sixth fragment";
    public static final String STATUS_SEVENTH = "seventh fragment";
    public static final String STATUS_EIGHTH = "eighth fragment";
    public static final String STATUS_NINTH = "ninth fragment";
    public static final String STATUS_TENTH = "tenth fragment";

    private String fragmentText;

    public static MyFragment getInstance(Bundle args) {
        MyFragment myFragment = new MyFragment();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentText = getArguments().getString(STATUS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment, container, false);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvContent.setText(fragmentText);    // 根据选项的不同，显示不同的文字
        return view;
    }
}
