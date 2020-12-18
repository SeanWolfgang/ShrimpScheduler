package com.example.shrimpscheduler;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ShrimpTaskRecyclerFragmentDate extends ShrimpTaskRecyclerFragment {
    private LocalDate date = LocalDate.now();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        this.getShrimpTaskViewModel().getShrimpTaskDate(date).observe(this, dateShrimpTasks -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(dateShrimpTasks);
        });

        return view;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
