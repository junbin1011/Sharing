package com.jkb.junbin.sharing.feature.file;

import android.accounts.NetworkErrorException;

import androidx.annotation.VisibleForTesting;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FilePresenterImpl implements FileListContract.FilePresenter {
    private FileDataSource mFileDataSource;
    @VisibleForTesting
    public FileListContract.FileView mFileView;

    private CompositeDisposable compositeDisposable;

    public FilePresenterImpl(FileDataSource fileDataSource, FileListContract.FileView fileView) {
        this.mFileDataSource = fileDataSource;
        this.mFileView = fileView;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    @VisibleForTesting
    public void getFileList() {
        try {
            compositeDisposable.add(mFileDataSource.getFileList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            fileList -> {
                                if (fileList == null || fileList.isEmpty()) {
                                    mFileView.showEmptyData();
                                } else {
                                    mFileView.showFileList(fileList);
                                }
                            }
                    ));
        } catch (NetworkErrorException e) {
            mFileView.showNetWorkException("NetworkErrorException");
        }
    }
}
