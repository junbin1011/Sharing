package com.jkb.junbin.sharing.function.transfer;

import com.jkb.junbin.sharing.library.log.LogUtils;
import java.util.Random;

public class FileTransfer {

    private boolean isLogin;

    public FileTransfer(boolean isLogin) {
        this.isLogin = isLogin;
    }

    // 模拟上传
    public FileInfo upload(String username, String path) {
        if (!isLogin) {
            return null;
        }
        //上传文件
        LogUtils.log(username + " upload file" + path, username);
        FileInfo fileInfo = new FileInfo();
        fileInfo.fileName = new Random().nextInt(1000) + ".pdf";
        fileInfo.fileSize = new Random().nextInt(20480);
        return fileInfo;
    }

    //模拟下载
    public FileInfo download(int id) {
        //下载文件
        if (!isLogin) {
            return null;
        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.fileName = id + ".pdf";
        fileInfo.fileSize = new Random().nextInt(20480);
        return fileInfo;
    }
}
