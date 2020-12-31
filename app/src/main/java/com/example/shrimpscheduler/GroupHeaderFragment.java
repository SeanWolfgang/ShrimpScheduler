package com.example.shrimpscheduler;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GroupHeaderFragment extends Fragment {
    private GroupHeaderFragment.GroupHeaderFragmentListener listener;

    private ImageButton newImageButton;

    public interface GroupHeaderFragmentListener{
        void newGroupListener();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.group_header_fragment, container, false);

        newImageButton = v.findViewById(R.id.group_header_imagebutton);

        newImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.newGroupListener();
            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GroupHeaderFragment.GroupHeaderFragmentListener) {
            listener = (GroupHeaderFragment.GroupHeaderFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement GroupHeaderFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}