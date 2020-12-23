package com.example.shrimpscheduler;

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

public class GroupRecyclerFragment extends Fragment {
    private RecyclerView recyclerView;
    private GroupViewModel groupViewModel;
    private GroupAdapter adapter = new GroupAdapter(new GroupAdapter.GroupDiff());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_content_main, container, false);

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view_template);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        groupViewModel.getAllGroups().observe(this, groups -> {
            adapter.submitList(groups);
        });

        return view;
    }

    public GroupViewModel getGroupViewModel() {return groupViewModel;}

    public GroupAdapter getAdapter() {
        return adapter;
    }

    /*
    public void setAdapter(TaskTemplateAdapter adapter) {
        this.adapter = adapter;
    }
    */
}