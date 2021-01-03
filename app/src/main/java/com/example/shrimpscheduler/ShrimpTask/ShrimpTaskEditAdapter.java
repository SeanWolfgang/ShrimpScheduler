package com.example.shrimpscheduler.ShrimpTask;

import android.os.Build;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.shrimpscheduler.MainFragments.ShrimpTaskEditViewHolder;
import com.example.shrimpscheduler.MainFragments.ShrimpTaskViewHolder;

public class ShrimpTaskEditAdapter extends ListAdapter<ShrimpTask, ShrimpTaskEditViewHolder> {
    private ShrimpTaskEditAdapter.OnShrimpTaskEditClickListener mListener;

    public interface OnShrimpTaskEditClickListener  {
        void onEditButtonClick(int position);
        void onDeleteButtonClick(int position);
    }

    public void setOnShrimpTaskEditClickListener(ShrimpTaskEditAdapter.OnShrimpTaskEditClickListener listener) {
        mListener = listener;
    }

    public ShrimpTaskEditAdapter(@NonNull DiffUtil.ItemCallback<ShrimpTask> diffCallback) {
        super(diffCallback);
        this.setHasStableIds(true);
    }

    @Override
    public ShrimpTaskEditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ShrimpTaskEditViewHolder.create(parent, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ShrimpTaskEditViewHolder holder, int position) {
        ShrimpTask current = getItem(position);
        holder.bind(current.getName(), current.getExecuteTime(), current.getDescription());
    }

    public static class ShrimpTaskDiff extends DiffUtil.ItemCallback<ShrimpTask> {

        @Override
        public boolean areItemsTheSame(@NonNull ShrimpTask oldItem, @NonNull ShrimpTask newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShrimpTask oldItem, @NonNull ShrimpTask newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }

    public int getShrimpTaskID(int position) {
        return getItem(position).getId();
    }

    public ShrimpTask getShrimpTask (int position) {
        return getItem(position);
    }
}