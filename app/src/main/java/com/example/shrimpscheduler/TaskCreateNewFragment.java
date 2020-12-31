package com.example.shrimpscheduler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
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
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskCreateNewFragment extends Fragment {
    private Spinner createShrimpTaskSpinner;
    private EditText createShrimpTaskNameEditText;
    private EditText createShrimpTaskDescriptionEditText;
    private CheckBox createShrimpTaskCheckbox;

    private TaskTemplateViewModel taskTemplateViewModel;
    private ShrimpTaskViewModel shrimpTaskViewModel;
    private GroupViewModel groupViewModel;

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

        taskCreateNewActivity = (TaskCreateNewActivity) getActivity();
        taskTemplateViewModel = taskCreateNewActivity.getTaskTemplateViewModel();
        shrimpTaskViewModel = taskCreateNewActivity.getShrimpTaskViewModel();
        groupViewModel = taskCreateNewActivity.getGroupViewModel();

        createShrimpTaskSpinner = view.findViewById(R.id.create_task_dialog_spinner);
        createShrimpTaskNameEditText = view.findViewById(R.id.create_task_dialog_name);
        createShrimpTaskDescriptionEditText = view.findViewById(R.id.create_task_dialog_description);
        createShrimpTaskCheckbox = view.findViewById(R.id.create_task_dialog_batch_checkbox);

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
}