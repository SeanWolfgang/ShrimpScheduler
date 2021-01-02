package com.example.shrimpscheduler.CreateTemplate;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.shrimpscheduler.MainFragments.OkCancelButtonFooterFragment;
import com.example.shrimpscheduler.R;
import com.example.shrimpscheduler.Template.TaskTemplate;
import com.example.shrimpscheduler.Template.TaskTemplateViewModel;

import java.util.ArrayList;

public class TemplateCreateNewActivity extends AppCompatActivity
        implements TemplateCreateNewFragment.TemplateCreateNewFragmentListener,
        OkCancelButtonFooterFragment.OkCancelButtonFooterFragmentListener {

    private FragmentManager fragmentManager;
    private TemplateCreateNewFragment templateCreateNewFragment;
    private OkCancelButtonFooterFragment okCancelButtonFooterFragment;

    private TaskTemplateViewModel taskTemplateViewModel;

    private ArrayList<String> taskTemplateNames = new ArrayList<>();

    private String templateName;
    private String templateDescription;
    private int templateInterval = 0;
    private boolean templateRepeat;
    private boolean nameRepeated = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        });
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
            TaskTemplate insertingTemplate;

            if (templateRepeat) {
                insertingTemplate = new TaskTemplate(templateName, templateName, templateDescription, templateInterval, templateRepeat);
            } else {
                insertingTemplate = new TaskTemplate(templateName, templateName, templateDescription, 0, templateRepeat);
            }

            taskTemplateViewModel.insert(insertingTemplate);

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
}
