package com.example.shrimpscheduler.ShrimpTask;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ShrimpTask.class}, version = 2, exportSchema = false)
@TypeConverters({LocalDateTypeConverters.class})
public abstract class ShrimpTaskDatabase extends RoomDatabase {
    public abstract ShrimpTaskDao shrimpTaskDao();

    private static volatile ShrimpTaskDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static Migration migrationOneTwo = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'shrimp_task_database' ADD COLUMN 'group' TEXT");
        }
    };

    static ShrimpTaskDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShrimpTaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ShrimpTaskDatabase.class, "shrimptask")
                            .addMigrations(migrationOneTwo)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}