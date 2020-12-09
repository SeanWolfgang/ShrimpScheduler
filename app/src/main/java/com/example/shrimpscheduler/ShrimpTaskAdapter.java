package com.example.shrimpscheduler;

import android.os.Build;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShrimpTaskAdapter extends ListAdapter<ShrimpTask, ShrimpTaskViewHolder> {

    public ShrimpTaskAdapter(@NonNull DiffUtil.ItemCallback<ShrimpTask> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ShrimpTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ShrimpTaskViewHolder.create(parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ShrimpTaskViewHolder holder, int position) {
        ShrimpTask current = getItem(position);
        holder.bind(current.getName(), current.getExecuteTime(), current.getDescription());
    }

    static class ShrimpTaskDiff extends DiffUtil.ItemCallback<ShrimpTask> {

        @Override
        public boolean areItemsTheSame(@NonNull ShrimpTask oldItem, @NonNull ShrimpTask newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShrimpTask oldItem, @NonNull ShrimpTask newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }
}