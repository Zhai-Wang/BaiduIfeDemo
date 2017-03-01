package com.mouzhai.oomdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class TestActivity extends AppCompatActivity {

    private ImageView ivOomAnim;

    @SuppressLint("StaticFieldLeak")
    static Activity staticActivity;

    @SuppressLint("StaticFieldLeak")
    static View staticView;

    InnerClass inner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initData();

        ivOomAnim = (ImageView) findViewById(R.id.iv_oom_anim);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //播放动画，这里只是实践一下刚学的帧动画，并不会引起 OOM
        //但是如果这里加载的是上百张图片，或是图片过大的话，就会造成卡顿甚至 OOM，本质上还是 Bitmap 过大引起的
        //如果一定要加载大量大图，就要想办法处理 Bitmap，例如图片压缩、手动回收释放资源、缓存、使用其他方式播放动画等等
        ivOomAnim.setImageResource(R.drawable.oom_animlist);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivOomAnim.getDrawable();
        animationDrawable.start();
    }

    private void initData() {
        //一个静态的 Activity 可能会引起内存泄漏，如果此 Activity 结束之前没有清除此引用，这个对象就一直不会被回收
        staticActivity = this;

        //静态的 View 会持有 Context 的强引用，可能引起内存泄漏
        staticView = findViewById(R.id.tv_test);

        //非静态内部类和匿名类都会持有 Activity 的引用，会引起内存泄漏
        createInnerClass();

        //当 Handler 中有延迟的的任务或是等待执行的任务队列过长，由于消息持有对 Handler 的引用，
        //而 Handler 又持有对其外部类的潜在引用，会导致 Activity 无法被垃圾回收器回收，造成内存泄露。
        createHandler();

        //同样的，使用 Thread 和 TimerTask 也可能导致内存泄漏
        //只要它们是通过匿名类创建的，尽管它们在单独的线程被执行，它们也会持有对 Activity 的强引用
        createThread();

        //系统服务可以通过 Context.getSystemService 获取，它们负责执行某些后台任务，或者为硬件访问提供接口，
        //如果 Context 对象想要在服务内部的事件发生时被通知，那就需要把自己注册到服务的监听器中，
        //然而，这会让服务持有 Activity 的引用，如果在 Activity onDestroy 时没有释放掉引用就会内存泄漏
        registerListener();

        // Cursor、Stream 没有 close，View 没有 recycle，集合中的对象没有清理，都可能会造成 OOM

        //单例也会引起 OOM
        SingletonDemo.getInstance(this);
    }

    void createInnerClass() {
        inner = new InnerClass();
    }

    void createHandler() {
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        }.postDelayed(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, Long.MAX_VALUE >> 1);
    }

    void createThread() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, Long.MAX_VALUE >> 1);
    }

    void registerListener() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ALL);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {}

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {}
        }, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    class InnerClass extends Thread {
        @Override
        public void run() {
            //由于这里的死循环，此内部类一直不会被回收，它持有的外部 Activity 的引用也就不会被释放
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
