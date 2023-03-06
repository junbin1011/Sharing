package com.jkb.junbin.sharing.shadow;

import com.jkb.junbin.sharing.feature.file.FileController;
import com.jkb.junbin.sharing.function.transfer.FileInfo;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

import java.util.ArrayList;
import java.util.List;

@Implements(FileController.class)
public class ShadowFileController {

    @RealObject
    public FileController fileController;


    @Implementation
    public List<FileInfo> getFileList() {
        ArrayList<FileInfo> infoList = new ArrayList<>();
        infoList.add(new FileInfo("Android遗留系统重构.pdf", 102400));
        infoList.add(new FileInfo("研发那些事第一季.mp4", 9900));
        return infoList;
    }
}
