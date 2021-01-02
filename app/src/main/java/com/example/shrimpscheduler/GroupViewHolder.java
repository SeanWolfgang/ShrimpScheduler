package com.example.shrimpscheduler;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shrimpscheduler.Group.GroupAdapter;

public class GroupViewHolder extends RecyclerView.ViewHolder {
    private final TextView groupName;
    private final Button deleteGroupButton;

    private GroupViewHolder(View itemView, GroupAdapter.OnGroupTaskClickListener listener) {
        super(itemView);
        groupName = itemView.findViewById(R.id.group_name_recycler);
        deleteGroupButton = itemView.findViewById(R.id.delete_group_button);

        deleteGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onGroupDeleteClick(position);
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bind(String name) {
        groupName.setText(name);
    }

    public static GroupViewHolder create(ViewGroup parent, GroupAdapter.OnGroupTaskClickListener listener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_recycler, parent, false);
        return new GroupViewHolder(view, listener);
    }
}
