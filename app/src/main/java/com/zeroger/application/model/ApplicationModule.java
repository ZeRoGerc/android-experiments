package com.zeroger.application.model;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @NonNull
    private final ExperimentsApplication application;

    public ApplicationModule(@NonNull ExperimentsApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @NonNull
    ExperimentsApplication provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    @NonNull
    Handler provideMainThreadHandler() {
        return new Handler(Looper.getMainLooper());
    }
}
