package com.example.shrimpscheduler.CreateTask;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shrimpscheduler.Group.Group;
import com.example.shrimpscheduler.MainFragments.EmptyFragment;
import com.example.shrimpscheduler.Group.GroupViewModel;
import com.example.shrimpscheduler.MainFragments.OkCancelButtonFooterFragment;
import com.example.shrimpscheduler.R;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTask;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskViewModel;
import com.example.shrimpscheduler.Template.TaskTemplateViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static java.time.temporal.ChronoUnit.DAYS;

public class TaskCreateNewActivity extends AppCompatActivity
        implements TaskCreateNewFragment.TaskCreateNewFragmentListener,
        TaskCreateNewSingleDatepickerFragment.TaskCreateNewSingleDatepickerFragmentListener,
        OkCancelButtonFooterFragment.OkCancelButtonFooterFragmentListener {

    public FragmentManager fragmentManager;
    public boolean groupMake = false;

    // Declare models
    public ShrimpTaskViewModel shrimpTaskViewModel;
    public TaskTemplateViewModel taskTemplateViewModel;
    public GroupViewModel groupViewModel;

    /*
    View fragments
     */

    // Empty fragment
    public EmptyFragment emptyFragment0;
    public EmptyFragment emptyFragment1;
    public EmptyFragment emptyFragment2;
    public EmptyFragment emptyFragment3;

    // Create task fragments
    private TaskCreateNewFragment taskCreateNewFragment;
    private TaskCreateNewGroupRecyclerFragment taskCreateNewGroupRecyclerFragment;
    private TaskCreateNewSingleDatepickerFragment taskCreateNewSingleDatepickerFragment;
    private OkCancelButtonFooterFragment okCancelButtonFooterFragment;
    private LinearLayoutManager linearLayoutManager;

    private boolean nameValid = false;
    private boolean singleDateValid = false;
    private boolean groupValid = false;

    private int yearsAdded = 2;

    private ArrayList<String> distinctShrimpTaskNames = new ArrayList<>();
    private ArrayList<LocalDate> lastExecuteDate = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> selectedGroups = new ArrayList<>();
    private String selectedTemplate;
    private boolean templateRepeat = false;
    private int templateInterval = 0;
    private String singleName;
    private LocalDate singleDate;
    private String taskDescription = "";
    private HashMap<String, LocalDate> groupDates = new HashMap<String, LocalDate>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task_activity);

        fragmentManager = getSupportFragmentManager();

        // Assign all models
        shrimpTaskViewModel = new ViewModelProvider(this).get(ShrimpTaskViewModel.class);
        taskTemplateViewModel = new ViewModelProvider(this).get(TaskTemplateViewModel.class);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        emptyFragment0 = new EmptyFragment();
        emptyFragment1 = new EmptyFragment();
        emptyFragment2 = new EmptyFragment();
        emptyFragment3 = new EmptyFragment();

        taskCreateNewFragment = new TaskCreateNewFragment();
        taskCreateNewGroupRecyclerFragment = new TaskCreateNewGroupRecyclerFragment();
        taskCreateNewSingleDatepickerFragment = new TaskCreateNewSingleDatepickerFragment();
        okCancelButtonFooterFragment = new OkCancelButtonFooterFragment();

        linearLayoutManager = taskCreateNewGroupRecyclerFragment.getLayoutManager();
        selectedTemplate = getResources().getString(R.string.empty_template_string);

        shrimpTaskViewModel.getDistinctShrimpTaskNames().observe(this, distinctShrimpNames -> {
            // Update the cached copy of the words in the adapter.
            for (String name : distinctShrimpNames) {
                distinctShrimpTaskNames.add(name);
            }
        });

        shrimpTaskViewModel.getLastExecuteDate().observe(this, lastExecuteDateList -> {
            // Update the cached copy of the words in the adapter.
            for (LocalDate date : lastExecuteDateList) {
                lastExecuteDate.add(date);
            }
        });

        taskCreateNewGroupRecyclerFragment.getAdapter().setOnItemClickListener(new TaskCreateNewGroupAdapter.OnTaskCreateNewGroupAdapterClickListener() {
            @Override
            public void onGroupDateClick(String name, int position) {
                TextView dateTextView = (TextView) taskCreateNewGroupRecyclerFragment.getRecyclerView().findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.create_task_group_recycler_date_textview);

                if (groupDates.containsKey(name)) {
                    showDatePickerDialog(name, groupDates.get(name), dateTextView);
                } else {
                    showDatePickerDialog(name, LocalDate.now(), dateTextView);
                }
            }

            @Override
            public void onGroupCheckClick(boolean checked, String name) {
                if (checked) {
                    if (!selectedGroups.contains(name)) {
                        selectedGroups.add(name);
                    }
                } else {
                    selectedGroups.remove(name);
                }

                updateGroupView();
            }
        });

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.create_task_activity_0, taskCreateNewFragment)
            .replace(R.id.create_task_activity_1, taskCreateNewSingleDatepickerFragment)
            .replace(R.id.create_task_activity_2, okCancelButtonFooterFragment)
            .commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void nameChanged(String name) {
        updateName(name);
        if (groupMake) {
            updateGroupView();
        }
    }

    @Override
    public void descriptionChanged(String description) {
        taskDescription = description;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void templateChanged(String templateName, String name, String description, boolean repeat, int interval) {
        selectedTemplate = templateName;
        templateRepeat = repeat;
        templateInterval = interval;
        updateName(name);
        if (groupMake) {
            updateGroupView();
        }
        taskDescription = description;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void checkedStateChange(boolean checkedState) {
        groupMake = checkedState;

        if (checkedState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.create_task_activity_1, taskCreateNewGroupRecyclerFragment)
                    .commit();
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    updateGroupView();
                }
            }, 100);
            updateName(singleName);
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.create_task_activity_1, taskCreateNewSingleDatepickerFragment)
                    .commit();

            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    updateSingleDate();
                }
            }, 100);
        }
    }

    @Override
    public void checkedEditResetStateChange(boolean checkedState) {
        // Don't do anything with this because it's not the edit activity
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void selectedDate(LocalDate pickedDate) {
        singleDateValid = true;
        singleDate = pickedDate;
        taskCreateNewSingleDatepickerFragment.unsetTextRed();
        updateSingleDate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void confirmButtonListener() {
        boolean allValid = false;

        if (nameValid) {
            if (groupMake) {
                validateGroupSelections();
                if (groupValid) {
                    allValid = true;
                } else {
                    displayTextScreen(getResources().getString(R.string.group_error));
                }
            } else {
                if (singleDateValid) {
                    allValid = true;
                } else {
                    taskCreateNewSingleDatepickerFragment.setTextRed();
                    displayTextScreen(getResources().getString(R.string.single_date_error));
                }
            }
        } else {
            displayTextScreen(getResources().getString(R.string.type_name_error));
        }

        if (allValid) {
            if (groupMake) {
                for (int i = 0; i < names.size(); i++) {
                    LocalDate startDate = groupDates.get(selectedGroups.get(i));
                    LocalDate endDate  = startDate.plusYears(yearsAdded);
                    ShrimpTask baseShrimpTask = new ShrimpTask(names.get(i), selectedTemplate, startDate, taskDescription, selectedGroups.get(i));
                    placeShrimpTasks(baseShrimpTask, startDate, endDate);
                }
                finish();
            } else {
                LocalDate endDate  = singleDate.plusYears(yearsAdded);
                ShrimpTask baseShrimpTask = new ShrimpTask(names.get(0), selectedTemplate, singleDate, taskDescription, getResources().getString(R.string.no_group_string));
                placeShrimpTasks(baseShrimpTask, singleDate, endDate);
                finish();
            }
        }
    }

    @Override
    public void cancelButtonListener() {
        finish();
    }

    public ShrimpTaskViewModel getShrimpTaskViewModel() {
        return shrimpTaskViewModel;
    }

    public TaskTemplateViewModel getTaskTemplateViewModel() {
        return taskTemplateViewModel;
    }

    public GroupViewModel getGroupViewModel() {
        return groupViewModel;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showDatePickerDialog(String name, LocalDate pickedDate, TextView passedTextView) {
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener(){
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Do something with the date chosen by the user
                        LocalDate finalDate = LocalDate.of(year, month + 1, dayOfMonth);
                        groupDates.put(name, finalDate);
                        passedTextView.setText(finalDate.toString());
                        passedTextView.setTextColor(getResources().getColor(R.color.textValid));
                        updateGroupView();
                    }
                }, pickedDate.getYear(), pickedDate.getMonthValue() - 1, pickedDate.getDayOfMonth());

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateGroupView() {
        for (int i = 0; i < taskCreateNewGroupRecyclerFragment.getAdapter().getItemCount(); i++) {
            CheckBox groupCheckBox = (CheckBox) taskCreateNewGroupRecyclerFragment.getRecyclerView().findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.create_task_group_recycler_name);
            TextView groupTaskFullName = (TextView) taskCreateNewGroupRecyclerFragment.getRecyclerView().findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.create_task_group_recycler_task_fullname);
            TextView groupLastDate = (TextView) taskCreateNewGroupRecyclerFragment.getRecyclerView().findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.create_task_group_recycler_task_lastdate);
            TextView groupDateTextView = (TextView) taskCreateNewGroupRecyclerFragment.getRecyclerView().findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.create_task_group_recycler_date_textview);

            String groupName = groupCheckBox.getText().toString();
            String taskFullName = singleName + getResources().getString(R.string.between_name_group_string) + groupName;

            if (selectedGroups.contains(groupName)) {
                groupCheckBox.setChecked(true);
                if (groupDates.containsKey(groupName)) {
                    //String selectedDateString = getResources().getString(R.string.create_task_selected_date);
                    //selectedDateString = selectedDateString + " " + groupDates.get(groupName).toString();
                    String selectedDateString = groupDates.get(groupName).toString();
                    groupDateTextView.setText(selectedDateString);
                }

                if (singleName != null && !singleName.trim().isEmpty()) {
                    groupTaskFullName.setText(taskFullName);

                    if (distinctShrimpTaskNames.contains(taskFullName)) {
                        groupLastDate.setVisibility(View.VISIBLE);
                        String lastDateString = getResources().getString(R.string.create_task_last_exist_date);
                        lastDateString = lastDateString + " " + lastExecuteDate.get(distinctShrimpTaskNames.indexOf(taskFullName)).toString();
                        groupLastDate.setText(lastDateString);
                        if (groupDates.containsKey(groupName)) {
                            if( (groupDates.get(groupName).compareTo(lastExecuteDate.get(distinctShrimpTaskNames.indexOf(taskFullName))) > 0)) {
                                groupTaskFullName.setTextColor(getResources().getColor(R.color.textValid));
                            } else {
                                groupTaskFullName.setTextColor(getResources().getColor(R.color.textInvalid));
                            }
                        } else {
                            groupTaskFullName.setTextColor(getResources().getColor(R.color.textInvalid));
                        }
                    } else {
                        groupTaskFullName.setTextColor(getResources().getColor(R.color.textValid));
                        groupLastDate.setVisibility(View.GONE);
                    }
                } else {
                    groupTaskFullName.setText("");
                }
            } else {
                groupTaskFullName.setText("");
                groupLastDate.setVisibility(View.GONE);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void validateGroupSelections() {
        groupValid = true;

        int groupCount =taskCreateNewGroupRecyclerFragment.getAdapter().getItemCount();

        if (groupCount == 0 || !(selectedGroups.size() > 0)) {
            groupValid = false;
        } else {
            names = new ArrayList<>();

            for (int i = 0; i < groupCount; i++) {
                CheckBox groupCheckBox = (CheckBox) taskCreateNewGroupRecyclerFragment.getRecyclerView().findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.create_task_group_recycler_name);
                TextView groupDateTextView = (TextView) taskCreateNewGroupRecyclerFragment.getRecyclerView().findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.create_task_group_recycler_date_textview);

                String groupName = groupCheckBox.getText().toString();
                String taskFullName = singleName + getResources().getString(R.string.between_name_group_string) + groupName;

                if (selectedGroups.contains(groupName)) {
                    names.add(taskFullName);
                    if (distinctShrimpTaskNames.contains(taskFullName)) {
                        if (groupDates.containsKey(groupName)) {
                            if (groupDates.get(groupName).compareTo(lastExecuteDate.get(distinctShrimpTaskNames.indexOf(taskFullName))) <= 0) {
                                groupValid = false;
                            }
                        } else {
                            groupValid = false;
                        }
                    }
                    if (!groupDates.containsKey(groupName)) {
                        groupValid = false;
                        groupDateTextView.setTextColor(getResources().getColor(R.color.textInvalid));
                    }
                }
            }
        }
    }

    private void displayTextScreen(String inputText) {
        Toast.makeText(
                getApplicationContext(),
                inputText,
                Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateName(String name) {
        singleName = name;

        if (name == null || name.trim().isEmpty()) {
            taskCreateNewFragment.setNameRed();
            nameValid = false;
        } else {
            singleName = name.trim();

            if (groupMake) {
                nameValid = true;
                names = new ArrayList<>();
                taskCreateNewFragment.unsetNameRed();
                // Add code to update arraylist with group checks

            } else {
                if (distinctShrimpTaskNames.contains(singleName)) {
                    nameValid = false;
                    taskCreateNewFragment.setNameRed();
                } else {
                    nameValid = true;
                    taskCreateNewFragment.unsetNameRed();
                    names = new ArrayList<>();
                    names.add(singleName);
                }
            }
        }

        updateSingleDate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateSingleDate() {
        if (!groupMake) {
            TextView selectedDate = taskCreateNewSingleDatepickerFragment.getView().findViewById(R.id.create_task_single_datepicker_textview);
            TextView existingDate = taskCreateNewSingleDatepickerFragment.getView().findViewById(R.id.create_task_single_datepicker_old_date_textview);

            if (singleDate != null) {
                String selectedDateString = getResources().getString(R.string.create_task_selected_date);
                selectedDateString = selectedDateString + " " + singleDate.toString();
                selectedDate.setText(selectedDateString);
            }

            if (singleName != null && !singleName.trim().isEmpty()) {
                if (distinctShrimpTaskNames.contains(singleName)) {
                    LocalDate lastExecuteDateitem = lastExecuteDate.get(distinctShrimpTaskNames.indexOf(singleName));

                    if (singleDate != null) {
                        if (singleDate.compareTo(lastExecuteDateitem) > 0) {
                            taskCreateNewFragment.unsetNameRed();
                        } else {
                            taskCreateNewFragment.setNameRed();
                        }
                    }


                    String lastDateString = getResources().getString(R.string.create_task_last_exist_date);
                    lastDateString = lastDateString + " " + lastExecuteDateitem.toString();
                    existingDate.setText(lastDateString);
                    existingDate.setVisibility(View.VISIBLE);
                } else {
                    existingDate.setVisibility(View.GONE);
                }
            } else {
                existingDate.setVisibility(View.GONE);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void placeShrimpTasks(ShrimpTask newShrimpTask, LocalDate startDate, LocalDate endDate) {
        if (templateRepeat) {
            long repeatTimes = (DAYS.between(startDate, endDate)) / templateInterval;

            for (int i = 0; i < repeatTimes; i++) {
                shrimpTaskViewModel.insert(new ShrimpTask(newShrimpTask.getName(), newShrimpTask.getParentName(), startDate, newShrimpTask.getDescription(), newShrimpTask.getGroup()));
                startDate = startDate.plusDays(templateInterval);
            }
        } else {
            shrimpTaskViewModel.insert(newShrimpTask);
        }

    }


}