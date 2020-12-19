package com.example.shrimpscheduler;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import java.time.LocalDate;
import java.util.Calendar;

public class ButtonRibbonFragment extends Fragment {

    public static final int NEW_SINGLE_SCREEN_REQUEST_CODE = 2;
    public static final String MANAGE_TASK_BUTTON_LABEL = "ManageTask";
    public static final int MANAGE_TASK_BUTTON_CODE = 0;

    private ButtonRibbonFragmentListener listener;

    private Button viewTodayTasksButton;
    private Button viewAllTasksButton;
    private Button viewDateTasksButton;
    private Button viewTemplatesButton;
    private Button manageTasksButton;
    private Button manageTemplatesButton;
    private Button newTaskTemplateButton;
    private Button dataHubButton;
    private LocalDate pickedDate;

    public interface ButtonRibbonFragmentListener{
        void onButtonClicked(String buttonTitle);
        void onDateTasksButtonClicked(LocalDate date);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.button_ribbon, container, false);

        viewTodayTasksButton = (Button) view.findViewById(R.id.view_today_tasks);
        viewAllTasksButton = (Button) view.findViewById(R.id.view__all_tasks);
        viewDateTasksButton = (Button) view.findViewById(R.id.view__date_tasks);
        viewTemplatesButton = (Button) view.findViewById(R.id.view_templates);
        manageTasksButton = (Button) view.findViewById(R.id.manage_tasks);
        manageTemplatesButton = (Button) view.findViewById(R.id.manage_templates);
        newTaskTemplateButton = (Button) view.findViewById(R.id.new_template_button);
        dataHubButton = (Button) view.findViewById(R.id.data_hub);

        viewTodayTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonTitle = "ViewTodayTasks";
                listener.onButtonClicked(buttonTitle);
            }
        });

        viewAllTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonTitle = "ViewAllTasks";
                listener.onButtonClicked(buttonTitle);
            }
        });

        viewDateTasksButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                pickedDate = LocalDate.of(mYear, mMonth, mDay);

                // Create a new instance of DatePickerDialog and return it
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Do something with the date chosen by the user

                                pickedDate = LocalDate.of(year, month + 1, dayOfMonth);

                                listener.onDateTasksButtonClicked(pickedDate);
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

                String buttonTitle = "ViewAllTasks";
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
