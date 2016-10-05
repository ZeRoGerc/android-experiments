package com.zerogerc.application.utils;

import android.support.annotation.NonNull;

import com.zerogerc.application.model.ExperimentsApplication;

import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

import static com.zerogerc.application.utils.RobolectricTools.enableFakeRxScheduler;

public class IntegrationTestApplication extends ExperimentsApplication implements TestLifecycleApplication {

    @Override
    public void beforeTest(@NonNull Method method) {
        enableFakeRxScheduler();
    }

    @Override
    public void prepareTest(@NonNull Object test) {
    }

    @Override
    public void afterTest(@NonNull Method method) {
    }
}
