package com.example.shrimpscheduler.MainFragments;

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

import com.example.shrimpscheduler.R;
import com.example.shrimpscheduler.Template.TaskTemplateAdapter;
import com.example.shrimpscheduler.Template.TaskTemplateViewModel;

public class TaskTemplateRecyclerFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskTemplateViewModel taskTemplateViewModel;
    private TaskTemplateAdapter adapter = new TaskTemplateAdapter(new TaskTemplateAdapter.TaskTemplateDiff());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_template_content_main, container, false);

        taskTemplateViewModel = new ViewModelProvider(this).get(TaskTemplateViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view_template);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        taskTemplateViewModel.getAllTaskTemplates().observe(this, taskTemplates -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(taskTemplates);
        });

        return view;
    }

    public TaskTemplateViewModel getTaskTemplateViewModel() {return taskTemplateViewModel;}

    public TaskTemplateAdapter getAdapter() {
        return adapter;
    }

    /*
    public void setAdapter(TaskTemplateAdapter adapter) {
        this.adapter = adapter;
    }
    */
}
