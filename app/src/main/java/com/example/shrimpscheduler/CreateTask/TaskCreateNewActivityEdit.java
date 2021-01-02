package com.example.shrimpscheduler.CreateTask;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class TaskCreateNewActivityEdit extends TaskCreateNewActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void confirmButtonListener() {
        super.confirmButtonListener();
    }
}