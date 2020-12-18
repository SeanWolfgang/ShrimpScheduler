package com.example.shrimpscheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class ShrimpTaskCreateNew extends AppCompatActivity
    implements DatePickerDialog.OnDateSetListener {

    // implements TimePickerDialog.OnTimeSetListener
    public static final String EXTRA_REPLY1 = "REPLY1";
    public static final String EXTRA_REPLY2 = "REPLY2";
    public static final String EXTRA_REPLY3 = "REPLY3";
    public static final String EXTRA_REPLY4 = "REPLY4";

    private EditText nameEditText;
    private EditText descriptionEditText;
    private Button timeButton;
    private Button dateButton;
    private TextView dateText;

    private int[] pickedDate = new int[3];
    private boolean pickedDateBool = false;

    private TaskTemplateAdapter adapter = new TaskTemplateAdapter(new TaskTemplateAdapter.TaskTemplateDiff());
    private TaskTemplateViewModel taskTemplateViewModel;
    private ShrimpTaskViewModel shrimpTaskViewModel;
    private Spinner templateSpinner;
    private ArrayList<String> spinnerStrings = new ArrayList<>();
    private ArrayList<String> templateNameStrings = new ArrayList<>();
    private ArrayList<String> templateDescriptionStrings = new ArrayList<>();
    private int selectedSpinnerItem;
    private int numberOfMatchedItems;
    private int numberOfItems;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        nameEditText = findViewById(R.id.task_name);
        descriptionEditText = findViewById(R.id.description);
        // timeButton = findViewById(R.id.pick_time_button);
        dateButton = findViewById(R.id.pick_date_button);
        dateText = findViewById(R.id.startDate);
        templateSpinner = findViewById(R.id.parent_name);
        final Button saveButton = findViewById(R.id.save_user_button);

        taskTemplateViewModel = new ViewModelProvider(this).get(TaskTemplateViewModel.class);
        shrimpTaskViewModel = new ViewModelProvider(this).get(ShrimpTaskViewModel.class);

        spinnerStrings.add("Select Template");
        templateNameStrings.add("first empty");
        templateDescriptionStrings.add("first empty");

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                shrimpTaskViewModel.setFilter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        taskTemplateViewModel.getAllTaskTemplates().observe(this, taskTemplates -> {
            // Update the cached copy of the words in the adapter.

            for (TaskTemplate taskTemplate : taskTemplates) {
                spinnerStrings.add(taskTemplate.getName());
                templateNameStrings.add(taskTemplate.getDefaultName());
                templateDescriptionStrings.add(taskTemplate.getDefaultDescription());
            }
        });

        shrimpTaskViewModel.getCountShrimpTask().observe(this, shrimpTaskNameCountDB -> {
            // Update the cached copy of the words in the adapter.
            numberOfItems = shrimpTaskNameCountDB;
        });

        shrimpTaskViewModel.getShrimpTasksNameMatch().observe(this, shrimpTaskNameCountDBMatch -> {
            // Update the cached copy of the words in the adapter.
            numberOfMatchedItems = shrimpTaskNameCountDBMatch;
        });

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinnerStrings);
        spinnerAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        templateSpinner.setAdapter(spinnerAdapter);

        templateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinnerItem = position;
                if (selectedSpinnerItem == 0) {
                    /*
                    Toast.makeText(
                            getApplicationContext(),
                            "AHHHHHHHHHH",
                            Toast.LENGTH_SHORT).show();

                     */
                } else {
                    /*
                    Toast.makeText(
                            getApplicationContext(),
                            Integer.toString(position),
                            Toast.LENGTH_SHORT).show();

                     */
                    nameEditText.setText(templateNameStrings.get(position));
                    descriptionEditText.setText(templateDescriptionStrings.get(position));
                }


                /*
                Toast.makeText(
                        getApplicationContext(),
                        "Total Items: " + Integer.toString(numberOfItems),
                        Toast.LENGTH_SHORT).show();

                Toast.makeText(
                        getApplicationContext(),
                        "Matched Items: " + Integer.toString(numberOfMatchedItems),
                        Toast.LENGTH_SHORT).show();
                */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            if (TextUtils.isEmpty(nameEditText.getText())) {
                //setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(
                        getApplicationContext(),
                        R.string.name_empty,
                        Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(descriptionEditText.getText())) {
                //setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(
                        getApplicationContext(),
                        R.string.description_empty,
                        Toast.LENGTH_LONG).show();
            } else if (!pickedDateBool) {
                //setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(
                        getApplicationContext(),
                        R.string.date_empty,
                        Toast.LENGTH_LONG).show();
            } else if (selectedSpinnerItem == 0) {
                //setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(
                        getApplicationContext(),
                        R.string.no_template_selected,
                        Toast.LENGTH_LONG).show();
            } else if (numberOfMatchedItems > 0) {
                //setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(
                        getApplicationContext(),
                        R.string.repeat_name,
                        Toast.LENGTH_LONG).show();
            } else {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY1, name);
                replyIntent.putExtra(EXTRA_REPLY2, pickedDate);
                replyIntent.putExtra(EXTRA_REPLY3, description);
                replyIntent.putExtra(EXTRA_REPLY4, selectedSpinnerItem - 1);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }

    /*
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }
    */

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /*
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
    */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        LocalDate c = LocalDate.of(year, month + 1, day);

        // dateText.setText("Y: " + year + " M: " + month + " D: " + day);

        pickedDate[0] = year;
        pickedDate[1] = month;
        pickedDate[2] = day;

        pickedDateBool = true;

        dateText = findViewById(R.id.startDate);

        dateText.setText(c.toString());
    }

}
