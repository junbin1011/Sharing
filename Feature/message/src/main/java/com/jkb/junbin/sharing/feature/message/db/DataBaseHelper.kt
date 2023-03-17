package com.jkb.junbin.sharing.feature.message.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseHelper(context: Context?) : SQLiteOpenHelper(context, "message.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        createTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    fun createTable(db: SQLiteDatabase) {
        val createTableSql = """CREATE TABLE IF NOT EXISTS $message_info(
	$id INTEGER PRIMARY KEY AUTOINCREMENT,
	$content VARCHAR(1024) ,
	$fileName VARCHAR(1024) ,
	$date LONG 
)"""
        try {
            db.execSQL(createTableSql)
        } catch (e: Exception) {
            Log.d("Task:Sql", e.message!!)
        }
    }

    companion object {
        var message_info = "message_info"
        var id = "id"
        var content = "content"
        var fileName = "fileName"
        var date = "date"
    }
}