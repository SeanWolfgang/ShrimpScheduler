package com.example.shrimpscheduler;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class GroupCreateNew  extends Fragment {
    private EditText groupName;
    private Button saveButton;

    public MainActivity mainActivity;
    private GroupViewModel groupViewModel;
    private int numberOfMatchedItems;

    private GroupCreateNew.GroupCreateNewFragmentListener listener;

    public interface GroupCreateNewFragmentListener{
        void onGroupCreateNewButtonClicked(String name);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_group_fragment, container, false);

        mainActivity = (MainActivity) getActivity();
        groupViewModel = mainActivity.getGroupViewModel();

        groupName = view.findViewById(R.id.new_group_name);
        saveButton = view.findViewById(R.id.save_new_group);


        groupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                groupViewModel.setGroupNameFilter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        groupViewModel.getGroupNameMatch().observe(this, groupNameMatch -> {
            // Update the cached copy of the words in the adapter.
            numberOfMatchedItems = groupNameMatch;
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String proposedGroupName = groupName.getText().toString();

                if (TextUtils.isEmpty(groupName.getText())) {
                    Toast.makeText(
                            getContext(),
                            R.string.need_name,
                            Toast.LENGTH_LONG).show();
                } else if (numberOfMatchedItems > 0) {
                    //setResult(RESULT_CANCELED, replyIntent);
                    Toast.makeText(
                            getContext(),
                            R.string.repeat_name,
                            Toast.LENGTH_LONG).show();
                } else {
                    listener.onGroupCreateNewButtonClicked(proposedGroupName);
                    resetForm();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GroupCreateNew.GroupCreateNewFragmentListener) {
            listener = (GroupCreateNew.GroupCreateNewFragmentListener) context;
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
        groupName.getText().clear();
    }
}
