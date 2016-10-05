package com.zerogerc.application;

import com.zerogerc.application.utils.IntegrationTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.util.ActivityController;

import static org.robolectric.Robolectric.buildActivity;

@RunWith(IntegrationTestRunner.class)
public class MainActivityTest {

    @Test
    public void onCreate_shouldWorkOnNormalLifecycle() {
        final ActivityController<MainActivity> controller = buildActivity(MainActivity.class);
        controller
                .create()
                .start()
                .resume();

        controller
                .pause()
                .stop()
                .destroy();
    }
}