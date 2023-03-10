package com.jkb.junbin.sharing.function.shell.interfaces;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IFileStatistics extends IProvider {
    int getDownloadCount(String id);
}
