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

public class ShrimpTaskRecyclerFragment extends Fragment {
    private RecyclerView recyclerView;
    private ShrimpTaskViewModel shrimpTaskViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shrimp_task_content_main, container, false);

        shrimpTaskViewModel = new ViewModelProvider(this).get(ShrimpTaskViewModel.class);
        final ShrimpTaskAdapter adapter = new ShrimpTaskAdapter(new ShrimpTaskAdapter.ShrimpTaskDiff());

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        shrimpTaskViewModel.getAllShrimpTasks().observe(this, shrimpTasks -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(shrimpTasks);
        });

        return view;
    }

    public ShrimpTaskViewModel getShrimpTaskViewModel() {return shrimpTaskViewModel;}
}
