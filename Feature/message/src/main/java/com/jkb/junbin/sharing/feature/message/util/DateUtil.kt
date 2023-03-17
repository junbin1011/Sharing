package com.jkb.junbin.sharing.feature.message.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun getDateToString(time: Long): String {
        val d = Date(time)
        val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        return sf.format(d)
    }
}