package com.jkb.junbin.sharing.library.log;

import android.util.Log;


public class LogUtils {
    public static void log(String log, String username) {
        //打印系统日志
        String tag = LogUtils.class.getSimpleName();
        if (username != null) {
            tag += "-" + username;
        }
        Log.d(tag, log);
    }
}
