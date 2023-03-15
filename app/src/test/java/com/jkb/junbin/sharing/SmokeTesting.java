package com.jkb.junbin.sharing;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.jkb.junbin.sharing.feature.account.AccountController;
import com.jkb.junbin.sharing.feature.account.AccountInfo;
import com.jkb.junbin.sharing.feature.account.LoginActivity;
import com.jkb.junbin.sharing.function.shell.MainActivity;
import com.jkb.junbin.sharing.shadow.ShadowFileRemoteDataSource;
import com.jkb.junbin.sharing.shadow.ShadowMessageController;
import com.jkb.junbin.sharing.shadow.ShadowPostCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;



@RunWith(AndroidJUnit4.class)
@Config(shadows = {ShadowFileRemoteDataSource.class, ShadowMessageController.class, ShadowPostCard.class})
@LargeTest
public class SmokeTesting {
    @Before
    public void setUp() {
        AccountController.isLogin = true;
        AccountInfo mockAccountInfo = new AccountInfo();
        mockAccountInfo.username = "test";
        AccountController.currentAccountInfo = mockAccountInfo;
    }

    @After
    public void tearDown() {
        AccountController.isLogin = false;
        AccountController.currentAccountInfo = null;
    }

    @Test
    public void show_login_success_when_input_correct_username_and_password() {
        ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.username)).perform(typeText("123@163.com"));
        onView(withId(R.id.password)).perform(typeText("123456"));
        Intents.init();
        onView(withId(R.id.login)).perform(click());
        intended(allOf(
                toPackage("com.jkb.junbin.sharing"),
                hasComponent(hasClassName(MainActivity.class.getName()))));
    }

    @Test
    public void show_show_file_ui_when_click_tab_file() {
        //given
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            //when
            onView(withText("文件")).perform(click());
            //then
            onView(withText("Android遗留系统重构.pdf")).check(isVisible());
            onView(withText("100.00K")).check(isVisible());
            onView(withText("研发那些事第一季.mp4")).check(isVisible());
            onView(withText("9.67K")).check(isVisible());
        });
    }

    @Test
    public void show_message_ui_when_click_tab_messsage() {
        //given
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            //when
            onView(withText("消息")).perform(click());
            //then
            onView(withText("张三共享文件到消息中...")).check(isVisible());
            onView(withText("1.pdf")).check(isVisible());
            onView(withText("2021-03-17 14:47:55")).check(isVisible());
            onView(withText("李四共享视频到消息中...")).check(isVisible());
            onView(withText("2.pdf")).check(isVisible());
            onView(withText("2021-03-17 14:48:08")).check(isVisible());
        });
    }

    @Test
    public void show_account_ui_when_click_tab_account() {
        //given
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            //when
            onView(withText("个人")).perform(click());
            //then
            onView(withText("test")).check(isVisible());
        });
    }

    public ViewAssertion isVisible(){
        return matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE));
    }
}

