package com.example.shrimpscheduler;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ShrimpTaskRecyclerFragmentAll extends ShrimpTaskRecyclerFragment {
    private ShrimpTaskViewModel allShrimpTaskViewModel;
    private LocalDate filterDate = LocalDate.now();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        allShrimpTaskViewModel = super.getShrimpTaskViewModel();

        allShrimpTaskViewModel.getAllShrimpTasks().observe(this, allTasks -> {
            adapter.submitList(allTasks);
        });

        /*
        allShrimpTaskViewModel.getAllShrimpTasks().observe(this, shrimpTasks -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(shrimpTasks);
        });
        */

        return view;
    }

    @Override
    public ShrimpTaskViewModel getShrimpTaskViewModel() {return allShrimpTaskViewModel; }

    public void setFilterDate(LocalDate filterDate) {
        this.filterDate = filterDate;
    }
}