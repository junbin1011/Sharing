package com.jkb.junbin.sharing.feature.message


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.alibaba.android.arouter.launcher.ARouter
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
@Config(shadows = [ShadowMessageRepository::class])
class DynamicViewModelTest {
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

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
    fun `show show message list when get success`() = runBlocking {
        //given
        ShadowMessageRepository.state = ShadowMessageRepository.State.SUCCESS
        val messageViewModel = MessageViewModel(ApplicationProvider.getApplicationContext())
        //when
        messageViewModel.getMessageList()
        //then
        val messageOne = LiveDataTestUtil.getValue(messageViewModel.messageListLiveData)[0]
        assertThat(messageOne.id).isEqualTo(1)
        assertThat(messageOne.content).isEqualTo("张三共享文件到消息中...")
        assertThat(messageOne.fileName).isEqualTo("大型Android遗留系统重构.pdf")
        assertThat(messageOne.formatDate).isEqualTo("2021-03-17 14:47:55")
        val messageTwo = LiveDataTestUtil.getValue(messageViewModel.messageListLiveData)[1]
        assertThat(messageTwo.id).isEqualTo(2)
        assertThat(messageTwo.content).isEqualTo("李四共享视频到消息中...")
        assertThat(messageTwo.fileName).isEqualTo("修改代码的艺术.pdf")
        assertThat(messageTwo.formatDate).isEqualTo("2021-03-17 14:48:08")

    }

    @Test
    fun `show show dynamic list when net work exception but have cache`() = runBlocking {
        //given
        ShadowMessageRepository.state = ShadowMessageRepository.State.CACHE
        val messageViewModel = MessageViewModel(ApplicationProvider.getApplicationContext())
        //when
        messageViewModel.getMessageList()
        //then
        val messageOne = LiveDataTestUtil.getValue(messageViewModel.messageListLiveData)[0]
        assertThat(messageOne.id).isEqualTo(1)
        assertThat(messageOne.content).isEqualTo("张三共享文件到消息中...")
        assertThat(messageOne.fileName).isEqualTo("大型Android遗留系统重构.pdf")
        assertThat(messageOne.formatDate).isEqualTo("2021-03-17 14:47:55")
        val messageTwo = LiveDataTestUtil.getValue(messageViewModel.messageListLiveData)[1]
        assertThat(messageTwo.id).isEqualTo(2)
        assertThat(messageTwo.content).isEqualTo("李四共享视频到消息中...")
        assertThat(messageTwo.fileName).isEqualTo("修改代码的艺术.pdf")
        assertThat(messageTwo.formatDate).isEqualTo("2021-03-17 14:48:08")

    }

    @Test
    fun `show show error tip when net work exception and not have cache`() = runBlocking {
        //given
        ShadowMessageRepository.state = ShadowMessageRepository.State.ERROR
        val messageViewModel = MessageViewModel(ApplicationProvider.getApplicationContext())
        //when
        messageViewModel.getMessageList()
        //then
        val errorMessage = LiveDataTestUtil.getValue(messageViewModel.errorMessageLiveData)
        assertThat(errorMessage).isEqualTo("网络异常，请点击重试。")
        val messageList = LiveDataTestUtil.getValue(messageViewModel.messageListLiveData)
        assertThat(messageList).isNull()
    }

    @Test
    fun `show show empty tip when not has data`() = runBlocking {
        //given
        ShadowMessageRepository.state = ShadowMessageRepository.State.EMPTY
        val messageViewModel = MessageViewModel(ApplicationProvider.getApplicationContext())

        //when
        messageViewModel.getMessageList()
        //then
        val messageList = LiveDataTestUtil.getValue(messageViewModel.messageListLiveData)
        assertThat(messageList).isEmpty()
    }
}
