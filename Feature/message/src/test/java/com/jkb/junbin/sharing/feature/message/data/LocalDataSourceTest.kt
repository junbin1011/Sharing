package com.jkb.junbin.sharing.feature.message.data

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.alibaba.android.arouter.launcher.ARouter
import com.google.common.truth.Truth
import com.jkb.junbin.sharing.feature.message.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class LocalDataSourceTest {
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        ARouter.openDebug()
        ARouter.init(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun `should get message list is empty when database has not data`() = runBlocking {
        //given
        val localDataSource = LocalDataSource(ApplicationProvider.getApplicationContext())
        //when
        val messageListFromCache = localDataSource.getMessageListFromCache()
        //then
        assert(messageListFromCache.isEmpty())
    }

    @Test
    fun `should get message list success when database has data`() = runBlocking {
        //given
        val localDataSource = LocalDataSource(ApplicationProvider.getApplicationContext())
        localDataSource.saveMessageToCache(getMockData())
        //when
        val messageListFromCache = localDataSource.getMessageListFromCache()
        //then
        val messageOne = messageListFromCache[0]
        Truth.assertThat(messageOne.id).isEqualTo(1)
        Truth.assertThat(messageOne.content).isEqualTo("张三共享文件到消息中...")
        Truth.assertThat(messageOne.fileName).isEqualTo("大型Android遗留系统重构.pdf")
        Truth.assertThat(messageOne.formatDate).isEqualTo("2021-03-17 14:47:55")
        val messageTwo = messageListFromCache[1]
        Truth.assertThat(messageTwo.id).isEqualTo(2)
        Truth.assertThat(messageTwo.content).isEqualTo("李四共享视频到消息中...")
        Truth.assertThat(messageTwo.fileName).isEqualTo("修改代码的艺术.pdf")
        Truth.assertThat(messageTwo.formatDate).isEqualTo("2021-03-17 14:48:08")
    }


    private fun getMockData(): ArrayList<Message> {
        val messageList = ArrayList<Message>()
        val message = Message(
            1, "张三共享文件到消息中...", "大型Android遗留系统重构.pdf", 1615963675000L
        )
        messageList.add(message)
        messageList.add(
            Message(
                2, "李四共享视频到消息中...", "修改代码的艺术.pdf", 1615963688000L
            )
        )
        return messageList
    }
}