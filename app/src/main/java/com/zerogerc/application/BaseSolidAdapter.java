package com.zerogerc.application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import solid.collections.SolidList;

import static solid.collections.SolidList.empty;

public abstract class BaseSolidAdapter<T, V extends BindingViewHolder<T>> extends RecyclerView.Adapter<V> {

    @NonNull
    protected final LayoutInflater inflater;

    @NonNull
    private final Context context;

    @NonNull
    protected SolidList<T> items = empty();

    public BaseSolidAdapter(@NonNull Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    protected Context getContext() {
        return context;
    }

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(@NonNull SolidList<T> list) {
        this.items = list;
        notifyDataSetChanged();
    }

    public void clear() {
        items = empty();
        notifyDataSetChanged();
    }
}

