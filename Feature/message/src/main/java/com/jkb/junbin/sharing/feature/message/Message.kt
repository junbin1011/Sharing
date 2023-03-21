package com.jkb.junbin.sharing.feature.message

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.alibaba.android.arouter.launcher.ARouter
import com.jkb.junbin.sharing.feature.message.util.DateUtil
import com.jkb.junbin.sharing.function.shell.interfaces.IFileStatistics

@Entity(tableName = "message_info")
class Message(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "fileName") var fileName: String,
    @ColumnInfo(name = "date") var date: Long
) {
    @Ignore
    val formatDate = DateUtil.getDateToString(date)
    @Ignore
    val downloadCount = ARouter.getInstance().navigation(IFileStatistics::class.java)
        ?.getDownloadCount(id.toString())
}