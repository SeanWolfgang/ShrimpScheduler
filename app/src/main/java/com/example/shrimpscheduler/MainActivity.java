package com.example.shrimpscheduler;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.time.Clock;
import java.time.OffsetDateTime;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    private ShrimpTaskViewModel shrimpTaskViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final ShrimpTaskAdapter adapter = new ShrimpTaskAdapter(new ShrimpTaskAdapter.ShrimpTaskDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shrimpTaskViewModel = new ViewModelProvider(this).get(ShrimpTaskViewModel.class);

        shrimpTaskViewModel.getAllShrimpTasks().observe(this, shrimpTasks -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(shrimpTasks);
        });

        fab = findViewById(R.id.fab);

        fab.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this, CreateTask.class);
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
            ShrimpTask shrimpTask = new ShrimpTask(data.getStringExtra(CreateTask.EXTRA_REPLY1), "parent", OffsetDateTime.now(Clock.systemDefaultZone()), data.getStringExtra(CreateTask.EXTRA_REPLY2));
            shrimpTaskViewModel.insert(shrimpTask);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
