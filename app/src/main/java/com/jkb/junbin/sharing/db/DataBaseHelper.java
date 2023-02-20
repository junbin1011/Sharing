package com.jkb.junbin.sharing.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static String message_info = "message_info";
    public static String id = "id";
    public static String content = "content";
    public static String fileName = "fileName";
    public static String date = "date";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "message.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTable(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS " + message_info + "(\n" +
                "\t" + id + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t" + content + " VARCHAR(1024) ,\n" +
                "\t" + fileName + " VARCHAR(1024) ,\n" +
                "\t" + date + " LONG \n" + ")";

        try {
            db.execSQL(createTableSql);
        } catch (Exception e) {
            Log.d("Task:Sql", e.getMessage());
        }
    }
}
