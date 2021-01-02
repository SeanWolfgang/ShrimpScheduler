package com.example.shrimpscheduler.Group;

import android.os.Build;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.shrimpscheduler.MainFragments.GroupViewHolder;

public class GroupAdapter extends ListAdapter<Group, GroupViewHolder> {
    private OnGroupTaskClickListener mListener;

    public interface OnGroupTaskClickListener {
        void onGroupDeleteClick(int position);
    }

    public void setOnItemClickListener(GroupAdapter.OnGroupTaskClickListener listener) {
        mListener = listener;
    }

    public GroupAdapter(@NonNull DiffUtil.ItemCallback<Group> diffCallback) {
        super(diffCallback);
        this.setHasStableIds(true);
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return GroupViewHolder.create(parent, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        Group current = getItem(position);
        holder.bind(current.getName());
    }

    public static class GroupDiff extends DiffUtil.ItemCallback<Group> {

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
}