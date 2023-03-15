package com.jkb.junbin.sharing.feature.file;

import android.accounts.NetworkErrorException;

import com.jkb.junbin.sharing.function.transfer.FileInfo;
import com.jkb.junbin.sharing.library.net.NetUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FileRemoteDataSource implements FileDataSource {

    @Override
    public Flowable<List<FileInfo>> getFileList() throws NetworkErrorException {
        //模拟获取文件
        NetUtil.mockNetExecutor();
        ArrayList<FileInfo> infoList = new ArrayList<>();
        infoList.add(new FileInfo("Android遗留系统重构.pdf", 102400));
        infoList.add(new FileInfo("研发那些事第一季.mp4", 9900));
        return Flowable.fromArray(infoList);
    }
}
