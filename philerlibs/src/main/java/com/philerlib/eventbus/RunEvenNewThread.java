package com.philerlib.eventbus;

import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by philer on 2019/8/5.
 * Describe:子线程运行某一个方法
 */
public class RunEvenNewThread implements RunEventInterface {
    ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void run(Runnable runnable) {
        executorService.execute(runnable);
    }
}
