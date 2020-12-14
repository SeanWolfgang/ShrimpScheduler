package com.example.shrimpscheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class CreateTask extends AppCompatActivity
        implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_REPLY1 = "REPLY1";
    public static final String EXTRA_REPLY2 = "REPLY2";

    private EditText nameEditText;
    private EditText descriptionEditText;
    private Button timeButton;
    private Button dateButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        nameEditText = findViewById(R.id.task_name);
        descriptionEditText = findViewById(R.id.description);
        timeButton = findViewById(R.id.pick_time_button);
        dateButton = findViewById(R.id.pick_date_button);

        final Button saveButton = findViewById(R.id.save_user_button);

        saveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(nameEditText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else if (TextUtils.isEmpty(descriptionEditText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY1, name);
                replyIntent.putExtra(EXTRA_REPLY2, description);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        timeButton = findViewById(R.id.pick_time_button);
        if (DateFormat.is24HourFormat(this)) {
            timeButton.setText(hourOfDay + ":" + minute);
        } else {
            String AM_PM;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            timeButton.setText(hourOfDay % 12 + ":" + minute + " " + AM_PM);
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        String currentDateString = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(c.getTime());

        dateButton = findViewById(R.id.pick_date_button);

        dateButton.setText(currentDateString);
    }
}
