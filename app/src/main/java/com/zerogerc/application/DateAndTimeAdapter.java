package com.zerogerc.application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class DateAndTimeAdapter extends PagerAdapter {

    @NonNull
    private final Context context;

    @NonNull
    private final ViewPager viewPager;

    @NonNull
    private final String dateString;

    @NonNull
    private final String timeString;

    public DateAndTimeAdapter(@NonNull ViewPager viewPager) {
        this.context = viewPager.getContext();
        this.viewPager = viewPager;
        this.dateString = "Date";
        this.timeString = "Time";
    }

    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return viewPager.getChildAt(position);
    }

    @Override
    @NonNull
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return dateString;
            case 1:
                return timeString;
            default:
                return super.getPageTitle(position);
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return viewPager.getChildCount();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
