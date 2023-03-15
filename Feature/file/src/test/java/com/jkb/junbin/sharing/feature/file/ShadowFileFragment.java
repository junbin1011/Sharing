package com.jkb.junbin.sharing.feature.file;

import com.jkb.junbin.sharing.function.transfer.FileInfo;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

import java.util.ArrayList;

@Implements(FilePresenterImpl.class)
public class ShadowFileFragment {

    @RealObject
    public FilePresenterImpl filePresenterImpl;
    private FileListContract.FileView mFileView;

    enum State {
        SUCCESS,
        ERROR,
        EMPTY
    }

    public static State state = State.SUCCESS;

    @Implementation
    public void getFileList() {
        mFileView = filePresenterImpl.mFileView;
        if (state == State.SUCCESS) {
            ArrayList<FileInfo> infoList = new ArrayList<>();
            infoList.add(new FileInfo("遗留代码重构.pdf", 102400));
            infoList.add(new FileInfo("系统组件化.pdf", 9900));
            mFileView.showFileList(infoList);
        } else if (state == State.ERROR) {
            mFileView.showNetWorkException("NetworkErrorException");
        } else if (state == State.EMPTY) {
            mFileView.showEmptyData();
        }
    }
}
