package com.example.shrimpscheduler.MainFragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskViewModel;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ShrimpTaskRecyclerFragmentDate extends ShrimpTaskRecyclerFragment {
    private ShrimpTaskRecyclerFragmentDate.ShrimpTaskRecyclerFragmentDateUpdatedListener listener;
    private ShrimpTaskViewModel dateShrimpTaskViewModel;
    private LocalDate filterDate = LocalDate.now();

    public interface ShrimpTaskRecyclerFragmentDateUpdatedListener{
        void taskDateViewUpdatedListener();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        dateShrimpTaskViewModel = super.getShrimpTaskViewModel();

        dateShrimpTaskViewModel.setDate(filterDate);

        dateShrimpTaskViewModel.getShrimpTaskDate().observe(this, dateTasks -> {
            adapter.submitList(dateTasks);
            listener.taskDateViewUpdatedListener();
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
        dateShrimpTaskViewModel.setDate(filterDate);
    }

    private void displayTextScreenShort(String inputText) {
        Toast.makeText(
                getContext(),
                inputText,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ShrimpTaskRecyclerFragmentDate.ShrimpTaskRecyclerFragmentDateUpdatedListener) {
            listener = (ShrimpTaskRecyclerFragmentDate.ShrimpTaskRecyclerFragmentDateUpdatedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ShrimpTaskRecyclerFragmentDateUpdatedListener");
        }
    }

    public LocalDate getFilterDate() {
        return filterDate;
    }

    public void setFilterDateNoCommit(LocalDate filterDate) {
        this.filterDate = filterDate;
    }
}
