package com.jkb.junbin.sharing.feature.message

import android.accounts.NetworkErrorException
import com.jkb.junbin.sharing.feature.message.data.MessageRepository
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements

@Implements(MessageRepository::class)
class ShadowMessageRepository {
    enum class State {
        SUCCESS, ERROR, EMPTY, CACHE
    }

    @Implementation
    fun getMessageList(): MutableList<Message> {
        if (state == State.SUCCESS) {
            return getMessageData()
        } else if (state == State.EMPTY) {
            return mutableListOf();
        } else if (state == State.ERROR) {
            throw NetworkErrorException();
        } else {
            throw NetworkErrorException();
        }
    }

    @Implementation
    fun getMessageListFromCache(): MutableList<Message> {
        if (state == State.CACHE) {
            return getMessageData()
        } else {
            return mutableListOf()
        }
    }

    @Implementation
    fun saveMessageToCache(messageList: List<Message>) {}

    private fun getMessageData(): ArrayList<Message> {
        val messageList = ArrayList<Message>()
        val message = Message(
            1, "张三共享文件到消息中...", "大型Android遗留系统重构.pdf", 1615963675000L
        )
        messageList.add(message)
        messageList.add(
            Message(
                2, "李四共享视频到消息中...", "修改代码的艺术.pdf", 1615963688000L
            )
        )
        return messageList
    }

    companion object {
        var state = State.SUCCESS
    }

}