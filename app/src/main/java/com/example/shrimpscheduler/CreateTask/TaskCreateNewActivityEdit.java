package com.example.shrimpscheduler.CreateTask;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shrimpscheduler.Group.GroupViewModel;
import com.example.shrimpscheduler.MainActivity;
import com.example.shrimpscheduler.MainFragments.EmptyFragment;
import com.example.shrimpscheduler.MainFragments.OkCancelButtonFooterFragment;
import com.example.shrimpscheduler.R;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTask;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskViewModel;
import com.example.shrimpscheduler.Template.TaskTemplateViewModel;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class TaskCreateNewActivityEdit  extends AppCompatActivity  implements TaskCreateNewFragment.TaskCreateNewFragmentListener,
        TaskCreateNewSingleDatepickerFragment.TaskCreateNewSingleDatepickerFragmentListener,
        OkCancelButtonFooterFragment.OkCancelButtonFooterFragmentListener {
    public FragmentManager fragmentManager;

    // Declare models
    public ShrimpTaskViewModel shrimpTaskViewModel;

    private String passedTemplate;
    private String passedName;
    private String passedDescription;
    private int[] passedDate;
    private LocalDate passedDateLocalDate;
    private int passedID;

    private boolean modifyAllFuture;
    private LocalDate newDate;
    private ShrimpTask modifyTask;
    private ArrayList<ShrimpTask> nameMatchTasks = new ArrayList<>();
    private long dateDelta;

    // Create task fragments
    private TaskCreateNewFragment taskEditCreateNewFragment;
    private TaskCreateNewSingleDatepickerFragment taskEditCreateNewSingleDatepickerFragment;
    private OkCancelButtonFooterFragment okCancelButtonFooterFragment;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task_activity);

        fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();

        passedTemplate = intent.getStringExtra(MainActivity.EXTRA_1);
        passedName = intent.getStringExtra(MainActivity.EXTRA_2);
        passedDescription = intent.getStringExtra(MainActivity.EXTRA_3);
        passedDate = intent.getIntArrayExtra(MainActivity.EXTRA_4);
        passedID = intent.getIntExtra(MainActivity.EXTRA_5, 0);

        passedDateLocalDate = LocalDate.of(passedDate[0], passedDate[1], passedDate[2]);
        newDate = passedDateLocalDate;

        // Assign all models
        shrimpTaskViewModel = new ViewModelProvider(this).get(ShrimpTaskViewModel.class);

        taskEditCreateNewFragment = new TaskCreateNewFragment();
        taskEditCreateNewSingleDatepickerFragment = new TaskCreateNewSingleDatepickerFragment();
        okCancelButtonFooterFragment = new OkCancelButtonFooterFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.create_task_activity_0, taskEditCreateNewFragment)
                .replace(R.id.create_task_activity_1, taskEditCreateNewSingleDatepickerFragment)
                .replace(R.id.create_task_activity_2, okCancelButtonFooterFragment)
                .commit();

        shrimpTaskViewModel.setIDFilter(passedID);
        shrimpTaskViewModel.setNameFilter(passedName);

        shrimpTaskViewModel.getSelectShrimpTaskID().observe(this, selectedShrimpTask -> {
            // Update the cached copy of the words in the adapter.
            modifyTask = selectedShrimpTask;
        });

        shrimpTaskViewModel.getFutureShrimpTaskNameSpecified().observe(this, futureShrimpTasks -> {
            // Update the cached copy of the words in the adapter.
            for (ShrimpTask matchedTask : futureShrimpTasks) {
                nameMatchTasks.add(matchedTask);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api =  Build.VERSION_CODES.O)
            @Override
            public void run() {
                configureFragmentsForEdit();
            }
        }, 100);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void confirmButtonListener() {
        // Doing it's own thing
        boolean valid = true;

        if (passedDescription.trim().isEmpty()) {
            displayTextScreen(getResources().getString(R.string.edit_name_description_empty));
            valid = false;
        }

        if (valid) {
            if (modifyAllFuture) {
                for (ShrimpTask eachTask : nameMatchTasks) {
                    if (eachTask.getExecuteTime().compareTo(passedDateLocalDate) >= 0) {
                        eachTask.setDescription(passedDescription);
                        eachTask.setExecuteTime(eachTask.getExecuteTime().plusDays(dateDelta));

                        shrimpTaskViewModel.updateShrimpTask(eachTask);
                    }
                }
                finish();
            } else {
                modifyTask.setDescription(passedDescription);
                modifyTask.setExecuteTime(newDate);

                shrimpTaskViewModel.updateShrimpTask(modifyTask);
                finish();
            }

        }
    }

    @Override
    public void cancelButtonListener() {
        finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void configureFragmentsForEdit() {
        taskEditCreateNewFragment.configureForEdit(passedName, passedDescription);
        taskEditCreateNewSingleDatepickerFragment.configureForEdit(passedDateLocalDate, passedDateLocalDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void selectedDate(LocalDate pickedDate) {
        newDate = pickedDate;
        dateDelta = DAYS.between(passedDateLocalDate, newDate);

        //View datePickerButtonView = taskEditCreateNewSingleDatepickerFragment.getView();
        //TextView newDate = datePickerButtonView.findViewById(R.id.create_task_single_datepicker_textview);
        //String newString = getResources().getString(R.string.create_task_single_datepicker_edit_date_new) + " " + newDate.toString();
        //newDate.setText(newString);
    }

    @Override
    public void nameChanged(String name) {
        // Not applicable since name will be disabled
    }

    @Override
    public void descriptionChanged(String description) {
        passedDescription = description;
    }

    @Override
    public void templateChanged(String templateName, String name, String description, boolean repeat, int daysBetweenRepeat) {

    }

    @Override
    public void checkedStateChange(boolean checkedState) {
        modifyAllFuture = checkedState;
    }

    private void displayTextScreen(String inputText) {
        Toast.makeText(
                getApplicationContext(),
                inputText,
                Toast.LENGTH_LONG).show();
    }

}