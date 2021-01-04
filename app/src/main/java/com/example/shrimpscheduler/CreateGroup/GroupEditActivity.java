package com.example.shrimpscheduler.CreateGroup;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.shrimpscheduler.Group.Group;
import com.example.shrimpscheduler.Group.GroupViewModel;
import com.example.shrimpscheduler.MainActivity;
import com.example.shrimpscheduler.MainFragments.OkCancelButtonFooterFragment;
import com.example.shrimpscheduler.R;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTask;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskViewModel;

import java.util.ArrayList;

public class GroupEditActivity extends AppCompatActivity
        implements GroupCreateNewFragment.GroupCreateNewFragmentListener,
        OkCancelButtonFooterFragment.OkCancelButtonFooterFragmentListener {

    private FragmentManager fragmentManager;
    private GroupCreateNewFragment groupCreateNewFragment;
    private OkCancelButtonFooterFragment okCancelButtonFooterFragment;

    private GroupViewModel groupViewModel;
    public ShrimpTaskViewModel shrimpTaskViewModel;

    private ArrayList<String> groupNames = new ArrayList<>();

    private String groupName;
    private String passedGroupName;
    private int passedID;
    private boolean nameRepeated = false;

    private Group modifyGroup;
    private ArrayList<ShrimpTask> groupShrimpTasks = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        passedGroupName = intent.getStringExtra(MainActivity.EXTRA_6);
        passedID = intent.getIntExtra(MainActivity.EXTRA_7, 0);

        groupName = passedGroupName;

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
        shrimpTaskViewModel = new ViewModelProvider(this).get(ShrimpTaskViewModel.class);

        // Set filters
        groupViewModel.setIDFilter(passedID);
        shrimpTaskViewModel.setGroupFilter(passedGroupName);

        groupViewModel.getGroupSpecifiedID().observe(this, selectedGroup -> {
            // Update the cached copy of the words in the adapter.
            modifyGroup = selectedGroup;
        });

        groupViewModel.getAllGroups().observe(this, allGroups -> {
            // Update the cached copy of the words in the adapter.
            for (Group group : allGroups) {
                groupNames.add(group.getName());
            }

            if (groupNames.contains(passedGroupName)) {
                groupNames.remove(passedGroupName);
            }
        });

        shrimpTaskViewModel.getShrimpTasksGroupMatch().observe(this, matchedTasksGroups -> {
            // Update the cached copy of the words in the adapter.
            for (ShrimpTask matchedTask : matchedTasksGroups) {
                groupShrimpTasks.add(matchedTask);
            }
        });

        try {
            configureForEdit(passedGroupName);
        } catch (IllegalStateException e) {
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api =  Build.VERSION_CODES.O)
                @Override
                public void run() {
                    configureForEdit(passedGroupName);
                }
            }, 100);
        } catch (NullPointerException e) {
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api =  Build.VERSION_CODES.O)
                @Override
                public void run() {
                    configureForEdit(passedGroupName);
                }
            }, 100);
        }
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

        if (groupName == getResources().getString(R.string.no_group_string)) {
            valid = false;
            displayTextScreen(getResources().getString(R.string.reserved_group_error));
        }

        if (valid) {
            modifyGroup.setName(groupName);

            groupViewModel.updateGroup(modifyGroup);

            if (groupShrimpTasks.size() > 0) {
               for (ShrimpTask groupTask : groupShrimpTasks) {

                   String taskOldName = groupTask.getName();
                   String taskNewName = taskOldName.substring(0, taskOldName.length() - passedGroupName.length());
                   taskNewName = taskNewName + groupName;

                   groupTask.setName(taskNewName);
                   groupTask.setGroup(groupName);

                   shrimpTaskViewModel.updateShrimpTask(groupTask);
               }
            }

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

    private void configureForEdit(String editName) {
        groupCreateNewFragment.configureForEdit(editName);
    }
}