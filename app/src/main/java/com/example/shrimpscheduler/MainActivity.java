package com.example.shrimpscheduler;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class MainActivity extends AppCompatActivity
        implements TaskDatePickingFragment.TaskDatePickingListener,
        FooterButtonFragment.FooterButtonFragmentListener,
        GroupHeaderFragment.GroupHeaderFragmentListener,
        TemplateHeaderFragment.TemplateHeaderFragmentListener {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private FragmentManager fragmentManager;

    // Declare models
    private ShrimpTaskViewModel shrimpTaskViewModel;
    private TaskTemplateViewModel taskTemplateViewModel;
    private GroupViewModel groupViewModel;

    /*
    View fragments
     */

    // Empty fragment
    public EmptyFragment emptyFragment0;
    public EmptyFragment emptyFragment1;
    public EmptyFragment emptyFragment2;
    public EmptyFragment emptyFragment3;

    // Task screen variables
    private Fragment dataPreviewFragment;
    private TaskDatePickingFragment taskDatePickingFragment;
    private ShrimpTaskRecyclerFragmentDate shrimpTaskRecyclerFragmentDate;

    // Template screen variables
    private TemplateHeaderFragment templateHeaderFragment;
    private TaskTemplateRecyclerFragment taskTemplateRecyclerFragment;

    // Group screen variables
    private GroupHeaderFragment groupHeaderFragment;
    private GroupRecyclerFragment groupRecyclerFragment;

    // Data screen variables
    // private DataRecyclerFragment dataRecyclerFragment;

    // Footer
    private FooterButtonFragment footerButtonFragment;


    /*
    Create new activities
     */


    //private Fragment databaseManagementFragment;
    //private Fragment newTaskTemplateFragment;
    //private Fragment newGroupFragment;

    // Activity variables
    private int yearsAdded = 20;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        // Assign all models
        shrimpTaskViewModel = new ViewModelProvider(this).get(ShrimpTaskViewModel.class);
        taskTemplateViewModel = new ViewModelProvider(this).get(TaskTemplateViewModel.class);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        /*
        View fragments
         */

        // Empty Fragment
        emptyFragment0 = new EmptyFragment();
        emptyFragment1 = new EmptyFragment();
        emptyFragment2 = new EmptyFragment();
        emptyFragment3 = new EmptyFragment();

        // Task screen variables
        dataPreviewFragment = new DataPreviewFragment();
        taskDatePickingFragment = new TaskDatePickingFragment();
        shrimpTaskRecyclerFragmentDate = new ShrimpTaskRecyclerFragmentDate();

        // Template screen variables
        templateHeaderFragment = new TemplateHeaderFragment();
        taskTemplateRecyclerFragment = new TaskTemplateRecyclerFragment();

        // Group screen variables
        groupHeaderFragment = new GroupHeaderFragment();
        groupRecyclerFragment = new GroupRecyclerFragment();

        // Data screen variables
        // something to go here once the variable is ready

        // Footer
        footerButtonFragment = new FooterButtonFragment();

        shrimpTaskRecyclerFragmentDate.getAdapter().setOnShrimpTaskClickListener(new ShrimpTaskAdapter.OnShrimpTaskClickListener() {
            @Override
            public void onDoneButtonClick(int position) {
                ShrimpTask shrimpTask = shrimpTaskRecyclerFragmentDate.getAdapter().getShrimpTask(position);
                shrimpTask.setDisposed(true);
                shrimpTask.setDone(true);
                shrimpTaskRecyclerFragmentDate.getShrimpTaskViewModel().updateShrimpTask(shrimpTask);
            }

            @Override
            public void onNotDoneButtonClick(int position) {
                ShrimpTask shrimpTask = shrimpTaskRecyclerFragmentDate.getAdapter().getShrimpTask(position);
                shrimpTask.setDisposed(true);
                shrimpTask.setDone(false);
                shrimpTaskRecyclerFragmentDate.getShrimpTaskViewModel().updateShrimpTask(shrimpTask);
            }
        });

        displayTasksScreen();
    }

    public GroupViewModel getGroupViewModel() {
        return groupViewModel;
    }

    public ShrimpTaskViewModel getShrimpTaskViewModel() {
        return shrimpTaskViewModel;
    }

    public TaskTemplateViewModel getTaskTemplateViewModel() {
        return taskTemplateViewModel;
    }

    public void openCreateTaskDialog() {
        ShrimpTaskCreateDialog createTaskDialog = new ShrimpTaskCreateDialog();
        createTaskDialog.show(getSupportFragmentManager(), "create task dialog");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void calendarButtonListener(LocalDate pickedDate) {
        shrimpTaskRecyclerFragmentDate.setFilterDate(pickedDate);
        /*
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_2, shrimpTaskRecyclerFragmentDate)
                .commit();

         */
    }

    @Override
    public void switchModeListener(boolean isEditMode) {
        if (isEditMode) {
            // Switch to edit recycler view
        } else {
            // Switch to view recycler view
        }
    }

    @Override
    public void newTaskListener() {
        // Open new task activity
        Intent intent = new Intent(MainActivity.this, TaskCreateNewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onTaskButtonClicked() {
        // Open task fragments
        displayTasksScreen();
    }

    @Override
    public void onTemplateButtonClicked() {
        // Open templates fragments
        displayTemplatesScreen();
    }

    @Override
    public void onSettingsButtonClicked() {
        // Open settings dialog
    }

    @Override
    public void onGroupButtonClicked() {
        // Open groups fragments
        displayGroupsScreen();
    }

    @Override
    public void onDataButtonClicked() {
        // Open data fragments
        displayDataScreen();
    }

    public void displayTasksScreen() {
        fragmentManager.beginTransaction()
                .replace(R.id.container_0, dataPreviewFragment)
                .replace(R.id.container_1, taskDatePickingFragment)
                .replace(R.id.container_2, shrimpTaskRecyclerFragmentDate)
                .replace(R.id.container_3, footerButtonFragment)
                .commit();
    }

    public void displayTemplatesScreen() {
        fragmentManager.beginTransaction()
                .replace(R.id.container_0, emptyFragment0)
                .replace(R.id.container_1, templateHeaderFragment)
                .replace(R.id.container_2, taskTemplateRecyclerFragment)
                .replace(R.id.container_3, footerButtonFragment)
                .commit();
    }

    public void displayGroupsScreen() {
        fragmentManager.beginTransaction()
                .replace(R.id.container_0, emptyFragment0)
                .replace(R.id.container_1, groupHeaderFragment)
                .replace(R.id.container_2, groupRecyclerFragment)
                .replace(R.id.container_3, footerButtonFragment)
                .commit();
    }

    public void displayDataScreen() {
        fragmentManager.beginTransaction()
                .replace(R.id.container_0, emptyFragment0)
                .replace(R.id.container_1, emptyFragment1)
                .replace(R.id.container_2, shrimpTaskRecyclerFragmentDate)
                .replace(R.id.container_3, footerButtonFragment)
                .commit();
    }

    @Override
    public void newGroupListener() {

    }

    @Override
    public void newTemplateListener() {

    }
}

/*
taskTemplateRecyclerFragment.getAdapter().setOnItemClickListener(new TaskTemplateAdapter.OnTaskTemplateClickListener() {
            @Override
            public void onTaskTemplateEditClick(int position) {
                Toast.makeText(
                        getApplicationContext(),
                        taskTemplateRecyclerFragment.getAdapter().getTaskTemplate(  0).getName(),
                        Toast.LENGTH_LONG).show();

            }
        });


groupRecyclerFragment.getAdapter().setOnItemClickListener(new GroupAdapter.OnGroupTaskClickListener() {
            @Override
            public void onGroupDeleteClick(int position) {
                Group group = groupRecyclerFragment.getAdapter().getGroup(position);
                groupRecyclerFragment.getGroupViewModel().deleteItem(group.getId());
            }
        });



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            //ShrimpTask shrimpTask = new ShrimpTask("je", "parent", OffsetDateTime.now(Clock.systemDefaultZone()), "je");
            int[] dateArray = data.getIntArrayExtra(ShrimpTaskCreateNew.EXTRA_REPLY2);
            int templatePosition = data.getIntExtra(ShrimpTaskCreateNew.EXTRA_REPLY4, -1);
            ArrayList<String> batchSelectedGroups = (ArrayList<String>) data.getSerializableExtra(ShrimpTaskCreateNew.EXTRA_REPLY5);

            TaskTemplate taskTemplate = taskTemplateRecyclerFragment.getAdapter().getTaskTemplate(templatePosition);

            String proposedName = data.getStringExtra(ShrimpTaskCreateNew.EXTRA_REPLY1);

            shrimpTaskRecyclerFragmentAll.getShrimpTaskViewModel().setFilter(proposedName);

            // Log.w("myApp", dateArray.toString());


            Toast.makeText(
                    getApplicationContext(),
                    proposedName + Integer.toString(shrimpTaskRecyclerFragmentAll.getTaskNameMatchCount()),
                    Toast.LENGTH_LONG).show();



        LocalDate startDate = LocalDate.of(dateArray[0], dateArray[1] + 1, dateArray[2]);
        LocalDate endDate = LocalDate.of(dateArray[0] + yearsAdded, dateArray[1] + 1, dateArray[2]);
        int daysInterval = taskTemplate.getDaysBetweenRepeat();
        long repeatTimes = (DAYS.between(startDate, endDate)) / daysInterval;

            if (batchSelectedGroups == null) {
                    for (int i = 0; i < repeatTimes; i++) {
        ShrimpTask shrimpTask = new ShrimpTask(proposedName, taskTemplate.getName(), startDate, data.getStringExtra(ShrimpTaskCreateNew.EXTRA_REPLY3));

        shrimpTaskRecyclerFragmentAll.getShrimpTaskViewModel().insert(shrimpTask);

        startDate = startDate.plusDays(daysInterval);
        }
        } else {
        for (int j = 0; j < batchSelectedGroups.size(); j++) {
        String inBatchName = proposedName + " " + batchSelectedGroups.get(j);
        LocalDate inBatchStartDate = startDate;

        for (int i = 0; i < repeatTimes; i++) {
        ShrimpTask shrimpTask = new ShrimpTask(inBatchName, taskTemplate.getName(), inBatchStartDate, data.getStringExtra(ShrimpTaskCreateNew.EXTRA_REPLY3));

        shrimpTaskRecyclerFragmentAll.getShrimpTaskViewModel().insert(shrimpTask);

        inBatchStartDate = inBatchStartDate.plusDays(daysInterval);
        }
        }
        }

        getSupportFragmentManager().beginTransaction()
        .replace(R.id.middle_container, shrimpTaskRecyclerFragmentDate)
        .commit();

        } else {
        Toast.makeText(
        getApplicationContext(),
        R.string.empty_not_saved,
        Toast.LENGTH_LONG).show();
        }
        }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onGroupCreateNewButtonClicked(String name) {
        groupRecyclerFragment.getGroupViewModel().insert(new Group(name, "0"));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middle_container, groupRecyclerFragment)
                .commit();
    }



    @Override
    public void onTaskTemplateButtonClicked(String name, String description, int daysBetweenRepeat, boolean repeat) {
        TaskTemplate taskTemplate = new TaskTemplate(name, name, description, daysBetweenRepeat, repeat);

        taskTemplateRecyclerFragment.getTaskTemplateViewModel().insert(taskTemplate);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middle_container, taskTemplateRecyclerFragment)
                .commit();
    }

 */