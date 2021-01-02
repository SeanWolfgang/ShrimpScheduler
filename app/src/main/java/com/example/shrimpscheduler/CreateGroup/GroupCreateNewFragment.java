package com.example.shrimpscheduler.CreateGroup;

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

public class GroupCreateNewFragment extends Fragment {
    private EditText createGroupEditText;

    private GroupCreateNewFragment.GroupCreateNewFragmentListener listener;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_group_fragment, container, false);

        createGroupEditText = view.findViewById(R.id.create_group_name);

        createGroupEditText.addTextChangedListener(new TextWatcher() {
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

        return view;
    }

    public  interface GroupCreateNewFragmentListener {
        void nameChanged(String name);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (GroupCreateNewFragment.GroupCreateNewFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement GroupCreateNewFragmentListener");
        }
    }

    public void setNameRed() {
        createGroupEditText.setTextColor(getResources().getColor(R.color.textInvalid));
    }

    public void unsetNameRed() {
        createGroupEditText.setTextColor(getResources().getColor(R.color.textValid));
    }
}