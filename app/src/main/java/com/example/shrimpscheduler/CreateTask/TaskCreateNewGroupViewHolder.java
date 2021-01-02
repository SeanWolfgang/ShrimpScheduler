package com.example.shrimpscheduler.CreateTask;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shrimpscheduler.R;

public class TaskCreateNewGroupViewHolder extends RecyclerView.ViewHolder {
    private final CheckBox groupCheckbox;
    private final TextView groupFullNameTextView;
    private final TextView groupLastDateTextView;
    private final TextView groupDateTextView;
    private final ImageButton groupDateImageButton;

    private TaskCreateNewGroupViewHolder(View itemView, TaskCreateNewGroupAdapter.OnTaskCreateNewGroupAdapterClickListener listener) {
        super(itemView);
        groupCheckbox = itemView.findViewById(R.id.create_task_group_recycler_name);
        groupFullNameTextView = itemView.findViewById(R.id.create_task_group_recycler_task_fullname);
        groupLastDateTextView = itemView.findViewById(R.id.create_task_group_recycler_task_lastdate);
        groupDateTextView = itemView.findViewById(R.id.create_task_group_recycler_date_textview);
        groupDateImageButton = itemView.findViewById(R.id.create_task_group_recycler_date_imagebutton);

        groupCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onGroupCheckClick(isChecked, groupCheckbox.getText().toString());
                    }
                }
            }
        });

        groupDateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onGroupDateClick(groupCheckbox.getText().toString(), position);
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bind(String name) {
        groupCheckbox.setText(name);
    }

    static TaskCreateNewGroupViewHolder create(ViewGroup parent, TaskCreateNewGroupAdapter.OnTaskCreateNewGroupAdapterClickListener listener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.create_task_group_recycler, parent, false);
        return new TaskCreateNewGroupViewHolder(view, listener);
    }

    public TextView getGroupDateTextView() {
        return groupDateTextView;
    }

    public void setText(String text) {
        groupDateTextView.setText(text);
    }

}
