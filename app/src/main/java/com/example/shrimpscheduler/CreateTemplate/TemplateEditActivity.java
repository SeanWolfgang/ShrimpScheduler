package com.example.shrimpscheduler.CreateTemplate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.shrimpscheduler.MainActivity;
import com.example.shrimpscheduler.MainFragments.OkCancelButtonFooterFragment;
import com.example.shrimpscheduler.R;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTask;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskViewModel;
import com.example.shrimpscheduler.Template.TaskTemplate;
import com.example.shrimpscheduler.Template.TaskTemplateViewModel;

import java.util.ArrayList;

public class TemplateEditActivity extends AppCompatActivity
        implements TemplateCreateNewFragment.TemplateCreateNewFragmentListener,
        OkCancelButtonFooterFragment.OkCancelButtonFooterFragmentListener {

    private FragmentManager fragmentManager;
    private TemplateCreateNewFragment templateCreateNewFragment;
    private OkCancelButtonFooterFragment okCancelButtonFooterFragment;

    private TaskTemplateViewModel taskTemplateViewModel;
    private ShrimpTaskViewModel shrimpTaskViewModel;

    private ArrayList<String> taskTemplateNames = new ArrayList<>();
    private ArrayList<ShrimpTask> templateShrimpTasks = new ArrayList<>();

    private String templateName;
    private String templateDescription;
    private int templateInterval = 0;
    private boolean templateRepeat;
    private boolean nameRepeated = false;

    private TaskTemplate modifyTemplate;

    private String passedTemplateName;
    private String passedTemplateDescription;
    private boolean passedTemplateRepeat;
    private int passedTemplateInterval;
    private int passedTemplateID;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        passedTemplateName = intent.getStringExtra(MainActivity.EXTRA_8);
        passedTemplateDescription = intent.getStringExtra(MainActivity.EXTRA_9);
        passedTemplateRepeat = intent.getBooleanExtra(MainActivity.EXTRA_10, false);
        passedTemplateInterval = intent.getIntExtra(MainActivity.EXTRA_11, 0);
        passedTemplateID = intent.getIntExtra(MainActivity.EXTRA_12, 0);

        templateName = passedTemplateName;
        templateDescription = passedTemplateDescription;
        templateRepeat = passedTemplateRepeat;
        templateInterval = passedTemplateInterval;

        setContentView(R.layout.create_template_activity);
        fragmentManager = getSupportFragmentManager();

        templateCreateNewFragment = new TemplateCreateNewFragment();
        okCancelButtonFooterFragment = new OkCancelButtonFooterFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.create_template_activity_0, templateCreateNewFragment)
                .replace(R.id.create_template_activity_1, okCancelButtonFooterFragment)
                .commit();

        // Assign template view model
        taskTemplateViewModel = new ViewModelProvider(this).get(TaskTemplateViewModel.class);

        taskTemplateViewModel.getAllTaskTemplates().observe(this, allTaskTemplates -> {
            // Update the cached copy of the words in the adapter.
            for (TaskTemplate taskTemplate : allTaskTemplates) {
                taskTemplateNames.add(taskTemplate.getName());
            }

            if (taskTemplateNames.contains(passedTemplateName)) {
                taskTemplateNames.remove(passedTemplateName);
            }
        });

        // Assign template view model
        taskTemplateViewModel = new ViewModelProvider(this).get(TaskTemplateViewModel.class);
        shrimpTaskViewModel = new ViewModelProvider(this).get(ShrimpTaskViewModel.class);

        // Set filters
        taskTemplateViewModel.setIDFilter(passedTemplateID);
        shrimpTaskViewModel.setTemplateFilter(passedTemplateName);

        taskTemplateViewModel.getTaskTemplateSpecifiedID().observe(this, selectedTemplate -> {
            // Update the cached copy of the words in the adapter.
            modifyTemplate = selectedTemplate;
        });

        shrimpTaskViewModel.getShrimpTasksTemplateMatch().observe(this, matchedTasksTemplates -> {
            // Update the cached copy of the words in the adapter.
            for (ShrimpTask matchedTask : matchedTasksTemplates) {
                templateShrimpTasks.add(matchedTask);
            }
        });

        try {
            configureForEdit(passedTemplateName, passedTemplateDescription, passedTemplateRepeat, passedTemplateInterval);
        } catch (IllegalStateException e) {
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api =  Build.VERSION_CODES.O)
                @Override
                public void run() {
                    configureForEdit(passedTemplateName, passedTemplateDescription, passedTemplateRepeat, passedTemplateInterval);
                }
            }, 100);
        } catch (NullPointerException e) {
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api =  Build.VERSION_CODES.O)
                @Override
                public void run() {
                    configureForEdit(passedTemplateName, passedTemplateDescription, passedTemplateRepeat, passedTemplateInterval);
                }
            }, 100);
        }
    }

    @Override
    public void nameChanged(String name) {
        updateName(name);
    }

    @Override
    public void descriptionChanged(String description) {
        templateDescription = description;
    }

    @Override
    public void intervalChanged(int interval) {
        templateInterval = interval;
    }

    @Override
    public void checkedStateChange(boolean checkedState) {
        templateRepeat = checkedState;

        EditText intervalEditText = templateCreateNewFragment.getView().findViewById(R.id.task_template_interval);

        if (checkedState) {
            intervalEditText.setVisibility(View.VISIBLE);
            if (templateInterval > 0) {
                intervalEditText.setText(Integer.toString(templateInterval));
            }
        } else {
            intervalEditText.setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void confirmButtonListener() {
        boolean valid = true;
        if (templateName == null || templateName.trim().isEmpty() || nameRepeated) {
            valid = false;
            displayTextScreen(getResources().getString(R.string.template_make_name_error));
        }
        if (templateDescription == null || templateDescription.trim().isEmpty()) {
            valid = false;
            displayTextScreen(getResources().getString(R.string.template_make_description_error));
        }
        if ((templateRepeat) && (templateInterval <= 0)) {
            valid = false;
            displayTextScreen(getResources().getString(R.string.template_make_interval_error));
        }

        if (valid) {
            modifyTemplate.setName(templateName);
            modifyTemplate.setDefaultName(templateName);
            modifyTemplate.setDefaultDescription(templateDescription);
            modifyTemplate.setRepeat(templateRepeat);
            modifyTemplate.setDaysBetweenRepeat(templateInterval);

            taskTemplateViewModel.updateTemplate(modifyTemplate);

            if (templateShrimpTasks.size() > 0) {

                for (ShrimpTask templateTask : templateShrimpTasks) {
                    templateTask.setParentName(templateName);

                    if (checkIfNameMatch(templateTask)) {
                        String newName = templateName + getResources().getString(R.string.between_name_group_string) + templateTask.getGroup();
                        templateTask.setName(newName);
                    }

                    if (checkIfDescriptionMatch(templateTask)) {
                        templateTask.setDescription(templateDescription);
                    }

                    shrimpTaskViewModel.updateShrimpTask(templateTask);
                }
            }

            finish();
        }
    }

    private void updateName(String name) {
        templateName = name.trim();

        if (taskTemplateNames.contains(templateName)) {
            nameRepeated = true;
            templateCreateNewFragment.setNameRed();
        } else {
            nameRepeated = false;
            templateCreateNewFragment.unsetNameRed();
        }
    }

    @Override
    public void cancelButtonListener() {
        finish();
    }

    private void displayTextScreen(String inputText) {
        Toast.makeText(
                getApplicationContext(),
                inputText,
                Toast.LENGTH_LONG).show();
    }

    private void configureForEdit(String editName, String editDescription, boolean editRepeat, int editInterval) {
        templateCreateNewFragment.configureForEdit(editName, editDescription, editRepeat, editInterval);
    }

    private boolean checkIfNameMatch(ShrimpTask checkingTask) {
        boolean nameIsTemplate = false;

        String checkName = checkingTask.getName();
        String checkGroup = checkingTask.getGroup();

        if (checkGroup.equals(getResources().getString(R.string.no_group_string))){
            if (checkName.equals(templateName)) {
                nameIsTemplate = true;
            }
        } else {
            if (!checkGroup.trim().equals("")) {
                int deleteLength = checkGroup.length();
                deleteLength = deleteLength + getResources().getString(R.string.between_name_group_string).length();

                if (checkName.length() > deleteLength) {
                    String subName = checkName.substring(0, checkName.length() - deleteLength);

                    if (subName.equals(passedTemplateName)) {
                        nameIsTemplate = true;
                    }

                }
            }
        }

        return nameIsTemplate;
    }

    private boolean checkIfDescriptionMatch(ShrimpTask checkingTask) {
        boolean descriptionIsTemplate = false;

        if (checkingTask.getDescription().equals(passedTemplateDescription)) {
            descriptionIsTemplate = true;
        }

        return descriptionIsTemplate;
    }
}
