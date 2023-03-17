package com.jkb.junbin.sharing.feature.message.data

import android.content.ContentValues
import android.content.Context
import com.jkb.junbin.sharing.feature.message.Message
import com.jkb.junbin.sharing.feature.message.db.DataBaseHelper

class LocalDataSource constructor(
    private var mContext: Context
) : IDataSource {
    //判断游标是否为空
    override fun getMessageListFromCache(): MutableList<Message> {
        val messageList: MutableList<Message> = ArrayList()
        val dataBaseHelper = DataBaseHelper(mContext)
        val c = dataBaseHelper.writableDatabase.query(
            DataBaseHelper.Companion.message_info,
            null,
            null,
            null,
            null,
            null,
            null
        )
        if (c.moveToFirst()) { //判断游标是否为空
            for (i in 0 until c.count) {
                c.move(i) //移动到指定记录
                val id = c.getInt(c.getColumnIndex(DataBaseHelper.Companion.id))
                val content = c.getString(c.getColumnIndex(DataBaseHelper.Companion.content))
                val fileName = c.getString(c.getColumnIndex(DataBaseHelper.Companion.fileName))
                val date = c.getLong(c.getColumnIndex(DataBaseHelper.Companion.date))
                messageList.add(Message(id, content, fileName, date))
            }
        }
        return messageList
    }

    override fun saveMessageToCache(messageList: List<Message>) {
        val dataBaseHelper = DataBaseHelper(mContext)
        if (messageList.isNotEmpty()) {
            dataBaseHelper.writableDatabase.delete(
                DataBaseHelper.Companion.message_info,
                null,
                null
            )
            for (message in messageList) {
                val cv = ContentValues()
                cv.put(DataBaseHelper.Companion.id, message.id)
                cv.put(DataBaseHelper.Companion.content, message.content)
                cv.put(DataBaseHelper.Companion.date, message.date)
                cv.put(DataBaseHelper.Companion.fileName, message.fileName)
                dataBaseHelper.writableDatabase.insert(
                    DataBaseHelper.Companion.message_info,
                    null,
                    cv
                )
            }
        }
    }
}