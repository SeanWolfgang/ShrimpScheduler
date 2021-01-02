package com.example.shrimpscheduler.CreateGroup;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.shrimpscheduler.CreateTemplate.TemplateCreateNewFragment;
import com.example.shrimpscheduler.Group.Group;
import com.example.shrimpscheduler.Group.GroupViewModel;
import com.example.shrimpscheduler.MainFragments.OkCancelButtonFooterFragment;
import com.example.shrimpscheduler.R;
import com.example.shrimpscheduler.Template.TaskTemplate;
import com.example.shrimpscheduler.Template.TaskTemplateViewModel;

import java.util.ArrayList;

public class GroupCreateNewActivity extends AppCompatActivity
        implements GroupCreateNewFragment.GroupCreateNewFragmentListener,
        OkCancelButtonFooterFragment.OkCancelButtonFooterFragmentListener {

    private FragmentManager fragmentManager;
    private GroupCreateNewFragment groupCreateNewFragment;
    private OkCancelButtonFooterFragment okCancelButtonFooterFragment;

    private GroupViewModel groupViewModel;

    private ArrayList<String> groupNames = new ArrayList<>();

    private String groupName;
    private boolean nameRepeated = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_group_activity);
        fragmentManager = getSupportFragmentManager();

        groupCreateNewFragment = new GroupCreateNewFragment();
        okCancelButtonFooterFragment = new OkCancelButtonFooterFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.create_group_activity_0, groupCreateNewFragment)
                .replace(R.id.create_group_activity_1, okCancelButtonFooterFragment)
                .commit();

        // Assign template view model
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        groupViewModel.getAllGroups().observe(this, allGroups -> {
            // Update the cached copy of the words in the adapter.
            for (Group group : allGroups) {
                groupNames.add(group.getName());
            }
        });
    }

    @Override
    public void nameChanged(String name) {
        updateName(name);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void confirmButtonListener() {
        boolean valid = true;
        if (groupName == null || groupName.trim().isEmpty() || nameRepeated) {
            valid = false;
            displayTextScreen(getResources().getString(R.string.group_make_name_error));
        }

        if (valid) {
            Group insertingGroup;
            insertingGroup = new Group(groupName, "0");

            groupViewModel.insert(insertingGroup);

            finish();
        }
    }

    private void updateName(String name) {
        groupName = name.trim();

        if (groupNames.contains(groupName)) {
            nameRepeated = true;
            groupCreateNewFragment.setNameRed();
        } else {
            nameRepeated = false;
            groupCreateNewFragment.unsetNameRed();
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
