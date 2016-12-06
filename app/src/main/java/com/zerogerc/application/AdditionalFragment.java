package com.zerogerc.application;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import solid.collections.SolidList;

public class AdditionalFragment extends Fragment {

//    @BindView(R.id.fragment_additional_toolbar)
//    Toolbar toolbar;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    StringsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter  = new StringsAdapter(getContext());
    }

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_additional, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbar);

        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(
                getContext().getResources().getInteger(R.integer.feed_columns),
                StaggeredGridLayoutManager.VERTICAL
        ));
        recyclerView.setAdapter(adapter);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            ArrayList<String> list = new ArrayList<>(100);
            for (int i = 0; i < 100; i++) {
                list.add("item " + String.valueOf(i));
            }
            adapter.setItems(new SolidList<>(list));
            recyclerView.setVisibility(View.VISIBLE);
        }, 5000);
    }
}
