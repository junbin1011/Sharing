package com.jkb.junbin.sharing.util;

import android.util.Log;

import com.jkb.junbin.sharing.controller.AccountController;


public class LogUtils {
    public static void log(String log) {
        //打印系统日志
        String tag = LogUtils.class.getSimpleName();
        if (AccountController.currentAccountInfo != null) {
            tag += "-" + AccountController.currentAccountInfo.username;
        }
        Log.d(tag, log);
    }
}
