package com.example.shrimpscheduler.CreateTemplate;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shrimpscheduler.R;

public class TemplateCreateNewFragment extends Fragment {
    private EditText createTemplateNameEditText;
    private EditText createTemplateDescriptionEditText;
    private EditText createTemplateIntervalEditText;
    private CheckBox createTemplateRepeatCheckbox;

    private TemplateCreateNewFragment.TemplateCreateNewFragmentListener listener;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_template_fragment, container, false);

        createTemplateNameEditText = view.findViewById(R.id.task_template_name);
        createTemplateDescriptionEditText = view.findViewById(R.id.task_template_description);
        createTemplateIntervalEditText = view.findViewById(R.id.task_template_interval);
        createTemplateRepeatCheckbox = view.findViewById(R.id.task_template_repeat_setting);

        createTemplateIntervalEditText.setVisibility(View.GONE);

        createTemplateNameEditText.addTextChangedListener(new TextWatcher() {
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

        createTemplateDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listener.descriptionChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        createTemplateIntervalEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    listener.intervalChanged(Integer.parseInt(s.toString()));
                } catch (IllegalStateException e) {
                    listener.intervalChanged(0);
                } catch (NumberFormatException e) {
                    listener.intervalChanged(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        createTemplateRepeatCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.checkedStateChange(isChecked);
            }
        });

        return view;
    }

    public  interface TemplateCreateNewFragmentListener {
        void nameChanged(String name);
        void descriptionChanged(String description);
        void intervalChanged(int interval);
        void checkedStateChange(boolean checkedState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (TemplateCreateNewFragment.TemplateCreateNewFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement TemplateCreateNewFragmentListener");
        }
    }

    public void setNameRed() {
        createTemplateNameEditText.setTextColor(getResources().getColor(R.color.textInvalid));
    }

    public void unsetNameRed() {
        createTemplateNameEditText.setTextColor(getResources().getColor(R.color.textValid));
    }
}