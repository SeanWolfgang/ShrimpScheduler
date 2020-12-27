package com.example.shrimpscheduler;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

public class ShrimpTaskCreateDialog extends AppCompatDialogFragment {
    private Spinner createShrimpTaskSpinner;
    private EditText createShrimpTaskNameEditText;
    private EditText createShrimpTaskDescriptionEditText;
    private CheckBox createShrimpTaskCheckbox;

    private TaskTemplateViewModel taskTemplateViewModel;
    private ShrimpTaskViewModel shrimpTaskViewModel;
    private GroupViewModel groupViewModel;

    private ShrimpTaskCreateDialogListener listener;
    private int[] pickedDate = new int[3];
    private boolean pickedDateBool = false;

    public MainActivity mainActivity;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_task_dialog, null);

        mainActivity = (MainActivity) getActivity();
        taskTemplateViewModel = mainActivity.getTaskTemplateViewModel();
        shrimpTaskViewModel = mainActivity.getShrimpTaskViewModel();
        groupViewModel = mainActivity.getGroupViewModel();

        builder.setView(view)
                .setTitle(R.string.create_task_dialog_title)
                .setNegativeButton(R.string.create_task_dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.create_task_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String textName = createShrimpTaskNameEditText.getText().toString();
                        String textDescription = createShrimpTaskDescriptionEditText.getText().toString();

                    }
                });

        createShrimpTaskSpinner = view.findViewById(R.id.create_task_dialog_spinner);
        createShrimpTaskNameEditText = view.findViewById(R.id.create_task_dialog_name);
        createShrimpTaskDescriptionEditText = view.findViewById(R.id.create_task_dialog_description);
        createShrimpTaskCheckbox = view.findViewById(R.id.create_task_dialog_batch_checkbox);

        return builder.create();
    }

    public  interface ShrimpTaskCreateDialogListener {
        void makeShrimpTask(String name, String description, LocalDate startDate, String templateName, String[] groupNames);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ShrimpTaskCreateDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ShrimpTaskCreateDialogListener");
        }
    }

}