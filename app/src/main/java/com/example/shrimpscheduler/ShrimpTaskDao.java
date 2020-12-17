package com.example.shrimpscheduler;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface ShrimpTaskDao {
    @Query("SELECT * FROM shrimptask ORDER BY execute_time asc, id")
    LiveData<List<ShrimpTask>> getAllShrimpTasks();

    @Query("SELECT * FROM shrimptask WHERE execute_time = :date ORDER BY id")
    LiveData<List<ShrimpTask>> getShrimpTaskDate(LocalDate date);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(ShrimpTask shrimpTasks);

    @Query("DELETE FROM shrimptask")
    public void deleteAll();

    @Query("SELECT * FROM shrimptask WHERE id == :id")
    public ShrimpTask selectShrimpTaskID(int id);

    @Update
    public void updateShrimpTask(ShrimpTask... shrimpTasks);
}
