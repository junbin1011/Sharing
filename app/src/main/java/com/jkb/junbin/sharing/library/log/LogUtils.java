package com.jkb.junbin.sharing.library.log;

import android.util.Log;

import com.jkb.junbin.sharing.feature.account.AccountController;


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
