package com.jkb.junbin.sharing.feature.message;


import android.accounts.NetworkErrorException;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.jkb.junbin.sharing.feature.account.AccountController;
import com.jkb.junbin.sharing.feature.file.FileController;
import com.jkb.junbin.sharing.feature.message.db.DataBaseHelper;
import com.jkb.junbin.sharing.feature.message.Message;
import com.jkb.junbin.sharing.feature.file.FileInfo;
import com.jkb.junbin.sharing.library.log.LogUtils;
import com.jkb.junbin.sharing.library.net.NetUtil;

import java.util.ArrayList;
import java.util.List;

public class MessageController {

    FileController fileController = new FileController();

    Context mContext;


    public MessageController(Context context) {
        mContext = context;
    }

    public boolean post(Message message, FileInfo fileInfo) {
        //发送一条动态消息
        if (!AccountController.isLogin) {
            return false;
        }
        LogUtils.log(message.content);
        LogUtils.log(fileInfo.fileName);
        return true;

    }

    public List<Message> getMessageList() throws NetworkErrorException {
        NetUtil.mockNetExecutor();
        ArrayList<Message> messageList = new ArrayList<>();
        Message message = new Message(1, "张三共享文件到消息中...", fileController.download(1).fileName, 1615963675000L);

        messageList.add(message);
        messageList.add(new Message(2, "李四共享视频到消息中...", fileController.download(2).fileName, 1615963688000L));
        return messageList;
    }

    public List<Message> getMessageListFromCache() {
        List<Message> messageList = new ArrayList<>();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        Cursor c = dataBaseHelper.getWritableDatabase().query(DataBaseHelper.message_info, null, null, null, null, null, null);
        if (c.moveToFirst()) {//判断游标是否为空
            for (int i = 0; i < c.getCount(); i++) {
                c.move(i);//移动到指定记录
                int id = c.getInt(c.getColumnIndex(DataBaseHelper.id));
                String content = c.getString(c.getColumnIndex(DataBaseHelper.content));
                String fileName = c.getString(c.getColumnIndex(DataBaseHelper.fileName));
                long date = c.getLong(c.getColumnIndex(DataBaseHelper.date));
                messageList.add(new Message(id, content, fileName, date));
            }
        }
        return messageList;
    }

    public void saveMessageToCache(List<Message> messageList) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        if (messageList != null && messageList.size() > 0) {
            dataBaseHelper.getWritableDatabase().delete(DataBaseHelper.message_info, null, null);
            for (Message message : messageList) {
                ContentValues cv = new ContentValues();
                cv.put(DataBaseHelper.id, message.id);
                cv.put(DataBaseHelper.content, message.content);
                cv.put(DataBaseHelper.date, message.date);
                cv.put(DataBaseHelper.fileName, message.fileName);
                dataBaseHelper.getWritableDatabase().insert(DataBaseHelper.message_info, null, cv);
            }
        }
    }
}
