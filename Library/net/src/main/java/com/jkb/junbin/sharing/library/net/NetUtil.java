package com.jkb.junbin.sharing.library.net;

import android.accounts.NetworkErrorException;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;

import java.util.Random;

public class NetUtil {
    public static void mockNetExecutor() throws NetworkErrorException {
        //模拟网络方法 不能在主线程调用
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            throw new NetworkOnMainThreadException();
        }
        //模拟网络延时
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //模拟随机网络异常
        mockRandomException();
    }

    public static void mockRandomException() throws NetworkErrorException {
        //模拟随机网络异常
        Random random = new Random();
        int num = random.nextInt(100);
        if (num % 3 == 0) {
            throw new NetworkErrorException();
        }
    }
}
