package com.example.shrimpscheduler;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShrimpTaskDao {
    @Query("SELECT * FROM shrimptask")
    LiveData<List<ShrimpTask>> getAllShrimpTasks();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ShrimpTask shrimpTasks);
}
