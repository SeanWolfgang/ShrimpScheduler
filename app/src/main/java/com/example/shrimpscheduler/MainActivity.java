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
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class MainActivity extends AppCompatActivity
    implements ButtonRibbonFragment.ButtonRibbonFragmentListener,
        DatabaseManagementFragment.DataBaseManagementFragmentListener,
        TaskTemplateCreateNew.TaskTemplateFragmentListener,
        GroupCreateNew.GroupCreateNewFragmentListener,
        ShrimpTaskCreateDialog.ShrimpTaskCreateDialogListener {

    FloatingActionButton fab;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private ShrimpTaskRecyclerFragmentAll shrimpTaskRecyclerFragmentAll;
    private ShrimpTaskRecyclerFragmentDate shrimpTaskRecyclerFragmentToday;
    private ShrimpTaskRecyclerFragmentDate shrimpTaskRecyclerFragmentDate;
    private ShrimpTaskPagedListRecyclerFragment shrimpTaskPagedListRecyclerFragment;
    private TaskTemplateRecyclerFragment taskTemplateRecyclerFragment;
    private GroupRecyclerFragment groupRecyclerFragment;
    private Fragment buttonRibbonFragment;
    private Fragment dataPreviewFragment;

    private Fragment databaseManagementFragment;
    private Fragment newTaskTemplateFragment;
    private Fragment newGroupFragment;

    private int yearsAdded = 20;

    private ShrimpTaskViewModel shrimpTaskViewModel;
    private TaskTemplateViewModel taskTemplateViewModel;
    private GroupViewModel groupViewModel;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign all groups
        shrimpTaskViewModel = new ViewModelProvider(this).get(ShrimpTaskViewModel.class);
        taskTemplateViewModel = new ViewModelProvider(this).get(TaskTemplateViewModel.class);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        shrimpTaskRecyclerFragmentAll = new ShrimpTaskRecyclerFragmentAll();
        shrimpTaskRecyclerFragmentToday = new ShrimpTaskRecyclerFragmentDate();
        shrimpTaskRecyclerFragmentDate = new ShrimpTaskRecyclerFragmentDate();
        shrimpTaskPagedListRecyclerFragment = new ShrimpTaskPagedListRecyclerFragment();
        taskTemplateRecyclerFragment = new TaskTemplateRecyclerFragment();
        groupRecyclerFragment = new GroupRecyclerFragment();
        buttonRibbonFragment = new ButtonRibbonFragment();
        dataPreviewFragment = new DataPreviewFragment();
        newTaskTemplateFragment = new TaskTemplateCreateNew();
        newGroupFragment = new GroupCreateNew();

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

        shrimpTaskRecyclerFragmentAll.getAdapter().setOnShrimpTaskClickListener(new ShrimpTaskAdapter.OnShrimpTaskClickListener() {
            @Override
            public void onDoneButtonClick(int position) {
                ShrimpTask shrimpTask = shrimpTaskRecyclerFragmentAll.getAdapter().getShrimpTask(position);
                shrimpTask.setDisposed(true);
                shrimpTask.setDone(true);
                shrimpTaskRecyclerFragmentAll.getShrimpTaskViewModel().updateShrimpTask(shrimpTask);
                // Log.w("myApp", dateArray.toString());

                /*
                Toast.makeText(
                        getApplicationContext(),
                        shrimpTask.getName() + Integer.toString(shrimpTaskRecyclerFragmentAll.getTaskNameMatchCount()),
                        Toast.LENGTH_LONG).show();

                 */
            }

            @Override
            public void onNotDoneButtonClick(int position) {
                ShrimpTask shrimpTask = shrimpTaskRecyclerFragmentAll.getAdapter().getShrimpTask(position);
                shrimpTask.setDisposed(true);
                shrimpTask.setDone(false);
                shrimpTaskRecyclerFragmentAll.getShrimpTaskViewModel().updateShrimpTask(shrimpTask);
            }
        });

        shrimpTaskRecyclerFragmentToday.getAdapter().setOnShrimpTaskClickListener(new ShrimpTaskAdapter.OnShrimpTaskClickListener() {
            @Override
            public void onDoneButtonClick(int position) {
                ShrimpTask shrimpTask = shrimpTaskRecyclerFragmentToday.getAdapter().getShrimpTask(position);
                shrimpTask.setDisposed(true);
                shrimpTask.setDone(true);
                shrimpTaskRecyclerFragmentToday.getShrimpTaskViewModel().updateShrimpTask(shrimpTask);
                // Log.w("myApp", dateArray.toString());

                /*
                Toast.makeText(
                        getApplicationContext(),
                        shrimpTask.getName() + Integer.toString(shrimpTaskRecyclerFragmentToday.getTaskNameMatchCount()),
                        Toast.LENGTH_LONG).show();

                 */
            }

            @Override
            public void onNotDoneButtonClick(int position) {
                ShrimpTask shrimpTask = shrimpTaskRecyclerFragmentToday.getAdapter().getShrimpTask(position);
                shrimpTask.setDisposed(true);
                shrimpTask.setDone(false);
                shrimpTaskRecyclerFragmentToday.getShrimpTaskViewModel().updateShrimpTask(shrimpTask);
            }
        });


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

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.top_container, dataPreviewFragment)
                .replace(R.id.middle_container, taskTemplateRecyclerFragment)
                .replace(R.id.bottom_container, buttonRibbonFragment)
                .commit();


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middle_container, shrimpTaskRecyclerFragmentAll)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middle_container, shrimpTaskPagedListRecyclerFragment)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middle_container, shrimpTaskRecyclerFragmentDate)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middle_container, groupRecyclerFragment)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middle_container, shrimpTaskRecyclerFragmentToday)
                .commit();

        fab = findViewById(R.id.fab);

        fab.setOnClickListener( view -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.middle_container, taskTemplateRecyclerFragment)
                    .commit();

            Intent intent = new Intent(MainActivity.this, ShrimpTaskCreateNew.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

            /*
            Toast.makeText(
                    getApplicationContext(),
                    proposedName + Integer.toString(shrimpTaskRecyclerFragmentAll.getTaskNameMatchCount()),
                    Toast.LENGTH_LONG).show();

             */



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
    public void onDBManageButtonClicked(String input) {
        switch (input) {
            case "DeleteAll":
                shrimpTaskRecyclerFragmentAll.getShrimpTaskViewModel().deleteAll();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, shrimpTaskRecyclerFragmentAll)
                        .commit();
                break;
            case "DeleteFuture":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, shrimpTaskRecyclerFragmentAll)
                        .commit();
                break;
            case "DeleteTemplates":
                taskTemplateRecyclerFragment.getTaskTemplateViewModel().deleteAll();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, taskTemplateRecyclerFragment)
                        .commit();
                break;
            case "DeleteGroups":
                groupRecyclerFragment.getGroupViewModel().deleteAll();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, shrimpTaskRecyclerFragmentToday)
                        .commit();
                break;
        }
    }

    @Override
    public void onTaskTemplateButtonClicked(String name, String description, int daysBetweenRepeat, boolean repeat) {
        TaskTemplate taskTemplate = new TaskTemplate(name, name, description, daysBetweenRepeat, repeat);

        taskTemplateRecyclerFragment.getTaskTemplateViewModel().insert(taskTemplate);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middle_container, taskTemplateRecyclerFragment)
                .commit();
    }

    @Override
    public void onButtonClicked(String buttonTitle) {
        switch (buttonTitle) {
            case "ViewTodayTasks":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, shrimpTaskRecyclerFragmentToday)
                        .commit();
                break;
            case "ViewAllTasks":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, shrimpTaskRecyclerFragmentAll)
                        .commit();
                break;
            case "ViewPagedTasks":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, shrimpTaskPagedListRecyclerFragment)
                        .commit();
                break;
            case "ViewTemplates":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, taskTemplateRecyclerFragment)
                        .commit();
                break;
            case "ManageTasks":
                databaseManagementFragment = new DatabaseManagementFragment();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, databaseManagementFragment)
                        .commit();
                break;
            case "ManageTemplates":
                databaseManagementFragment = new DatabaseManagementFragment();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, databaseManagementFragment)
                        .commit();
                break;
            case "NewTaskTemplate":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, newTaskTemplateFragment)
                        .commit();
                break;
            case "NewGroup":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, newGroupFragment)
                        .commit();
                break;
            case "DataHub":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, shrimpTaskRecyclerFragmentAll)
                        .commit();
                break;
             case "ViewGroups":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, groupRecyclerFragment)
                        .commit();
                break;
            case "CreateTaskFragment":
                openCreateTaskDialog();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateTasksButtonClicked(LocalDate date) {
        shrimpTaskRecyclerFragmentDate.setFilterDate(date);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middle_container, shrimpTaskRecyclerFragmentDate)
                .commit();

        Toast.makeText(
                getApplicationContext(),
                date.toString(),
                Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onGroupCreateNewButtonClicked(String name) {
        groupRecyclerFragment.getGroupViewModel().insert(new Group(name, "0"));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middle_container, groupRecyclerFragment)
                .commit();
    }

    public GroupViewModel getGroupViewModel() {
        return groupViewModel;
    }

    public void setGroupViewModel(GroupViewModel groupViewModel) {
        this.groupViewModel = groupViewModel;
    }


    public ShrimpTaskViewModel getShrimpTaskViewModel() {
        return shrimpTaskViewModel;
    }

    public void setShrimpTaskViewModel(ShrimpTaskViewModel shrimpTaskViewModel) {
        this.shrimpTaskViewModel = shrimpTaskViewModel;
    }

    public TaskTemplateViewModel getTaskTemplateViewModel() {
        return taskTemplateViewModel;
    }

    public void setTaskTemplateViewModel(TaskTemplateViewModel taskTemplateViewModel) {
        this.taskTemplateViewModel = taskTemplateViewModel;
    }

    public void openCreateTaskDialog() {
        ShrimpTaskCreateDialog createTaskDialog = new ShrimpTaskCreateDialog();
        createTaskDialog.show(getSupportFragmentManager(), "create task dialog");
    }


    @Override
    public void makeShrimpTask(String name, String description, LocalDate startDate, String templateName, String[] groupNames) {

    }
}


