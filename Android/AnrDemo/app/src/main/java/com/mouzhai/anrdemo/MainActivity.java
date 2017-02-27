package com.mouzhai.anrdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注册本地广播接收器
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localReceiver = new LocalReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.mouzhai.anrdemo.LOCAL_BROADCAST");
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    public void mainThreadAnr(View view) {
        //在主线程中进行耗时操作会引起 ANR
        try {
            Thread.sleep(100 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void broadcastReceiverAnr(View view) {
        //点击按钮，发送本地广播
        Intent intent = new Intent("com.mouzhai.anrdemo.LOCAL_BROADCAST");
        localBroadcastManager.sendBroadcast(intent);
    }

    public void serviceAnr(View view) {
        //启动自定义 Service
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    /**
     * 自定义广播
     */
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //BroadcastReceiver 中也不能直接进行耗时操作，否则就会引起 ANR
            try {
                Thread.sleep(100 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
