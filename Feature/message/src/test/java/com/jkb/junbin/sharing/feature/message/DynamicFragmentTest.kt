package com.jkb.junbin.sharing.feature.message

import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alibaba.android.arouter.launcher.ARouter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@LargeTest
@Config(shadows = [ShadowMessageRepository::class])
class FragmentTest {

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ARouter.openDebug()
        ARouter.init(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun `show show message list when get success`() {
        //given
        ShadowMessageRepository.state = ShadowMessageRepository.State.SUCCESS
        //when
        val scenario: FragmentScenario<MessageFragment> =
            FragmentScenario.launchInContainer(MessageFragment::class.java)
        scenario.onFragment() {
            //then
            onView(withText("张三共享文件到消息中...")).check(matches(isDisplayed()))
            onView(withText("大型Android遗留系统重构.pdf")).check(matches(isDisplayed()))
            onView(withText("2021-03-17 14:47:55")).check(matches(isDisplayed()))
            onView(withText("李四共享视频到消息中...")).check(matches(isDisplayed()))
            onView(withText("修改代码的艺术.pdf")).check(matches(isDisplayed()))
            onView(withText("2021-03-17 14:48:08")).check(matches(isDisplayed()))
        }
    }

    @Test
    fun `show show message list when net work exception but have cache`() {
        //given
        ShadowMessageRepository.state = ShadowMessageRepository.State.CACHE
        //when
        val scenario: FragmentScenario<MessageFragment> =
            FragmentScenario.launchInContainer(MessageFragment::class.java)
        scenario.onFragment() {
            //then
            onView(withText("张三共享文件到消息中...")).check(matches(isDisplayed()))
            onView(withText("大型Android遗留系统重构.pdf")).check(matches(isDisplayed()))
            onView(withText("2021-03-17 14:47:55")).check(matches(isDisplayed()))
            onView(withText("李四共享视频到消息中...")).check(matches(isDisplayed()))
            onView(withText("修改代码的艺术.pdf")).check(matches(isDisplayed()))
            onView(withText("2021-03-17 14:48:08")).check(matches(isDisplayed()))
        }
    }

    @Test
    fun `show show error tip when net work exception and not have cache`() {
        //given
        ShadowMessageRepository.state = ShadowMessageRepository.State.ERROR
        //when
        val scenario: FragmentScenario<MessageFragment> =
            FragmentScenario.launchInContainer(MessageFragment::class.java)
        scenario.onFragment() {
            //then
            onView(withText("网络异常，请点击重试。")).check(matches(isDisplayed()))
        }
    }

    @Test
    fun `show show empty tip when not has data`() {
        //given
        ShadowMessageRepository.state = ShadowMessageRepository.State.EMPTY
        //when
        val scenario: FragmentScenario<MessageFragment> =
            FragmentScenario.launchInContainer(MessageFragment::class.java)
        scenario.onFragment() {
            //then
            onView(withText("没有数据，请点击重试。")).check(matches(isDisplayed()))
        }
    }
}