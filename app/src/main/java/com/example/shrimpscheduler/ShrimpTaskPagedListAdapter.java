package com.example.shrimpscheduler;

import android.os.Build;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ShrimpTaskPagedListAdapter extends PagedListAdapter<ShrimpTask, ShrimpTaskPagedListViewHolder> {
    private OnShrimpTaskPagedListClickListener mListener;

    public interface OnShrimpTaskPagedListClickListener {
        void onDoneButtonClick(int position);
        void onNotDoneButtonClick(int position);
    }

    public void setOnShrimpTaskPagedListClickListener(OnShrimpTaskPagedListClickListener listener) {
        mListener = listener;
    }


    public ShrimpTaskPagedListAdapter(@NonNull DiffUtil.ItemCallback<ShrimpTask> diffCallback) {
        super(diffCallback);
        //this.setHasStableIds(true);
    }

    @Override
    public ShrimpTaskPagedListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ShrimpTaskPagedListViewHolder.create(parent, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ShrimpTaskPagedListViewHolder holder, int position) {
        ShrimpTask current = getItem(position);
        if (current != null) {
            holder.bind(current.getName(), current.getExecuteTime(), current.getDescription(), current.isDone(), current.isDisposed());
        } else {

        }
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