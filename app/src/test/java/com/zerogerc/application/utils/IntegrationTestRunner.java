package com.zerogerc.application.utils;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import org.junit.runners.model.InitializationError;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Properties;

public class IntegrationTestRunner extends BaseTestRunner {

    /**
     * Creates a runner to run {@code testClass}. Looks in your working directory for your AndroidManifest.xml file
     * and res directory by default. Use the {@link Config} annotation to configure.
     *
     * @param testClass the test class to be run
     * @throws InitializationError if junit says so
     */
    public IntegrationTestRunner(@NonNull Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    @CallSuper
    @NonNull
    protected Properties getConfigProperties() {
        final Properties properties = super.getConfigProperties();
        properties.setProperty("application", IntegrationTestApplication.class.getName());
        return properties;
    }

    @NonNull
    public static IntegrationTestApplication app() {
        return (IntegrationTestApplication) RuntimeEnvironment.application;
    }
}
