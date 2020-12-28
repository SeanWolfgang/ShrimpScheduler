package com.example.shrimpscheduler;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskDatePicking extends Fragment implements DatePickerDialog.OnDateSetListener {
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
        View v = inflater.inflate(R.layout.task_date_picking_fragment, container, false);

        calendarImageButton = v.findViewById(R.id.task_date_picking_date_imagebutton);
        dateTextView = v.findViewById(R.id.task_date_picking_date_textview);
        switchModeImageButton = v.findViewById(R.id.task_date_picking_switch_mode_button);
        newImageButton = v.findViewById(R.id.task_date_picking_new_button);

        dateTextView.setText(pickedDate.toString());

        calendarImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
                listener.calendarButtonListener(pickedDate);
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
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getParentFragmentManager(), "datePicker");
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
        if (context instanceof TaskDatePicking.TaskDatePickingListener) {
            listener = (TaskDatePicking.TaskDatePickingListener) context;
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