package com.zerogerc.application;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StringViewHolder extends BindingViewHolder<String> {

    @BindView(R.id.item_text)
    TextView textView;

    public StringViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(@NonNull String item) {
        textView.setText(item);
    }
}
