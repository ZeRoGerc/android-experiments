package com.zeroger.application.model;

import android.os.Handler;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @NonNull
    ExperimentsApplication application();

    @NonNull
    Handler mainThreadHandler();
}
