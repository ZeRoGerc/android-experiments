package com.zerogerc.application.utils;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Properties;

public class BaseTestRunner extends RobolectricTestRunner {

    /**
     * Creates a runner to run {@code testClass}. Looks in your working directory for your AndroidManifest.xml file
     * and res directory by default. Use the {@link Config} annotation to configure.
     *
     * @param testClass the test class to be run
     * @throws InitializationError if junit says so
     */
    public BaseTestRunner(@NonNull Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    @CallSuper
    @NonNull
    protected Properties getConfigProperties() {
        final Properties properties = new Properties();
        properties.setProperty("constants", "com.zerogerc.application.BuildConfig");
        properties.setProperty("packageName", "com.zerogerc.application");
        properties.setProperty("sdk", "21");
        return properties;
    }
}
