package com.example.shrimpscheduler;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShrimpTaskPagedListRecyclerFragment extends Fragment {
    private RecyclerView recyclerView;
    private ShrimpTaskViewModel shrimpTaskViewModel;
    public ShrimpTaskPagedListAdapter adapter = new ShrimpTaskPagedListAdapter(new ShrimpTaskAdapter.ShrimpTaskDiff());
    private int taskNameMatchCount;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shrimp_task_content_main, container, false);

        shrimpTaskViewModel = new ViewModelProvider(this).get(ShrimpTaskViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        shrimpTaskViewModel.initAllTasks();

        shrimpTaskViewModel.getAllPagedShrimpTasks().observe(this, pagedList -> {
            adapter.submitList(pagedList);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(100);

        return view;
    }

    public ShrimpTaskViewModel getShrimpTaskViewModel() {return shrimpTaskViewModel;}

    public ShrimpTaskPagedListAdapter getAdapter() {
        return adapter;
    }

    public int getTaskNameMatchCount() {
        return taskNameMatchCount;
    }

    /*
    public void setAdapter(ShrimpTaskAdapter adapter) {
        this.adapter = adapter;
    }
    */
}
