package com.zerogerc.application;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.State;

public class TimeFragment extends Fragment {

    @BindView(R.id.time_picker)
    TimePicker timePicker;

    @State
    int hour;

    @State
    int minute;

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_picker_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Icepick.restoreInstanceState(this, savedInstanceState);

        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        hour = timePicker.getCurrentHour();
        minute = timePicker.getCurrentMinute();

        Icepick.saveInstanceState(this, outState);
    }
}
