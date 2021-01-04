package com.example.shrimpscheduler.MainFragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.shrimpscheduler.Group.Group;
import com.example.shrimpscheduler.Group.GroupViewModel;
import com.example.shrimpscheduler.MainActivity;
import com.example.shrimpscheduler.R;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTask;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskViewModel;
import com.example.shrimpscheduler.Template.TaskTemplate;
import com.example.shrimpscheduler.Template.TaskTemplateViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SettingsFragment extends Fragment {
    private TextView settingsDatePickerTextView;
    private Button settingsDeleteAllTasksButton;
    private Button settingsDeleteAllTemplatesButton;
    private Button settingsDeleteAllGroupsButton;
    private Button settingsDeleteFutureTaskNameButton;
    private Button settingsDeleteFutureTaskTemplateButton;
    private Button settingsDeleteFutureTaskGroupButton;
    private ImageButton settingsDeleteFutureTaskNameListButton;
    private ImageButton settingsDeleteFutureTaskTemplateListButton;
    private ImageButton settingsDeleteFutureTaskGroupListButton;
    private Button settingsDatepicker;

    private MainActivity mainActivity;
    private ShrimpTaskViewModel shrimpTaskViewModel;
    private TaskTemplateViewModel taskTemplateViewModel;
    private GroupViewModel groupViewModel;

    private HashMap<String, Boolean> distinctTaskNamesCheckedStates = new HashMap<String, Boolean>();
    private HashMap<String, Boolean> distinctTemplateNamesCheckedStates = new HashMap<String, Boolean>();
    private HashMap<String, Boolean> distinctGroupNamesCheckedStates = new HashMap<String, Boolean>();

    private ArrayList<ShrimpTask> allShrimpTasks = new ArrayList<>();

    private LocalDate effectiveDate = LocalDate.now();

    private SettingsFragment.SettingsFragmentListener listener;

    public interface SettingsFragmentListener{
        void onDeleteAllTasksClicked();
        void onDeleteAllTemplatesClicked();
        void onDeleteAllGroupsClicked();
        void onDeleteFutureTasksNameClicked();
        void onDeleteFutureTasksTemplateClicked();
        void onDeleteFutureTasksGroupClicked();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        mainActivity = (MainActivity) getActivity();

        shrimpTaskViewModel = mainActivity.getShrimpTaskViewModel();
        taskTemplateViewModel = mainActivity.getTaskTemplateViewModel();
        groupViewModel = mainActivity.getGroupViewModel();

        shrimpTaskViewModel.getAllShrimpTasks().observe(this, gottenAllShrimpTasks -> {
            allShrimpTasks.clear();

            for (ShrimpTask eachTask : gottenAllShrimpTasks) {
                allShrimpTasks.add(eachTask);
            }
        });

        shrimpTaskViewModel.getDistinctShrimpTaskNames().observe(this, gottenDistinctTaskNames -> {
            for (String eachTaskName : gottenDistinctTaskNames) {
                distinctTaskNamesCheckedStates.put(eachTaskName, false);
            }
        });

        taskTemplateViewModel.getAllTaskTemplates().observe(this, allTemplates -> {
            for (TaskTemplate eachTemplate : allTemplates) {
                distinctTemplateNamesCheckedStates.put(eachTemplate.getName(), false);
            }
        });

        groupViewModel.getAllGroups().observe(this, allGroups -> {
            for (Group eachGroup : allGroups) {
                distinctGroupNamesCheckedStates.put(eachGroup.getName(), false);
            }
        });

        settingsDatePickerTextView = view.findViewById(R.id.settings_datepicker_label);
        settingsDeleteAllTasksButton = view.findViewById(R.id.settings_delete_all_tasks);
        settingsDeleteAllTemplatesButton = view.findViewById(R.id.settings_delete_all_templates);
        settingsDeleteAllGroupsButton = view.findViewById(R.id.settings_delete_all_groups);
        settingsDeleteFutureTaskNameButton = view.findViewById(R.id.settings_delete_future_task_name);
        settingsDeleteFutureTaskTemplateButton = view.findViewById(R.id.settings_delete_future_task_template);
        settingsDeleteFutureTaskGroupButton = view.findViewById(R.id.settings_delete_future_task_group);
        settingsDeleteFutureTaskNameListButton = view.findViewById(R.id.settings_delete_future_task_name_select);
        settingsDeleteFutureTaskTemplateListButton = view.findViewById(R.id.settings_delete_future_task_template_select);
        settingsDeleteFutureTaskGroupListButton = view.findViewById(R.id.settings_delete_future_task_group_select);
        settingsDatepicker = view.findViewById(R.id.settings_datepicker);

        settingsDatepicker.setText(effectiveDate.toString());

        settingsDeleteAllTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteAllTasksClicked();
            }
        });

        settingsDeleteAllTemplatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteAllTemplatesClicked();
            }
        });

        settingsDeleteAllGroupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteAllGroupsClicked();
            }
        });

        settingsDeleteFutureTaskNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteFutureTasksNameClicked();
            }
        });

        settingsDeleteFutureTaskTemplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteFutureTasksTemplateClicked();
            }
        });

        settingsDeleteFutureTaskGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteFutureTasksGroupClicked();
            }
        });

        settingsDeleteFutureTaskNameListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runNameMultiPicker(v);
            }
        });

        settingsDeleteFutureTaskTemplateListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTemplateMultiPicker(v);
            }
        });

        settingsDeleteFutureTaskGroupListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runGroupMultiPicker(v);
            }
        });

        settingsDatepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SettingsFragment.SettingsFragmentListener) {
            listener = (SettingsFragment.SettingsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SettingsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void runNameMultiPicker(View v) {
        int size = distinctTaskNamesCheckedStates.size();

        String[] groupArray = new String[size];
        boolean[] checkedGroupArray = new boolean[size];

        int i = 0;

        for (Map.Entry<String, Boolean> entry : distinctTaskNamesCheckedStates.entrySet()) {
            groupArray[i] = entry.getKey();
            checkedGroupArray[i] = entry.getValue();
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);

        //setTitle
        builder.setTitle("Select names");
        //set multichoice
        builder.setMultiChoiceItems(groupArray, checkedGroupArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Update the current focused item's checked status
                checkedGroupArray[which] = isChecked;
                distinctTaskNamesCheckedStates.put(groupArray[which], isChecked);
            }
        });

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }


    public void runTemplateMultiPicker(View v) {
        int size = distinctTemplateNamesCheckedStates.size();

        String[] groupArray = new String[size];
        boolean[] checkedGroupArray = new boolean[size];

        int i = 0;

        for (Map.Entry<String, Boolean> entry : distinctTemplateNamesCheckedStates.entrySet()) {
            groupArray[i] = entry.getKey();
            checkedGroupArray[i] = entry.getValue();
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);

        //setTitle
        builder.setTitle("Select templates");
        //set multichoice
        builder.setMultiChoiceItems(groupArray, checkedGroupArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Update the current focused item's checked status
                checkedGroupArray[which] = isChecked;
                distinctTemplateNamesCheckedStates.put(groupArray[which], isChecked);
            }
        });

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }


    public void runGroupMultiPicker(View v) {
        int size = distinctGroupNamesCheckedStates.size();

        String[] groupArray = new String[size];
        boolean[] checkedGroupArray = new boolean[size];

        int i = 0;

        for (Map.Entry<String, Boolean> entry : distinctGroupNamesCheckedStates.entrySet()) {
            groupArray[i] = entry.getKey();
            checkedGroupArray[i] = entry.getValue();
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);

        //setTitle
        builder.setTitle("Select groups");
        //set multichoice
        builder.setMultiChoiceItems(groupArray, checkedGroupArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Update the current focused item's checked status
                checkedGroupArray[which] = isChecked;
                distinctGroupNamesCheckedStates.put(groupArray[which], isChecked);
            }
        });

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }

    public void showDatePickerDialog(View v) {
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Do something with the date chosen by the user
                        effectiveDate = LocalDate.of(year, month + 1, dayOfMonth);
                        settingsDatepicker.setText(effectiveDate.toString());
                    }
                }, effectiveDate.getYear(), effectiveDate.getMonthValue() - 1, effectiveDate.getDayOfMonth());

        datePickerDialog.show();
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public HashMap<String, Boolean> getDistinctTaskNamesCheckedStates() {
        return distinctTaskNamesCheckedStates;
    }

    public HashMap<String, Boolean> getDistinctTemplateNamesCheckedStates() {
        return distinctTemplateNamesCheckedStates;
    }

    public HashMap<String, Boolean> getDistinctGroupNamesCheckedStates() {
        return distinctGroupNamesCheckedStates;
    }

    public ArrayList<ShrimpTask> getAllShrimpTasks() {
        return allShrimpTasks;
    }
}
