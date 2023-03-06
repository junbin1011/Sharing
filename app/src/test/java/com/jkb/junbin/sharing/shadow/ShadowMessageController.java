package com.jkb.junbin.sharing.shadow;

import android.accounts.NetworkErrorException;

import com.jkb.junbin.sharing.feature.message.Message;
import com.jkb.junbin.sharing.feature.message.MessageController;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

import java.util.ArrayList;
import java.util.List;

@Implements(MessageController.class)
public class ShadowMessageController {

    @RealObject
    public MessageController messageController;


    @Implementation
    public List<Message> getMessageList() throws NetworkErrorException {
        ArrayList<Message> messageList = new ArrayList<>();
        Message message = new Message(1, "张三共享文件到消息中...", "1.pdf", 1615963675000L);

        messageList.add(message);
        messageList.add(new Message(2, "李四共享视频到消息中...", "2.pdf", 1615963688000L));
        return messageList;
    }
}
