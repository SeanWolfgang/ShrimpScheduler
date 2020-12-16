package com.example.shrimpscheduler;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ShrimpTask.class}, version = 1, exportSchema = false)
@TypeConverters({LocalDateTypeConverters.class})
public abstract class ShrimpTaskDatabase extends RoomDatabase {
    public abstract ShrimpTaskDao shrimpTaskDao();

    private static volatile ShrimpTaskDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ShrimpTaskDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShrimpTaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ShrimpTaskDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}