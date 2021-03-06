package com.antyzero.smoksmog.screen;


import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.antyzero.smoksmog.rules.RxSchedulerTestRule;
import com.antyzero.smoksmog.ui.screen.settings.SettingsActivity;
import com.squareup.spoon.Spoon;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith( AndroidJUnit4.class )
@LargeTest
public class SettingsActivityTest {

    @Rule
    public final ActivityTestRule<SettingsActivity> activityTestRule = new MockedNetworkActivityTestRule<>( SettingsActivity.class );
    @Rule
    public final RxSchedulerTestRule rxSchedulerTestRule = new RxSchedulerTestRule();

    @Test
    public void checkCreation() {

        // Given
        Activity activity = activityTestRule.getActivity();

        // When
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        // Then
        Spoon.screenshot( activity, "Created" );
    }
}
