package com.zerogerc.application.model;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fuck_boilerplate.rx_paparazzo.RxPaparazzo;

import rx_activity_result.RxActivityResult;

public class ExperimentsApplication extends Application {

    @SuppressWarnings("NullableProblems") // onCreate
    @NonNull
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = initApplicationComponent();
        RxPaparazzo.register(this);
        RxActivityResult.register(this);
    }

    @NonNull
    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @NonNull
    public static ExperimentsApplication getApplication(@NonNull Context context) {
        return (ExperimentsApplication) context.getApplicationContext();
    }

    @NonNull
    public static ApplicationComponent getApplicationComponent(@NonNull Context context) {
        return getApplication(context).getApplicationComponent();
    }

    @NonNull
    private ApplicationComponent initApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
