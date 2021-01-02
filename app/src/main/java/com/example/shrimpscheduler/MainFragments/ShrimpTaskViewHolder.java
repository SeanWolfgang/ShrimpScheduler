package com.example.shrimpscheduler.MainFragments;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shrimpscheduler.R;
import com.example.shrimpscheduler.ShrimpTaskPack.ShrimpTaskAdapter;

import java.time.LocalDate;

public class ShrimpTaskViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameTextView;
    private final TextView executeDatetimeTextView;
    private final TextView descriptionTextView;
    private final Button doneButton;
    private final Button notDoneButton;

    private ShrimpTaskViewHolder(View itemView, ShrimpTaskAdapter.OnShrimpTaskClickListener listener) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.task_name);
        executeDatetimeTextView = itemView.findViewById(R.id.execute_datetime);
        descriptionTextView = itemView.findViewById(R.id.description);

        doneButton = itemView.findViewById(R.id.done_button);
        notDoneButton = itemView.findViewById(R.id.not_done_button);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDoneButtonClick(position);
                    }
                }
            }
        });

        notDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onNotDoneButtonClick(position);
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bind(String name, LocalDate executeTime, String description, boolean done, boolean disposed) {
        nameTextView.setText(name);
        executeDatetimeTextView.setText(executeTime.toString());
        descriptionTextView.setText(description);
        if (disposed) {
            if (done) {
                notDoneButton.setBackgroundColor(notDoneButton.getContext().getColor(R.color.buttonUnClickedBack));
                notDoneButton.setTextColor(notDoneButton.getContext().getColor(R.color.buttonUnClickedText));
                doneButton.setBackgroundColor(doneButton.getContext().getColor(R.color.doneButtonClickedBack));
                doneButton.setTextColor(doneButton.getContext().getColor(R.color.doneButtonClickedText));
            } else {
                doneButton.setBackgroundColor(doneButton.getContext().getColor(R.color.buttonUnClickedBack));
                doneButton.setTextColor(doneButton.getContext().getColor(R.color.buttonUnClickedText));
                notDoneButton.setBackgroundColor(notDoneButton.getContext().getColor(R.color.notDoneButtonClickedBack));
                notDoneButton.setTextColor(notDoneButton.getContext().getColor(R.color.notDoneButtonClickedText));
            }
        } else {
            notDoneButton.setBackgroundColor(notDoneButton.getContext().getColor(R.color.notDoneButtonClickedBack));
            notDoneButton.setTextColor(notDoneButton.getContext().getColor(R.color.notDoneButtonClickedText));
            doneButton.setBackgroundColor(doneButton.getContext().getColor(R.color.doneButtonClickedBack));
            doneButton.setTextColor(doneButton.getContext().getColor(R.color.doneButtonClickedText));
        }
    }

    public static ShrimpTaskViewHolder create(ViewGroup parent, ShrimpTaskAdapter.OnShrimpTaskClickListener listener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shrimp_task_recycler, parent, false);
        return new ShrimpTaskViewHolder(view, listener);
    }
}
