package com.example.shrimpscheduler;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DatabaseManagementFragment extends Fragment {
    private Button deleteAllButton;
    private Button deleteFutureButton;
    private Button deleteTemplatesButton;

    private DataBaseManagementFragmentListener listener;

    public interface DataBaseManagementFragmentListener{
        void onDBManageButtonClicked(String buttonTitle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.database_management_fragment, container, false);

        deleteAllButton = view.findViewById(R.id.delete_everything);
        deleteFutureButton = view.findViewById(R.id.delete_future);
        deleteTemplatesButton = view.findViewById(R.id.delete_templates);

        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonTitle = "DeleteAll";
                listener.onDBManageButtonClicked(buttonTitle);
            }
        });

        deleteFutureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonTitle = "DeleteFuture";
                listener.onDBManageButtonClicked(buttonTitle);
            }
        });

        deleteTemplatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonTitle = "DeleteTemplates";
                listener.onDBManageButtonClicked(buttonTitle);
            }
        });

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DataBaseManagementFragmentListener) {
            listener = (DataBaseManagementFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DataBaseManagementFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
