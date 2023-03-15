package com.jkb.junbin.sharing.feature.file;

import android.accounts.NetworkErrorException;

import com.jkb.junbin.sharing.function.transfer.FileInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;


public interface FileDataSource {
    Flowable<List<FileInfo>> getFileList() throws NetworkErrorException;
}
