package com.zerogerc.application;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.State;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView textView;

    @SuppressWarnings("NullableProblems") // onCreate
    @State
    @NonNull
    ArrayParcel arrayParcel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            ArrayList<String> x = new ArrayList<>(3);
            x.add("first");
            x.add("second");
            x.add("third");

            arrayParcel = ArrayParcel.builder()
                    .values(x)
                    .build();
        } else {
            Icepick.restoreInstanceState(this, savedInstanceState);
        }

        textView.setText(TextUtils.join(", ", arrayParcel.values()));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
}
