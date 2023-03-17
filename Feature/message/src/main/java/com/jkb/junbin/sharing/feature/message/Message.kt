package com.jkb.junbin.sharing.feature.message

import com.alibaba.android.arouter.launcher.ARouter
import com.jkb.junbin.sharing.feature.message.util.DateUtil
import com.jkb.junbin.sharing.function.shell.interfaces.IFileStatistics

class Message(var id: Int, var content: String, var fileName: String, var date: Long) {
    val formatDate = DateUtil.getDateToString(date)
    val downloadCount = ARouter.getInstance().navigation(IFileStatistics::class.java)?.getDownloadCount(id.toString())
}