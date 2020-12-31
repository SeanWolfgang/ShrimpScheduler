package com.example.shrimpscheduler;

import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.time.LocalDate;

public class TaskCreateNewGroupAdapter extends ListAdapter<Group, TaskCreateNewGroupViewHolder> {
    private TaskCreateNewGroupAdapter.OnTaskCreateNewGroupAdapterClickListener mListener;

    public interface OnTaskCreateNewGroupAdapterClickListener {
        void onGroupDateClick(String name, int position);
        void onGroupCheckClick(boolean checked, String name);
    }

    public void setOnItemClickListener(TaskCreateNewGroupAdapter.OnTaskCreateNewGroupAdapterClickListener listener) {
        mListener = listener;
    }

    public TaskCreateNewGroupAdapter(@NonNull DiffUtil.ItemCallback<Group> diffCallback) {
        super(diffCallback);
        this.setHasStableIds(true);
    }

    @Override
    public TaskCreateNewGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return TaskCreateNewGroupViewHolder.create(parent, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(TaskCreateNewGroupViewHolder holder, int position) {
        Group current = getItem(position);
        holder.bind(current.getName());
    }

    static class GroupDiff extends DiffUtil.ItemCallback<Group> {

        @Override
        public boolean areItemsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }

    public Group getGroup (int position) { return getItem(position); }

    @Override
    public long getItemId(int position) {
        // requires static value, it means need to keep the same value
        // even if the item position has been changed.
        return getGroup(position).getId();
    }
}