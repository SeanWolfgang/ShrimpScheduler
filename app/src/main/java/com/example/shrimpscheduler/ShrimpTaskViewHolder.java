package com.example.shrimpscheduler;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class ShrimpTaskViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameTextView;
    private final TextView executeDatetimeTextView;
    private final TextView descriptionTextView;

    private ShrimpTaskViewHolder(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.task_name);
        executeDatetimeTextView = itemView.findViewById(R.id.execute_datetime);
        descriptionTextView = itemView.findViewById(R.id.description);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bind(String name, OffsetDateTime executeTime, String description) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        nameTextView.setText(name);
        executeDatetimeTextView.setText(formatter.format(executeTime));
        descriptionTextView.setText(description);
    }

    static ShrimpTaskViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_tasks_recycler, parent, false);
        return new ShrimpTaskViewHolder(view);
    }
}
