package com.example.shrimpscheduler;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TaskTemplate.class}, version = 1, exportSchema = false)
public abstract class TaskTemplateDatabase  extends RoomDatabase {
    public abstract TaskTemplateDao taskTemplateDao();

    private static volatile TaskTemplateDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TaskTemplateDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskTemplateDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskTemplateDatabase.class, "task_template_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}