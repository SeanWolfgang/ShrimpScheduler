package com.example.shrimpscheduler.Template;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shrimpscheduler.R;

public class TaskTemplateViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameTextView;
    private final TextView descriptionTextView;
    private final TextView repeatTextView;
    private final TextView repeatIntervalTextView;
    private final ImageButton editButton;
    private final ImageButton deleteButton;

    private TaskTemplateViewHolder(View itemView, TaskTemplateAdapter.OnTaskTemplateClickListener listener) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.template_name);
        descriptionTextView = itemView.findViewById(R.id.template_description);
        repeatTextView = itemView.findViewById(R.id.template_repeat);
        repeatIntervalTextView = itemView.findViewById(R.id.repeat_interval);
        editButton = itemView.findViewById(R.id.template_edit_button);
        deleteButton = itemView.findViewById(R.id.template_delete_button);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onTaskTemplateEditClick(position);
                    }
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onTaskTemplateDeleteClick(position);
                    }
                }
            }
        });
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

    public static TaskTemplateViewHolder create(ViewGroup parent, TaskTemplateAdapter.OnTaskTemplateClickListener listener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_template_recycler, parent, false);
        return new TaskTemplateViewHolder(view, listener);
    }
}
