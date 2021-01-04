package com.example.shrimpscheduler.ShrimpTask;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shrimpscheduler.R;

import java.time.LocalDate;

public class ShrimpTaskEditViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameTextView;
    private final TextView executeDatetimeTextView;
    private final TextView descriptionTextView;
    private final ImageButton editButton;
    private final ImageButton deleteButton;

    private ShrimpTaskEditViewHolder(View itemView, ShrimpTaskEditAdapter.OnShrimpTaskEditClickListener listener) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.task_edit_name);
        executeDatetimeTextView = itemView.findViewById(R.id.task_edit_execute_datetime);
        descriptionTextView = itemView.findViewById(R.id.task_edit_description);

        editButton = itemView.findViewById(R.id.task_edit_edit_button);
        deleteButton = itemView.findViewById(R.id.task_edit_delete_button);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onEditButtonClick(position);
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
                        listener.onDeleteButtonClick(position);
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bind(String name, LocalDate executeTime, String description) {
        nameTextView.setText(name);
        executeDatetimeTextView.setText(executeTime.toString());
        descriptionTextView.setText(description);
    }

    public static ShrimpTaskEditViewHolder create(ViewGroup parent, ShrimpTaskEditAdapter.OnShrimpTaskEditClickListener listener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shrimp_task_edit_recycler, parent, false);
        return new ShrimpTaskEditViewHolder(view, listener);
    }
}
