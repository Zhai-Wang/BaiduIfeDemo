package com.mouzhai.oomdemo;

import android.content.Context;

/**
 * 用于演示单例引起的内存泄漏
 * 由于例中的 instance 持有了context，所以被传进来的 activity（或者 service 或 broadcast）没有办法释放，也就造成了内存泄露。
 * <p>
 * Created by Mouzhai on 2017/3/1.
 */

public class SingletonDemo {
    private static volatile SingletonDemo instance;
    private Context context;

    private SingletonDemo(Context context){
        this.context = context;
    }

    public static SingletonDemo getInstance(Context context){
        if (instance == null){
            synchronized (SingletonDemo.class){
                if (instance == null)
                    instance = new SingletonDemo(context);
            }
        }
        return instance;
    }
}
