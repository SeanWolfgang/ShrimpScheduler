package com.example.shrimpscheduler.MainFragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.shrimpscheduler.ShrimpTaskPack.ShrimpTaskViewModel;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ShrimpTaskRecyclerFragmentDate extends ShrimpTaskRecyclerFragment {
    private ShrimpTaskViewModel dateShrimpTaskViewModel;
    private LocalDate filterDate = LocalDate.now();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        dateShrimpTaskViewModel = super.getShrimpTaskViewModel();

        dateShrimpTaskViewModel.setDate(filterDate);

        dateShrimpTaskViewModel.getShrimpTaskDate().observe(this, dateTasks -> {
            dateShrimpTaskViewModel.setDate(filterDate);
            adapter.submitList(dateTasks);
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
    public ShrimpTaskViewModel getShrimpTaskViewModel() {return dateShrimpTaskViewModel; }

    public void setFilterDate(LocalDate filterDate) {
        this.filterDate = filterDate;
    }
}
