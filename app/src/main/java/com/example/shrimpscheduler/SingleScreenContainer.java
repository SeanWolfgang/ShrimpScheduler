package com.example.shrimpscheduler;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class SingleScreenContainer extends AppCompatActivity {
    public static final String MANAGE_TASK_BUTTON_LABEL = "ManageTask";
    public static final int MANAGE_TASK_BUTTON_CODE = 0;

    private Fragment buttonRibbonFragment;
    private Fragment databaseManagementFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_screen_container);

        buttonRibbonFragment = new ButtonRibbonFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.footer_container, buttonRibbonFragment)
                .commit();

        int buttonCode = getIntent().getIntExtra(MANAGE_TASK_BUTTON_LABEL,-1);

        switch (buttonCode) {
            case MANAGE_TASK_BUTTON_CODE:
                databaseManagementFragment = new DatabaseManagementFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, databaseManagementFragment)
                        .commit();
                break;
        }
    }
}
