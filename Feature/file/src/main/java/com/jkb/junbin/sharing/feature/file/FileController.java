package com.jkb.junbin.sharing.feature.file;


import android.accounts.NetworkErrorException;


import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.jkb.junbin.sharing.function.shell.interfaces.IAccountState;
import com.jkb.junbin.sharing.function.transfer.FileInfo;
import com.jkb.junbin.sharing.function.transfer.FileTransfer;
import com.jkb.junbin.sharing.library.net.NetUtil;

import java.util.ArrayList;

import java.util.List;


public class FileController {

    //模拟获取文件
    public List<FileInfo> getFileList() throws NetworkErrorException {
        NetUtil.mockNetExecutor();
        ArrayList<FileInfo> infoList = new ArrayList<>();
        infoList.add(new FileInfo("Android遗留系统重构.pdf", 102400));
        infoList.add(new FileInfo("研发那些事第一季.mp4", 9900));
        return infoList;
    }

}
