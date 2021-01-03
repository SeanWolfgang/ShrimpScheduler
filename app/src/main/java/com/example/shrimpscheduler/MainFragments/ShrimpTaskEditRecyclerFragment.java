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
import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskAdapter;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskEditAdapter;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskViewModel;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ShrimpTaskEditRecyclerFragment extends Fragment {
    private RecyclerView recyclerView;
    public ShrimpTaskEditAdapter adapter = new ShrimpTaskEditAdapter(new ShrimpTaskEditAdapter.ShrimpTaskDiff());
    private ShrimpTaskViewModel dateShrimpTaskViewModel;
    private LocalDate filterDate = LocalDate.now();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shrimp_task_content_main, container, false);

        dateShrimpTaskViewModel = new ViewModelProvider(this).get(ShrimpTaskViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(100);

        dateShrimpTaskViewModel.setDate(filterDate);

        dateShrimpTaskViewModel.getShrimpTaskDate().observe(this, dateTasks -> {
            dateShrimpTaskViewModel.setDate(filterDate);
            adapter.submitList(dateTasks);
        });

        return view;
    }

    public ShrimpTaskViewModel getShrimpTaskViewModel() {return dateShrimpTaskViewModel;}

    public ShrimpTaskEditAdapter getAdapter() {
        return adapter;
    }

    public void setFilterDate(LocalDate filterDate) {
        this.filterDate = filterDate;
    }
}
