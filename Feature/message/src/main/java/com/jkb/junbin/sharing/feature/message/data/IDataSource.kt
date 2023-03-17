package com.jkb.junbin.sharing.feature.message.data

import com.jkb.junbin.sharing.feature.message.Message

interface IDataSource {
    //判断游标是否为空
    fun getMessageListFromCache(): MutableList<Message>
    fun saveMessageToCache(messageList: List<Message>)
}