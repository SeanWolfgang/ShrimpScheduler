package com.example.shrimpscheduler.MainFragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.shrimpscheduler.R;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskDatePickingFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private TaskDatePickingListener listener;

    private ImageButton calendarImageButton;
    private TextView dateTextView;
    private ImageButton switchModeImageButton;
    private ImageButton newImageButton;

    private LocalDate pickedDate = LocalDate.now();
    private Boolean editMode = false;

    public interface TaskDatePickingListener{
        void calendarButtonListener(LocalDate pickedDate);
        void switchModeListener(boolean isEditMode);
        void newTaskListener();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shrimp_task_date_picking_fragment, container, false);

        calendarImageButton = v.findViewById(R.id.task_date_picking_date_imagebutton);
        dateTextView = v.findViewById(R.id.task_date_picking_date_textview);
        switchModeImageButton = v.findViewById(R.id.task_date_picking_switch_mode_button);
        newImageButton = v.findViewById(R.id.task_date_picking_new_button);

        dateTextView.setText("Displaying: " + pickedDate.toString());

        calendarImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        switchModeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMode();
                listener.switchModeListener(editMode);
            }
        });
        newImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.newTaskListener();
            }
        });

        return v;
    }

    public void showDatePickerDialog(View v) {
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Do something with the date chosen by the user
                        pickedDate = LocalDate.of(year, month + 1, dayOfMonth);
                        dateTextView.setText("Displaying: " + pickedDate.toString());
                        listener.calendarButtonListener(pickedDate);
                    }
                }, pickedDate.getYear(), pickedDate.getMonthValue() - 1, pickedDate.getDayOfMonth());

        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        pickedDate = LocalDate.of(year, month + 1, day);
        dateTextView.setText(pickedDate.toString());
    }

    public void switchMode() {
        editMode = !editMode;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TaskDatePickingFragment.TaskDatePickingListener) {
            listener = (TaskDatePickingFragment.TaskDatePickingListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement TaskDatePickingListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}