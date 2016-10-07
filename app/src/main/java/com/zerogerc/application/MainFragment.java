package com.zerogerc.application;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    @BindView(R.id.fragment_main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.fragment_main_tabs)
    TabLayout tabLayout;

    @BindView(R.id.fragment_main_view_pager)
    ViewPager viewPager;

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        viewPager.setAdapter(new MainViewPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }
}
