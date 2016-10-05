package com.zerogerc.application.utils;

import rx.plugins.RxJavaHooks;

import static rx.schedulers.Schedulers.immediate;

public class RobolectricTools {

    public static void enableFakeRxScheduler() {
        RxJavaHooks.setOnIOScheduler(scheduler -> immediate());
    }
}
