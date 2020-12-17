package com.example.shrimpscheduler;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity
    implements ButtonRibbonFragment.ButtonRibbonFragmentListener,
        DatabaseManagementFragment.DataBaseManagementFragmentListener,
        TaskTemplateCreateNew.TaskTemplateFragmentListener {

    FloatingActionButton fab;
    private ShrimpTaskViewModel shrimpTaskViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private ShrimpTaskRecyclerFragment shrimpTaskRecyclerFragment;
    private TaskTemplateRecyclerFragment taskTemplateRecyclerFragment;
    private Fragment buttonRibbonFragment;
    private Fragment dataPreviewFragment;

    private Fragment databaseManagementFragment;
    private Fragment newTaskTemplateFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shrimpTaskRecyclerFragment = new ShrimpTaskRecyclerFragment();
        taskTemplateRecyclerFragment = new TaskTemplateRecyclerFragment();
        buttonRibbonFragment = new ButtonRibbonFragment();
        dataPreviewFragment = new DataPreviewFragment();
        newTaskTemplateFragment = new TaskTemplateCreateNew();

        taskTemplateRecyclerFragment.getAdapter().setOnItemClickListener(new TaskTemplateAdapter.OnTaskTemplateClickListener() {
            @Override
            public void onTaskTemplateEditClick(int position) {
                Toast.makeText(
                        getApplicationContext(),
                        "Clicked edit.",
                        Toast.LENGTH_LONG).show();
            }


        });

        shrimpTaskRecyclerFragment.getAdapter().setOnShrimpTaskClickListener(new ShrimpTaskAdapter.OnShrimpTaskClickListener() {
            @Override
            public void onDoneButtonClick(int position) {
                ShrimpTask shrimpTask = shrimpTaskRecyclerFragment.getAdapter().getShrimpTask(position);
                shrimpTask.setDisposed(true);
                shrimpTask.setDone(true);
                shrimpTaskRecyclerFragment.getShrimpTaskViewModel().updateShrimpTask(shrimpTask);
            }

            @Override
            public void onNotDoneButtonClick(int position) {
                ShrimpTask shrimpTask = shrimpTaskRecyclerFragment.getAdapter().getShrimpTask(position);
                shrimpTask.setDisposed(true);
                shrimpTask.setDone(false);
                shrimpTaskRecyclerFragment.getShrimpTaskViewModel().updateShrimpTask(shrimpTask);
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.top_container, dataPreviewFragment)
                .replace(R.id.middle_container, taskTemplateRecyclerFragment)
                .replace(R.id.bottom_container, buttonRibbonFragment)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middle_container, shrimpTaskRecyclerFragment)
                .commit();

        fab = findViewById(R.id.fab);

        fab.setOnClickListener( view -> {
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

            // Log.w("myApp", dateArray.toString());

            LocalDate startDateTime = LocalDate.of(dateArray[0], dateArray[1] + 1, dateArray[2]);

            ShrimpTask shrimpTask = new ShrimpTask(data.getStringExtra(ShrimpTaskCreateNew.EXTRA_REPLY1), "parent", startDateTime, data.getStringExtra(ShrimpTaskCreateNew.EXTRA_REPLY3));

            shrimpTaskRecyclerFragment.getShrimpTaskViewModel().insert(shrimpTask);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onButtonClicked(String input) {
        switch (input) {
            case "ViewTodayTasks":
                shrimpTaskRecyclerFragment.getShrimpTaskViewModel().getShrimpTaskDate(LocalDate.now()).observe(shrimpTaskRecyclerFragment, dateShrimpTasks -> {
                    // Update the cached copy of the words in the adapter.
                    shrimpTaskRecyclerFragment.getAdapter().submitList(dateShrimpTasks);
                });
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, shrimpTaskRecyclerFragment)
                        .commit();
                break;
            case "ViewAllTasks":
                shrimpTaskRecyclerFragment.getShrimpTaskViewModel().getAllShrimpTasks().observe(shrimpTaskRecyclerFragment, shrimpTasks -> {
                    // Update the cached copy of the words in the adapter.
                    shrimpTaskRecyclerFragment.getAdapter().submitList(shrimpTasks);
                });
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, shrimpTaskRecyclerFragment)
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
                        .replace(R.id.middle_container, shrimpTaskRecyclerFragment)
                        .commit();
                break;
        }
    }

    @Override
    public void onDBManageButtonClicked(String input) {
        switch (input) {
            case "DeleteAll":
                shrimpTaskRecyclerFragment.getShrimpTaskViewModel().deleteAll();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, shrimpTaskRecyclerFragment)
                        .commit();
                break;
            case "DeleteFuture":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, shrimpTaskRecyclerFragment)
                        .commit();
                break;
            case "DeleteTemplates":
                taskTemplateRecyclerFragment.getTaskTemplateViewModel().deleteAll();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.middle_container, shrimpTaskRecyclerFragment)
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
}
