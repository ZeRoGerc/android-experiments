package com.zerogerc.application;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

public abstract class SmartFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    @NonNull
    private final SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public SmartFragmentStatePagerAdapter(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    @CallSuper
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    @CallSuper
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}