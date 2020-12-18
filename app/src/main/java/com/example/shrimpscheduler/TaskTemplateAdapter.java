package com.example.shrimpscheduler;

import android.os.Build;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskTemplateAdapter extends ListAdapter<TaskTemplate, TaskTemplateViewHolder> {
    private OnTaskTemplateClickListener mListener;

    public interface OnTaskTemplateClickListener {
        void onTaskTemplateEditClick(int position);
    }

    public void setOnItemClickListener(TaskTemplateAdapter.OnTaskTemplateClickListener listener) {
        mListener = listener;
    }

    public TaskTemplateAdapter(@NonNull DiffUtil.ItemCallback<TaskTemplate> diffCallback) {
        super(diffCallback);
        this.setHasStableIds(true);
    }

    @Override
    public TaskTemplateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return TaskTemplateViewHolder.create(parent, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(TaskTemplateViewHolder holder, int position) {
        TaskTemplate current = getItem(position);
        holder.bind(current.getName(), current.getDefaultDescription(), current.isRepeat(), current.getDaysBetweenRepeat());
    }

    static class TaskTemplateDiff extends DiffUtil.ItemCallback<TaskTemplate> {

        @Override
        public boolean areItemsTheSame(@NonNull TaskTemplate oldItem, @NonNull TaskTemplate newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskTemplate oldItem, @NonNull TaskTemplate newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }

    public TaskTemplate getTaskTemplate (int position) { return getItem(position); }
}