package com.example.testsome.designmode.mvc;

import android.util.Log;

/**
 * Created by philer on 2019/7/9.
 * Describe:
 */
public class Model {

    String name;
    int age;

    public void requestData(RequestCall requestCall) {
        long l = System.currentTimeMillis();
        Log.i("philer","currentTime : "+l+" : "+(l % 3));
        if (l % 3 == 1) {
            requestCall.requestFail();
        } else {
            name = System.currentTimeMillis() + "";
            age = (int) (System.currentTimeMillis() % 100);
            requestCall.requestOK(this);
        }
    }
}
