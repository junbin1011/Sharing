package com.jkb.junbin.sharing.shadow;

import com.jkb.junbin.sharing.feature.file.FileRemoteDataSource;
import com.jkb.junbin.sharing.function.transfer.FileInfo;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Implements(FileRemoteDataSource.class)
public class ShadowFileRemoteDataSource {

    @RealObject
    public FileRemoteDataSource fileRemoteDataSource;


    @Implementation
    public Flowable<List<FileInfo>> getFileList() {
        ArrayList<FileInfo> infoList = new ArrayList<>();
        infoList.add(new FileInfo("Android遗留系统重构.pdf", 102400));
        infoList.add(new FileInfo("研发那些事第一季.mp4", 9900));
        return Flowable.fromArray(infoList);
    }
}
