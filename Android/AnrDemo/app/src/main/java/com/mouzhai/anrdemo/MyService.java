package com.mouzhai.anrdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 自定义服务
 * Service 虽然经常后台运行，但是实际上也处于主线程之中，因此直接进行耗时操作会引起 ANR
 */
public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service:   ", "start");
        try {
            Thread.sleep(100 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
