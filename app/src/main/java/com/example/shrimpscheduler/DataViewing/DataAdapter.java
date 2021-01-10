package com.example.shrimpscheduler.DataViewing;

import android.os.Build;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class DataAdapter extends ListAdapter<Data, DataViewHolder> {
    private OnDataClickTaskListener mListener;

    public interface OnDataClickTaskListener {
        void onTaskClick(int position);
    }

    public void setOnDataClickTaskListener(OnDataClickTaskListener listener) {
        mListener = listener;
    }

    public DataAdapter(@NonNull DiffUtil.ItemCallback<Data> diffCallback) {
        super(diffCallback);
        this.setHasStableIds(true);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DataViewHolder.create(parent, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        Data current = getItem(position);
        holder.bind(current.getTitle(), current.getNotDisposedCount(), current.getDoneCount(), current.getNotDoneCount());
    }

    public static class DataDiff extends DiffUtil.ItemCallback<Data> {

        @Override
        public boolean areItemsTheSame(@NonNull Data oldItem, @NonNull Data newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Data oldItem, @NonNull Data newItem) {
            boolean same = true;

            if (true || false) {

            }

            if (!oldItem.getTitle().equals(newItem.getTitle()) ||
                    (oldItem.getNotDisposedCount() != newItem.getNotDisposedCount()) ||
                    (oldItem.getDoneCount() != newItem.getDoneCount()) ||
                    (oldItem.getNotDoneCount() != newItem.getNotDoneCount())){
                same = false;
            }

            return same;
        }
    }
}