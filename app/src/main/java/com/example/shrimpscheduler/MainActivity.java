package com.example.shrimpscheduler;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.shrimpscheduler.CreateGroup.GroupCreateNewActivity;
import com.example.shrimpscheduler.CreateGroup.GroupEditActivity;
import com.example.shrimpscheduler.CreateTask.TaskCreateNewActivity;
import com.example.shrimpscheduler.CreateTask.TaskEditActivity;
import com.example.shrimpscheduler.CreateTemplate.TemplateCreateNewActivity;
import com.example.shrimpscheduler.CreateTemplate.TemplateEditActivity;
import com.example.shrimpscheduler.Group.Group;
import com.example.shrimpscheduler.Group.GroupAdapter;
import com.example.shrimpscheduler.Group.GroupViewModel;
import com.example.shrimpscheduler.MainFragments.DataPreviewFragment;
import com.example.shrimpscheduler.MainFragments.EmptyFragment;
import com.example.shrimpscheduler.MainFragments.FooterButtonFragment;
import com.example.shrimpscheduler.MainFragments.GroupHeaderFragment;
import com.example.shrimpscheduler.MainFragments.GroupRecyclerFragment;
import com.example.shrimpscheduler.MainFragments.SettingsFragment;
import com.example.shrimpscheduler.MainFragments.ShrimpTaskEditRecyclerFragment;
import com.example.shrimpscheduler.MainFragments.ShrimpTaskRecyclerFragmentDate;
import com.example.shrimpscheduler.MainFragments.TaskDatePickingFragment;
import com.example.shrimpscheduler.MainFragments.TaskTemplateRecyclerFragment;
import com.example.shrimpscheduler.MainFragments.TemplateHeaderFragment;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTask;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskAdapter;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskEditAdapter;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskViewModel;
import com.example.shrimpscheduler.Template.TaskTemplate;
import com.example.shrimpscheduler.Template.TaskTemplateAdapter;
import com.example.shrimpscheduler.Template.TaskTemplateViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements TaskDatePickingFragment.TaskDatePickingListener,
        FooterButtonFragment.FooterButtonFragmentListener,
        GroupHeaderFragment.GroupHeaderFragmentListener,
        TemplateHeaderFragment.TemplateHeaderFragmentListener,
        SettingsFragment.SettingsFragmentListener{

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    // Extras for passing to task edit activity
    public static final String EXTRA_1 = "TEMPLATE_NAME";
    public static final String EXTRA_2 = "TASK_NAME";
    public static final String EXTRA_3 = "TASK_DESCRIPTION";
    public static final String EXTRA_4 = "TASK_DATE";
    public static final String EXTRA_5 = "TASK_ID";

    // Extras for passing to group edit activity
    public static final String EXTRA_6 = "GROUP_NAME";
    public static final String EXTRA_7 = "GROUP_ID";

    // Extras for passing to template edit activity
    public static final String EXTRA_8 = "TEMPLATE_NAME";
    public static final String EXTRA_9 = "TEMPLATE_DESCRIPTION";
    public static final String EXTRA_10 = "TEMPLATE_REPEAT";
    public static final String EXTRA_11 = "TEMPLATE_INTERVAL";
    public static final String EXTRA_12 = "TEMPLATE_ID";

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
    private ShrimpTaskEditRecyclerFragment shrimpTaskEditRecyclerFragment;

    // Template screen variables
    private TemplateHeaderFragment templateHeaderFragment;
    private TaskTemplateRecyclerFragment taskTemplateRecyclerFragment;

    // Settings screen variables
    private SettingsFragment settingsFragment;

    // Group screen variables
    private GroupHeaderFragment groupHeaderFragment;
    private GroupRecyclerFragment groupRecyclerFragment;

    // Data screen variables
    // private DataRecyclerFragment dataRecyclerFragment;

    // Footer
    private FooterButtonFragment footerButtonFragment;

    /*
    Settings variables
    */
    private ArrayList<ShrimpTask> allShrimpTasks = new ArrayList<>();

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
        shrimpTaskEditRecyclerFragment = new ShrimpTaskEditRecyclerFragment();

        // Template screen variables
        templateHeaderFragment = new TemplateHeaderFragment();
        taskTemplateRecyclerFragment = new TaskTemplateRecyclerFragment();

        // Settings screen variables
        settingsFragment = new SettingsFragment();

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
                displayTextScreen(shrimpTask.getParentName());
            }

            @Override
            public void onNotDoneButtonClick(int position) {
                ShrimpTask shrimpTask = shrimpTaskRecyclerFragmentDate.getAdapter().getShrimpTask(position);
                shrimpTask.setDisposed(true);
                shrimpTask.setDone(false);
                shrimpTaskRecyclerFragmentDate.getShrimpTaskViewModel().updateShrimpTask(shrimpTask);
            }
        });

        shrimpTaskEditRecyclerFragment.getAdapter().setOnShrimpTaskEditClickListener(new ShrimpTaskEditAdapter.OnShrimpTaskEditClickListener() {
            @Override
            public void onEditButtonClick(int position) {
                Intent intent = new Intent(MainActivity.this, TaskEditActivity.class);

                ShrimpTask shrimpTask = shrimpTaskEditRecyclerFragment.getAdapter().getShrimpTask(position);

                LocalDate executeDate = shrimpTask.getExecuteTime();

                int[] sendDate = new int[3];
                sendDate[0] = executeDate.getYear();
                sendDate[1] = executeDate.getMonthValue();
                sendDate[2] = executeDate.getDayOfMonth();

                intent.putExtra(EXTRA_1, shrimpTask.getParentName());
                intent.putExtra(EXTRA_2, shrimpTask.getName());
                intent.putExtra(EXTRA_3, shrimpTask.getDescription());
                intent.putExtra(EXTRA_4, sendDate);
                intent.putExtra(EXTRA_5, shrimpTask.getId());

                startActivity(intent);
            }

            @Override
            public void onDeleteButtonClick(int position) {
                ShrimpTask shrimpTask = shrimpTaskRecyclerFragmentDate.getAdapter().getShrimpTask(position);
                shrimpTaskEditRecyclerFragment.getShrimpTaskViewModel().deleteItem(shrimpTask.getId());
            }
        });

        groupRecyclerFragment.getAdapter().setOnItemClickListener(new GroupAdapter.OnGroupTaskClickListener() {

            @Override
            public void onGroupEditClick(int position) {
                Intent intent = new Intent(MainActivity.this, GroupEditActivity.class);

                Group group = groupRecyclerFragment.getAdapter().getGroup(position);

                intent.putExtra(EXTRA_6, group.getName());
                intent.putExtra(EXTRA_7, group.getId());

                startActivity(intent);
            }

            @Override
            public void onGroupDeleteClick(int position) {
                Group group = groupRecyclerFragment.getAdapter().getGroup(position);
                groupRecyclerFragment.getGroupViewModel().deleteItem(group.getId());
            }
        });

        taskTemplateRecyclerFragment.getAdapter().setOnItemClickListener(new TaskTemplateAdapter.OnTaskTemplateClickListener() {

            @Override
            public void onTaskTemplateEditClick(int position) {
                Intent intent = new Intent(MainActivity.this, TemplateEditActivity.class);

                TaskTemplate template = taskTemplateRecyclerFragment.getAdapter().getTaskTemplate(position);

                intent.putExtra(EXTRA_8, template.getName());
                intent.putExtra(EXTRA_9, template.getDefaultDescription());
                intent.putExtra(EXTRA_10, template.isRepeat());
                intent.putExtra(EXTRA_11, template.getDaysBetweenRepeat());
                intent.putExtra(EXTRA_12, template.getId());

                startActivity(intent);
            }

            @Override
            public void onTaskTemplateDeleteClick(int position) {
                TaskTemplate template = taskTemplateRecyclerFragment.getAdapter().getTaskTemplate(position);
                taskTemplateRecyclerFragment.getTaskTemplateViewModel().deleteItem(template.getId());
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void calendarButtonListener(LocalDate pickedDate) {
        shrimpTaskRecyclerFragmentDate.setFilterDate(pickedDate);
        shrimpTaskEditRecyclerFragment.setFilterDate(pickedDate);
    }

    @Override
    public void switchModeListener(boolean isEditMode) {
        switchTaskEditMode(isEditMode);
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
        displaySettingsScreen();
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

    public void displaySettingsScreen() {
        fragmentManager.beginTransaction()
                .replace(R.id.container_0, emptyFragment0)
                .replace(R.id.container_1, emptyFragment1)
                .replace(R.id.container_2, settingsFragment)
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
                .replace(R.id.container_2, shrimpTaskEditRecyclerFragment)
                .replace(R.id.container_3, footerButtonFragment)
                .commit();
    }

    @Override
    public void newGroupListener() {
        Intent intent = new Intent(MainActivity.this, GroupCreateNewActivity.class);
        startActivity(intent);
    }

    @Override
    public void newTemplateListener() {
        Intent intent = new Intent(MainActivity.this, TemplateCreateNewActivity.class);
        startActivity(intent);
    }

    private void switchTaskEditMode(boolean editMode) {
        if (editMode) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container_2, shrimpTaskRecyclerFragmentDate)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.container_2, shrimpTaskEditRecyclerFragment)
                    .commit();
        }
    }

    private void displayTextScreen(String inputText) {
        Toast.makeText(
                getApplicationContext(),
                inputText,
                Toast.LENGTH_LONG).show();
    }

    /*
    Settings functions
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDeleteAllTasksClicked() {
        shrimpTaskViewModel.deleteAll();
    }

    @Override
    public void onDeleteAllTemplatesClicked() {
        taskTemplateViewModel.deleteAll();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDeleteAllGroupsClicked() {
        groupViewModel.deleteAll();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDeleteFutureTasksNameClicked() {
        ArrayList<String> selectedNames = new ArrayList<>();
        LocalDate effectiveDate = settingsFragment.getEffectiveDate();

        for (Map.Entry<String, Boolean> entry : settingsFragment.getDistinctTaskNamesCheckedStates().entrySet()) {
            if (entry.getValue()) {
                selectedNames.add(entry.getKey());
            }
        }

        for (ShrimpTask eachTask : settingsFragment.getAllShrimpTasks()) {
            if (selectedNames.contains(eachTask.getName()) && eachTask.getExecuteTime().compareTo(effectiveDate) >= 0) {
                shrimpTaskViewModel.deleteItem(eachTask.getId());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDeleteFutureTasksTemplateClicked() {
        ArrayList<String> selectedTemplates = new ArrayList<>();
        LocalDate effectiveDate = settingsFragment.getEffectiveDate();

        for (Map.Entry<String, Boolean> entry : settingsFragment.getDistinctTemplateNamesCheckedStates().entrySet()) {
            if (entry.getValue()) {
                selectedTemplates.add(entry.getKey());
            }
        }

        for (ShrimpTask eachTask : settingsFragment.getAllShrimpTasks()) {
            if (selectedTemplates.contains(eachTask.getParentName()) && eachTask.getExecuteTime().compareTo(effectiveDate) >= 0) {
                shrimpTaskViewModel.deleteItem(eachTask.getId());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDeleteFutureTasksGroupClicked() {
        ArrayList<String> selectedGroups = new ArrayList<>();
        LocalDate effectiveDate = settingsFragment.getEffectiveDate();

        for (Map.Entry<String, Boolean> entry : settingsFragment.getDistinctGroupNamesCheckedStates().entrySet()) {
            if (entry.getValue()) {
                selectedGroups.add(entry.getKey());
            }
        }

        for (ShrimpTask eachTask : settingsFragment.getAllShrimpTasks()) {
            if (selectedGroups.contains(eachTask.getGroup()) && eachTask.getExecuteTime().compareTo(effectiveDate) >= 0) {
                shrimpTaskViewModel.deleteItem(eachTask.getId());
            }
        }
    }
}