package com.jkb.junbin.sharing.feature.account;

import com.jkb.junbin.sharing.function.shell.interfaces.IAccountState;

public class AccountStateImpl implements IAccountState {
    @Override
    public boolean isLogin() {
        return AccountController.isLogin;
    }

    @Override
    public String getUsername() {
        return AccountController.currentAccountInfo.username;
    }
}
