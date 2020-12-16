package com.example.shrimpscheduler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ButtonRibbonFragment extends Fragment {
    public static final int NEW_SINGLE_SCREEN_REQUEST_CODE = 2;
    public static final String MANAGE_TASK_BUTTON_LABEL = "ManageTask";
    public static final int MANAGE_TASK_BUTTON_CODE = 0;

    private ButtonRibbonFragmentListener listener;

    private Button viewTasksButton;
    private Button viewTemplatesButton;
    private Button manageTasksButton;
    private Button manageTemplatesButton;
    private Button newTaskTemplateButton;
    private Button dataHubButton;

    public interface ButtonRibbonFragmentListener{
        void onButtonClicked(String buttonTitle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.button_ribbon, container, false);

        viewTasksButton = (Button) view.findViewById(R.id.view_tasks);
        viewTemplatesButton = (Button) view.findViewById(R.id.view_templates);
        manageTasksButton = (Button) view.findViewById(R.id.manage_tasks);
        manageTemplatesButton = (Button) view.findViewById(R.id.manage_templates);
        newTaskTemplateButton = (Button) view.findViewById(R.id.new_template_button);
        dataHubButton = (Button) view.findViewById(R.id.data_hub);

        viewTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonTitle = "ViewTasks";
                listener.onButtonClicked(buttonTitle);
            }
        });

        viewTemplatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonTitle = "ViewTemplates";
                listener.onButtonClicked(buttonTitle);
            }
        });

        manageTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonTitle = "ManageTasks";
                listener.onButtonClicked(buttonTitle);
            }
        });

        manageTemplatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonTitle = "ManageTemplates";
                listener.onButtonClicked(buttonTitle);
            }
        });

        newTaskTemplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonTitle = "NewTaskTemplate";
                listener.onButtonClicked(buttonTitle);
            }
        });

        dataHubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonTitle = "DataHub";
                listener.onButtonClicked(buttonTitle);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ButtonRibbonFragmentListener) {
            listener = (ButtonRibbonFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ButtonRibbonFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
