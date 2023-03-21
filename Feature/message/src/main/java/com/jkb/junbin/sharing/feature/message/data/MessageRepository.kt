package com.jkb.junbin.sharing.feature.message.data

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.jkb.junbin.sharing.feature.message.Message
import com.jkb.junbin.sharing.function.shell.interfaces.IAccountState
import com.jkb.junbin.sharing.function.transfer.FileInfo
import com.jkb.junbin.sharing.function.transfer.FileTransfer
import com.jkb.junbin.sharing.library.log.LogUtils
import com.jkb.junbin.sharing.library.net.NetUtil

class MessageRepository(mContext: Context?) {
    @JvmField
    @Autowired
    var iAccountState: IAccountState? = null

    var dataSource: LocalDataSource? = null;


    fun post(message: Message, fileInfo: FileInfo): Boolean {
        //发送一条动态消息
        if (!iAccountState!!.isLogin) {
            return false
        }
        LogUtils.log(message.content, iAccountState!!.username)
        LogUtils.log(fileInfo.fileName, iAccountState!!.username)
        return true
    }

    fun getMessageList(): MutableList<Message> {
        NetUtil.mockNetExecutor()
        val messageList = ArrayList<Message>()
        val message = Message(
            1, "张三共享文件到消息中...", FileTransfer(
                iAccountState!!.isLogin
            ).download(1).fileName, 1615963675000L
        )
        messageList.add(message)
        messageList.add(
            Message(
                2, "李四共享视频到消息中...", FileTransfer(
                    iAccountState!!.isLogin
                ).download(2).fileName, 1615963688000L
            )
        )
        return messageList
    }

    //判断游标是否为空
    suspend fun getMessageListFromCache(): MutableList<Message>? {
        return dataSource?.getMessageListFromCache()
    }

    suspend fun saveMessageToCache(messageList: List<Message>) {
        dataSource?.saveMessageToCache(messageList)
    }

    init {
        ARouter.getInstance().inject(this)
        dataSource = mContext?.let { LocalDataSource(it) }
    }
}