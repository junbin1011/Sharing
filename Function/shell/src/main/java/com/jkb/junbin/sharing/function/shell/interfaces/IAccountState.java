package com.jkb.junbin.sharing.function.shell.interfaces;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IAccountState extends IProvider {
    boolean isLogin();

    String getUsername();
}
