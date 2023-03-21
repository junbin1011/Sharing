package com.jkb.junbin.sharing.feature.message.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jkb.junbin.sharing.feature.message.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM message_info")
    suspend fun getAll(): List<Message>

    @Insert
    suspend fun insertAll(vararg message: Message)


    @1("DELETE FROM message_info")
    suspend fun deleteAll()
}