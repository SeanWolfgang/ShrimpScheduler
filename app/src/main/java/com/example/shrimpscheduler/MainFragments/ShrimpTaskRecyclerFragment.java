package com.example.shrimpscheduler.MainFragments;

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

import com.example.shrimpscheduler.R;
import com.example.shrimpscheduler.ShrimpTaskPack.ShrimpTaskAdapter;
import com.example.shrimpscheduler.ShrimpTaskPack.ShrimpTaskViewModel;

public class ShrimpTaskRecyclerFragment extends Fragment {
    private RecyclerView recyclerView;
    private ShrimpTaskViewModel shrimpTaskViewModel;
    public ShrimpTaskAdapter adapter = new ShrimpTaskAdapter(new ShrimpTaskAdapter.ShrimpTaskDiff());
    private int taskNameMatchCount;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shrimp_task_content_main, container, false);

        shrimpTaskViewModel = new ViewModelProvider(this).get(ShrimpTaskViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(100);

        shrimpTaskViewModel.getShrimpTasksNameMatch().observe(this, shrimpTaskNameCountDBMatch -> {
            // Update the cached copy of the words in the adapter.
            taskNameMatchCount = shrimpTaskNameCountDBMatch;
        });

        return view;
    }

    public ShrimpTaskViewModel getShrimpTaskViewModel() {return shrimpTaskViewModel;}

    public ShrimpTaskAdapter getAdapter() {
        return adapter;
    }

    public void showAllTasks() {
        shrimpTaskViewModel.getAllShrimpTasks().observe(this, shrimpTasks -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(shrimpTasks);
        });
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
