package com.jkb.junbin.sharing.function.shell;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class SharingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
    }
}
