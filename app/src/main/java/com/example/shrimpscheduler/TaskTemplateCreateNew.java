package com.example.shrimpscheduler;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TaskTemplateCreateNew extends Fragment {
    private EditText taskTemplateNameEditText;
    private EditText defaultDescriptionEditText;
    private EditText daysBetweenRepeatEditText;
    private CheckBox repeatCheckbox;
    private Button saveTaskTemplateButton;

    private TaskTemplateCreateNew.TaskTemplateFragmentListener listener;

    public interface TaskTemplateFragmentListener{
        void onTaskTemplateButtonClicked(String name, String description, int daysBetweenRepeat, boolean repeat);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_template_fragment, container, false);

        taskTemplateNameEditText = view.findViewById(R.id.task_template_name);
        defaultDescriptionEditText = view.findViewById(R.id.task_template_description);
        daysBetweenRepeatEditText = view.findViewById(R.id.task_template_days_repeat);
        repeatCheckbox = view.findViewById(R.id.task_repeat_setting);
        saveTaskTemplateButton = (Button) view.findViewById(R.id.save_task_template_button);

        saveTaskTemplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int daysBetweenRepeat;
                try {
                    daysBetweenRepeat = Integer.parseInt(daysBetweenRepeatEditText.getText().toString());
                } catch (NumberFormatException nfe) {
                    daysBetweenRepeat = -1;
                }

                if (TextUtils.isEmpty(taskTemplateNameEditText.getText())) {
                    Toast.makeText(
                            getContext(),
                            R.string.need_name,
                            Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(defaultDescriptionEditText.getText())) {
                    Toast.makeText(
                            getContext(),
                            R.string.need_description,
                            Toast.LENGTH_LONG).show();
                } else if ((TextUtils.isEmpty(daysBetweenRepeatEditText.getText())) && (repeatCheckbox.isChecked())) {
                    Toast.makeText(
                            getContext(),
                            R.string.need_repeat_interval,
                            Toast.LENGTH_LONG).show();
                } else {
                    listener.onTaskTemplateButtonClicked(taskTemplateNameEditText.getText().toString(),
                            defaultDescriptionEditText.getText().toString(),
                            daysBetweenRepeat,
                            repeatCheckbox.isChecked());

                    resetForm();
                }
            }
        });

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TaskTemplateCreateNew.TaskTemplateFragmentListener) {
            listener = (TaskTemplateCreateNew.TaskTemplateFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement TaskTemplateFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void resetForm() {
        taskTemplateNameEditText.getText().clear();
        defaultDescriptionEditText.getText().clear();
        daysBetweenRepeatEditText.getText().clear();
        repeatCheckbox.setChecked(false);
    }

}
