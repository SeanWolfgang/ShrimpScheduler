package com.example.shrimpscheduler.CreateTask;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shrimpscheduler.R;
import com.example.shrimpscheduler.Template.TaskTemplate;
import com.example.shrimpscheduler.Template.TaskTemplateViewModel;

import java.util.ArrayList;

public class TaskCreateNewFragment extends Fragment {
    private Spinner createShrimpTaskSpinner;
    private EditText createShrimpTaskNameEditText;
    private EditText createShrimpTaskDescriptionEditText;
    private CheckBox createShrimpTaskCheckbox;
    private CheckBox createShrimpTaskEditResetCheckbox;
    private View underline;

    private TaskTemplateViewModel taskTemplateViewModel;

    private TaskCreateNewFragment.TaskCreateNewFragmentListener listener;

    public TaskCreateNewActivity taskCreateNewActivity;

    private ArrayList<String> spinnerStrings = new ArrayList<>();
    private ArrayList<String> templateNameStrings = new ArrayList<>();
    private ArrayList<String> templateDescriptionStrings = new ArrayList<>();
    private ArrayList<Boolean> templateRepeats = new ArrayList<>();
    private ArrayList<Integer> templateIntervals = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_task_fragment, container, false);

        try {
            taskCreateNewActivity = (TaskCreateNewActivity) getActivity();
            taskTemplateViewModel = taskCreateNewActivity.getTaskTemplateViewModel();
        } catch (ClassCastException e) {
            taskTemplateViewModel = new ViewModelProvider(this).get(TaskTemplateViewModel.class);
        }


        createShrimpTaskSpinner = view.findViewById(R.id.create_task_spinner);
        createShrimpTaskNameEditText = view.findViewById(R.id.create_task_name);
        createShrimpTaskDescriptionEditText = view.findViewById(R.id.create_task_description);
        createShrimpTaskCheckbox = view.findViewById(R.id.create_task_batch_checkbox);
        createShrimpTaskEditResetCheckbox = view.findViewById(R.id.create_task_edit_reset_checkbox);
        underline = view.findViewById(R.id.create_task_underline);

        spinnerStrings.add(getContext().getString(R.string.empty_template_string));
        templateNameStrings.add("");
        templateDescriptionStrings.add("");
        templateRepeats.add(false);
        templateIntervals.add(1);

        taskTemplateViewModel.getAllTaskTemplates().observe(this, taskTemplates -> {
            // Update the cached copy of the words in the adapter.

            for (TaskTemplate taskTemplate : taskTemplates) {
                spinnerStrings.add(taskTemplate.getName());
                templateNameStrings.add(taskTemplate.getDefaultName());
                templateDescriptionStrings.add(taskTemplate.getDefaultDescription());
                templateRepeats.add(taskTemplate.isRepeat());
                templateIntervals.add(taskTemplate.getDaysBetweenRepeat());
            }
        });

        createShrimpTaskNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listener.nameChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        createShrimpTaskCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.checkedStateChange(isChecked);
            }
        });

        createShrimpTaskEditResetCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.checkedEditResetStateChange(isChecked);
            }
        });

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerStrings);
        spinnerAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        createShrimpTaskSpinner.setAdapter(spinnerAdapter);

        createShrimpTaskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    listener.templateChanged(
                            spinnerStrings.get(position),
                            templateNameStrings.get(position),
                            templateDescriptionStrings.get(position),
                            templateRepeats.get(position),
                            templateIntervals.get(position)
                    );
                } else {
                    String name = templateNameStrings.get(position);
                    String description = templateDescriptionStrings.get(position);
                    createShrimpTaskNameEditText.setText(name);
                    createShrimpTaskDescriptionEditText.setText(description);
                    listener.templateChanged(
                            spinnerStrings.get(position),
                            name,
                            description,
                            templateRepeats.get(position),
                            templateIntervals.get(position)
                    );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    public  interface TaskCreateNewFragmentListener {
        void nameChanged(String name);
        void descriptionChanged(String description);
        void templateChanged(String templateName, String name, String description, boolean repeat, int daysBetweenRepeat);
        void checkedStateChange(boolean checkedState);
        void checkedEditResetStateChange(boolean checkedState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (TaskCreateNewFragment.TaskCreateNewFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement TaskCreateNewFragmentListener");
        }
    }

    public void setNameRed() {
        createShrimpTaskNameEditText.setTextColor(getResources().getColor(R.color.textInvalid));
    }

    public void unsetNameRed() {
        createShrimpTaskNameEditText.setTextColor(getResources().getColor(R.color.textValid));
    }

    public void configureForEdit(String name, String description) {
        createShrimpTaskSpinner.setEnabled(false);
        createShrimpTaskSpinner.setVisibility(View.GONE);
        underline.setVisibility(View.GONE);
        createShrimpTaskNameEditText.setText(name);
        createShrimpTaskNameEditText.setEnabled(false);
        createShrimpTaskNameEditText.setTextColor(getResources().getColor(android.R.color.black));
        createShrimpTaskDescriptionEditText.setText(description);
        createShrimpTaskDescriptionEditText.setTextColor(getResources().getColor(android.R.color.black));
        createShrimpTaskCheckbox.setText(getResources().getString(R.string.create_task_edit_checkbox));

        createShrimpTaskEditResetCheckbox.setVisibility(View.VISIBLE);
    }
}