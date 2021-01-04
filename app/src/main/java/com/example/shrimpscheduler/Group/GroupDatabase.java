package com.example.shrimpscheduler.Group;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Group.class}, version = 1, exportSchema = false)
public abstract class GroupDatabase extends RoomDatabase {
    public abstract GroupDao groupDao();

    private static volatile GroupDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static GroupDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GroupDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GroupDatabase.class, "group")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}