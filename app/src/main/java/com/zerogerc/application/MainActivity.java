package com.zerogerc.application;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements BottomBarFragment.BottomBarFragmentCallbacks {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_fragment_container, new MainFragment(), MainFragment.class.getName())
                    .commit();
        }
    }

    @Override
    public void onFirstItemClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_fragment_container, new MainFragment(), MainFragment.class.getName())
                .commit();
    }


    @Override
    public void onSecondItemClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_fragment_container, new AdditionalFragment(), MainFragment.class.getName())
                .addToBackStack(null)
                .commit();
    }
}
