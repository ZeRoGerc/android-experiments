package com.zerogerc.application;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;

@AutoValue
public abstract class ArrayParcel implements Parcelable {

    @NonNull
    public abstract ArrayList<String> values();

    @NonNull
    public static Builder builder() {
        return new AutoValue_ArrayParcel.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {

        @NonNull
        public abstract Builder values(@NonNull ArrayList<String> values);

        @NonNull
        public abstract ArrayParcel build();
    }

}
