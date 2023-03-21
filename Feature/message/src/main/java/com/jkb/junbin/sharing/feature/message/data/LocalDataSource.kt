package com.jkb.junbin.sharing.feature.message.data

import android.content.Context
import androidx.room.Room
import com.jkb.junbin.sharing.feature.message.Message
import com.jkb.junbin.sharing.feature.message.db.AppDatabase

class LocalDataSource constructor(
    private var mContext: Context
) : IDataSource {

    val db = Room.databaseBuilder(
        mContext,
        AppDatabase::class.java, "message.db"
    ).build()

    override suspend fun getMessageListFromCache(): MutableList<Message> {
        return db.messageDao().getAll().toMutableList()
    }

    override suspend fun saveMessageToCache(messageList: List<Message>) {
        messageList.let {
            db.messageDao().deleteAll()
            db.messageDao().insertAll(*it.toTypedArray())
        }
    }
}