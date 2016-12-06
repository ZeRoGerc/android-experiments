package com.zerogerc.application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

public class StringsAdapter extends BaseSolidAdapter<String, StringViewHolder> {

    public StringsAdapter(@NonNull Context context) {
        super(context);
    }

    @Override
    public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StringViewHolder(inflater.inflate(R.layout.item_1, parent, false));
    }
}
