package com.jkb.junbin.sharing.feature.file;


import android.accounts.NetworkErrorException;


import com.jkb.junbin.sharing.feature.account.AccountController;
import com.jkb.junbin.sharing.feature.file.FileInfo;
import com.jkb.junbin.sharing.library.log.LogUtils;
import com.jkb.junbin.sharing.library.net.NetUtil;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;


public class FileController {

    // 模拟上传
    public FileInfo upload(String path) {
        if (!AccountController.isLogin) {
            return null;
        }
        //上传文件
        LogUtils.log(AccountController.currentAccountInfo.username+" upload file" + path);
        FileInfo fileInfo = new FileInfo();
        fileInfo.fileName = new Random().nextInt(1000)+".pdf";
        fileInfo.fileSize = new Random().nextInt(20480);
        return fileInfo;
    }

    //模拟下载
    public FileInfo download(int id) {
        //下载文件
        if (!AccountController.isLogin) {
            return null;
        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.fileName = id+".pdf";
        fileInfo.fileSize = new Random().nextInt(20480);
        return fileInfo;
    }

    //模拟获取文件
    public List<FileInfo> getFileList() throws NetworkErrorException {
        NetUtil.mockNetExecutor();
        ArrayList<FileInfo> infoList = new ArrayList<>();
        infoList.add(new FileInfo("Android遗留系统重构.pdf", 102400));
        infoList.add(new FileInfo("研发那些事第一季.mp4", 9900));
        return infoList;
    }

}
