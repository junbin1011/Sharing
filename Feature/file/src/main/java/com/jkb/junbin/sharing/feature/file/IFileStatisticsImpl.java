package com.jkb.junbin.sharing.feature.file;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jkb.junbin.sharing.function.shell.interfaces.IAccountState;
import com.jkb.junbin.sharing.function.shell.interfaces.IFileStatistics;

import java.util.Random;

@Route(path = "/fileFeature/IFileStatistics", name = "IFileStatistics", priority = 200)
public class IFileStatisticsImpl implements IFileStatistics {
    @Override
    public void init(Context context) {

    }

    @Override
    public int getDownloadCount(String id) {
        return new Random().nextInt(100);
    }
}
