package com.jkb.junbin.sharing.feature.file;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.alibaba.android.arouter.launcher.ARouter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;


@RunWith(AndroidJUnit4.class)
@LargeTest
@Config(shadows = {ShadowFileFragment.class})
public class FileFragmentTest {

    @Before
    public void setUp() throws Exception {
        ARouter.openDebug();
        ARouter.init(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void show_show_file_list_when_get_success() {
        //given
        ShadowFileFragment.state = ShadowFileFragment.State.SUCCESS;
        //when
        FragmentScenario<FileFragment> scenario = FragmentScenario.launchInContainer(FileFragment.class);
        scenario.onFragment(fragment -> {
            //then
            onView(withText("遗留代码重构.pdf")).check(matches(isDisplayed()));
            onView(withText("100.00K")).check(matches(isDisplayed()));
            onView(withText("系统组件化.pdf")).check(matches(isDisplayed()));
            onView(withText("9.67K")).check(matches(isDisplayed()));
        });
    }

    @Test
    public void show_show_error_tip_when_net_work_exception() {
        //given
        ShadowFileFragment.state = ShadowFileFragment.State.ERROR;
        //when
        FragmentScenario<FileFragment> scenario = FragmentScenario.launchInContainer(FileFragment.class);
        scenario.onFragment(fragment -> {
            //then
            onView(withText("NetworkErrorException")).check(matches(isDisplayed()));
        });
    }

    @Test
    public void show_show_empty_tip_when_not_has_data() {
        //given
        ShadowFileFragment.state = ShadowFileFragment.State.EMPTY;
        //when
        FragmentScenario<FileFragment> scenario = FragmentScenario.launchInContainer(FileFragment.class);
        scenario.onFragment(fragment -> {
            //then
            onView(withText("empty data")).check(matches(isDisplayed()));
        });
    }
}