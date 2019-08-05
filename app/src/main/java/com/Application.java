package com;

/**
 * Created by philer on 2019/7/22.
 * Describe:
 */
public class Application extends android.app.Application {

    private static Application applicationBase;

    public static Application getApplication() {
        return applicationBase;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        applicationBase = this;
    }
}
