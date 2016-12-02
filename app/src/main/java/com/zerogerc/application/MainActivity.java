package com.zerogerc.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Fragment1())
                    .commit();
        }
    }

    public void invokeFirst() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Fragment1())
                .addToBackStack(null)
                .commit();
    }

    public void invokeSecond() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Fragment2())
                .addToBackStack(null)
                .commit();
    }
}
