package com.jkb.junbin.sharing.feature.account;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jkb.junbin.sharing.function.shell.interfaces.IAccountState;

@Route(path = "/accountFeature/IAccountState", name = "IAccountState")
public class AccountStateImpl implements IAccountState {
    @Override
    public boolean isLogin() {
        return AccountController.isLogin;
    }

    @Override
    public String getUsername() {
        return AccountController.currentAccountInfo.username;
    }

    @Override
    public void init(Context context) {

    }
}
