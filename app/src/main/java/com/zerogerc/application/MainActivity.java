package com.zerogerc.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button_toggle)
    ToggleButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
