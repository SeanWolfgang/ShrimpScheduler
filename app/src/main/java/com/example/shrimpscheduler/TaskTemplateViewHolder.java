package com.example.shrimpscheduler;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskTemplateViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameTextView;
    private final TextView descriptionTextView;
    private final TextView repeatTextView;
    private final TextView repeatIntervalTextView;
    private final Button editButton;

    private TaskTemplateViewHolder(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.template_name);
        descriptionTextView = itemView.findViewById(R.id.template_description);
        repeatTextView = itemView.findViewById(R.id.template_repeat);
        repeatIntervalTextView = itemView.findViewById(R.id.repeat_interval);
        editButton = itemView.findViewById(R.id.edit_button);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bind(String name, String description, boolean repeat, int interval) {
        nameTextView.setText(name);
        descriptionTextView.setText(description);
        if (repeat) {
            repeatTextView.setText("Repeat: Yes");
            repeatIntervalTextView.setText("Every " + Integer.toString(interval) + " day(s)");
        } else {
            repeatTextView.setText("Repeat: No");
        }

    }

    static TaskTemplateViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_template_recycler, parent, false);
        return new TaskTemplateViewHolder(view);
    }
}
