package com.example.shrimpscheduler.CreateTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shrimpscheduler.Group.GroupViewModel;
import com.example.shrimpscheduler.R;

public class TaskCreateNewGroupRecyclerFragment extends Fragment {
    private RecyclerView recyclerView;
    private GroupViewModel groupViewModel;
    public TaskCreateNewGroupAdapter adapter = new TaskCreateNewGroupAdapter(new TaskCreateNewGroupAdapter.GroupDiff());
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_task_group_content_main, container, false);

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        layoutManager  = new LinearLayoutManager(view.getContext());
        recyclerView = view.findViewById(R.id.create_task_activity_group_recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(100);

        groupViewModel.getAllGroups().observe(this, allGroups -> {
            // Update the cached copy of the words in the adapter.
             adapter.submitList(allGroups);
        });

        return view;
    }

    public GroupViewModel getGroupViewModel() {
        return groupViewModel;
    }

    public TaskCreateNewGroupAdapter getAdapter() {
        return adapter;
    }

    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
