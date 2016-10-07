package com.zerogerc.application;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BottomBarFragment extends Fragment {

    @Nullable
    Unbinder unbinder;

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_bar_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }

    @OnClick(R.id.bottom_bar_first_item)
    public void onFirstItemClicked() {
        ((BottomBarFragmentCallbacks) getActivity()).onFirstItemClicked();
    }

    @OnClick(R.id.bottom_bar_second_item)
    public void onSecondItemClicked() {
        ((BottomBarFragmentCallbacks) getActivity()).onSecondItemClicked();
    }

    public interface BottomBarFragmentCallbacks {

        void onFirstItemClicked();

        void onSecondItemClicked();
    }
}
