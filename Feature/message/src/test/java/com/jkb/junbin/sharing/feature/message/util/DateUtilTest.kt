package com.jkb.junbin.sharing.feature.message.util

import androidx.test.filters.SmallTest
import org.junit.Assert
import org.junit.Test

@SmallTest
class DateUtilTest {

    @Test
    fun `should return 2021-03-17 14 47 5 when input is 1615963675000L`() {
        val format = DateUtil.getDateToString(1615963675000L)
        Assert.assertEquals("2021-03-17 14:47:55", format)
    }
}