package com.zerogerc.application;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends SmartFragmentStatePagerAdapter {

    private static final int COUNT = 2;

    @NonNull
    private final List<String> titles = new ArrayList<>(COUNT);

    public MainViewPagerAdapter(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager);
        titles.add("Date");
        titles.add("Time");
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DateFragment();
            default:
                return new TimeFragment();
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
