package com.jkb.junbin.sharing.feature.message.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jkb.junbin.sharing.feature.message.Message

@Database(entities = [Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}