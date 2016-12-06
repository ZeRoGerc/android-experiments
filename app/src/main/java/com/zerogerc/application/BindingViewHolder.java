package com.zerogerc.application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Base class for all view holders.
 * @param <T> item for binding content to view
 */
public abstract class BindingViewHolder<T> extends RecyclerView.ViewHolder {

    public abstract void bind(@NonNull T item);

    public BindingViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @NonNull
    public Context getContext() {
        return itemView.getContext();
    }
}
