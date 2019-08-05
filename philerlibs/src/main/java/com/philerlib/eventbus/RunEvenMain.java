package com.philerlib.eventbus;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by philer on 2019/8/5.
 * Describe:主线程运行某一个方法
 */
public class RunEvenMain implements RunEventInterface {
    android.os.Handler handler;

    public RunEvenMain() {
        Looper mainLooper = Looper.getMainLooper();
        handler = new Handler(mainLooper);
    }

    @Override
    public void run(Runnable runnable) {
        handler.post(runnable);
    }
}
