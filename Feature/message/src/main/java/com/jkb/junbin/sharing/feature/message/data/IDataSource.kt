package com.jkb.junbin.sharing.feature.message.data

import com.jkb.junbin.sharing.feature.message.Message

interface IDataSource {
    //判断游标是否为空
    suspend fun getMessageListFromCache(): MutableList<Message>
    suspend fun saveMessageToCache(messageList: List<Message>)
}