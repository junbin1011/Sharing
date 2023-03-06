package com.jkb.junbin.sharing.feature.file;


import android.accounts.NetworkErrorException;


import com.jkb.junbin.sharing.function.shell.interfaces.IAccountState;
import com.jkb.junbin.sharing.function.transfer.FileInfo;
import com.jkb.junbin.sharing.function.transfer.FileTransfer;
import com.jkb.junbin.sharing.library.net.NetUtil;

import java.util.ArrayList;

import java.util.List;


public class FileController {

    private IAccountState iAccountState;
    {
        try {
            iAccountState = (IAccountState) Class.forName("com.jkb.junbin.sharing.feature.account.AccountStateImpl").newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public FileTransfer fileTransfer = new FileTransfer(iAccountState.isLogin());

    //模拟获取文件
    public List<FileInfo> getFileList() throws NetworkErrorException {
        NetUtil.mockNetExecutor();
        ArrayList<FileInfo> infoList = new ArrayList<>();
        infoList.add(new FileInfo("Android遗留系统重构.pdf", 102400));
        infoList.add(new FileInfo("研发那些事第一季.mp4", 9900));
        return infoList;
    }

}
