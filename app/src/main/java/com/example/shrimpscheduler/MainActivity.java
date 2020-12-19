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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class MainActivity extends AppCompatActivity
    implements ButtonRibbonFragment.ButtonRibbonFragmentListener,
        DatabaseManagementFragment.DataBaseManagementFragmentListener,
        TaskTemplateCreateNew.TaskTemplateFragmentListener {

    FloatingActionButton fab;
    private ShrimpTaskViewModel shrimpTaskViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private ShrimpTaskRecyclerFragmentAll shrimpTaskRecyclerFragmentAll;
    private ShrimpTaskRecyclerFragmentDate shrimpTaskRecyclerFragmentToday;
    private ShrimpTaskRecyclerFragmentDate shrimpTaskRecyclerFragmentDate;
    private TaskTemplateRecyclerFragment taskTemplateRecyclerFragment;
    private Fragment buttonRibbonFragment;
    private Fragment dataPreviewFragment;

    private Fragment databaseManagementFragment;
    private Fragment newTaskTemplateFragment;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shrimpTaskRecyclerFragmentAll = new ShrimpTaskRecyclerFragmentAll();
        shrimpTaskRecyclerFragmentToday = new ShrimpTaskRecyclerFragmentDate();
        shrimpTaskRecyclerFragmentDate = new ShrimpTaskRecyclerFragmentDate();
        taskTemplateRecyclerFragment = new TaskTemplateRecyclerFragment();
        buttonRibbonFragment = new ButtonRibbonFragment();
        dataPreviewFragment = new DataPreviewFragment();
        newTaskTemplateFragment = new TaskTemplateCreateNew();

        taskTemplateRecyclerFragment.getAdapter().setOnItemClickListener(new TaskTemplateAdapter.OnTaskTemplateClickListener() {
            @Override
            public void onTaskTemplateEditClick(int position) {
                Toast.makeText(
                        getApplicationContext(),
                        taskTemplateRecyclerFragment.getAdapter().getTaskTemplate(  0).getName(),
                        Toast.LENGTH_LONG).show();
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
                .replace(R.id.middle_container, shrimpTaskRecyclerFragmentDate)
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
            LocalDate endDate = LocalDate.of(dateArray[0] + 1, dateArray[1] + 1, dateArray[2]);
            int daysInterval = taskTemplate.getDaysBetweenRepeat();
            long repeatTimes = (DAYS.between(startDate, endDate)) / daysInterval;

            for (int i = 0; i < repeatTimes; i++) {
                ShrimpTask shrimpTask = new ShrimpTask(proposedName, taskTemplate.getName(), startDate, data.getStringExtra(ShrimpTaskCreateNew.EXTRA_REPLY3));

                shrimpTaskRecyclerFragmentAll.getShrimpTaskViewModel().insert(shrimpTask);

                startDate = startDate.plusDays(daysInterval);
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
            case "DataHub":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, shrimpTaskRecyclerFragmentAll)
                        .commit();
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
}
