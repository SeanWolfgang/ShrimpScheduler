package com.example.shrimpscheduler.CreateTask;
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
public class TaskCreateNewSingleDatepickerFragment extends Fragment {
    private ImageButton taskCreateNewSingleImagebutton;
    private TextView taskCreateNewSingleTextview;

    private LocalDate pickedDate = LocalDate.now();

    private TaskCreateNewSingleDatepickerFragment.TaskCreateNewSingleDatepickerFragmentListener listener;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_task_single_datepicker, container, false);

        taskCreateNewSingleImagebutton = view.findViewById(R.id.create_task_single_datepicker_imagebutton);
        taskCreateNewSingleTextview = view.findViewById(R.id.create_task_single_datepicker_textview);

        taskCreateNewSingleImagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        return view;
    }

    public void showDatePickerDialog(View v) {
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Do something with the date chosen by the user
                        pickedDate = LocalDate.of(year, month + 1, dayOfMonth);
                        taskCreateNewSingleTextview.setText("" + pickedDate.toString());
                        listener.selectedDate(pickedDate);
                    }
                }, pickedDate.getYear(), pickedDate.getMonthValue() - 1, pickedDate.getDayOfMonth());

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public  interface TaskCreateNewSingleDatepickerFragmentListener {
        void selectedDate(LocalDate pickedDate);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (TaskCreateNewSingleDatepickerFragment.TaskCreateNewSingleDatepickerFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement TaskCreateNewSingleDatepickerFragmentListener");
        }
    }

    public void setTextRed() {
        taskCreateNewSingleTextview.setTextColor(getResources().getColor(R.color.textInvalid));
    }

    public void unsetTextRed() {
        taskCreateNewSingleTextview.setTextColor(getResources().getColor(R.color.textValid));
    }
}