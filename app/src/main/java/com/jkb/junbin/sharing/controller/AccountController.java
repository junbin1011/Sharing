package com.jkb.junbin.sharing.controller;


import android.accounts.NetworkErrorException;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.VisibleForTesting;

import com.jkb.junbin.sharing.callback.CallBack;
import com.jkb.junbin.sharing.model.AccountInfo;
import com.jkb.junbin.sharing.util.LogUtils;
import com.jkb.junbin.sharing.util.NetUtil;
import com.jkb.junbin.sharing.util.SharedPreferencesUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AccountController {
    public static boolean isLogin = false;
    public static AccountInfo currentAccountInfo;


    public AccountInfo getCurrentUserInfo() {
        //获取用户信息
        return currentAccountInfo;
    }

    public void login(Context context, String username, String password, CallBack callBack) {
        //用户登录
        LogUtils.log("login...");
        try {
            //验证账号及密码
            if (!isUserNameValid(username) || !isPasswordValid(password)) {
                callBack.filed("账户或密码格式错误。");
                return;
            }
            NetUtil.mockRandomException();
            //通过服务器判断账户及密码的有效性
            boolean result = checkFromServer(username, password);
            if (result) {
                AccountController.isLogin = true;
                currentAccountInfo = new AccountInfo();
                currentAccountInfo.username = username;
                //登录成功保持本地的信息
                SharedPreferencesUtils.put(context, username, password);
                callBack.success("success");
            } else {
                callBack.filed("login failed");
            }

        } catch (NetworkErrorException networkErrorException) {
            callBack.filed("网络异常，请重试");
        }

    }

    // 为了进行演示，去除通过服务器鉴定的逻辑，当用户输入账号及密码为123时则验证成功
    private static boolean checkFromServer(String username, String password) {
        if (username.equals("123@163.com") && password.equals("123456")) {
            return true;
        }
        return false;
    }

    @VisibleForTesting
    protected boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = pattern.matcher(username);
        if (username.contains("@")) {
            return matcher.matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    @VisibleForTesting
    protected boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
