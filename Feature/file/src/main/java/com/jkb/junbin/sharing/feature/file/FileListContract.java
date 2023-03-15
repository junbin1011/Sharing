package com.jkb.junbin.sharing.feature.file;

public interface FileListContract {
    interface FileView {
        void showFileList(Object fileList);

        void showNetWorkException(String msg);

        void showEmptyData();
    }

    interface FilePresenter {
        void getFileList();
    }
}
