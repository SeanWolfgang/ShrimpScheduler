package com.example.shrimpscheduler.ShrimpTaskPack;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shrimpscheduler.ShrimpTaskPack.ShrimpTask;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface ShrimpTaskDao {
    @Query("SELECT * FROM shrimptask ORDER BY execute_time asc, id")
    LiveData<List<ShrimpTask>> getAllShrimpTasks();

    @Query("SELECT name FROM ( SELECT *, max(execute_time) FROM shrimptask WHERE execute_time >= :date GROUP BY name ORDER BY name)")
    LiveData<List<String>> getDistinctShrimpTaskNames(LocalDate date);

    @Query("SELECT execute_time FROM ( SELECT *, max(execute_time) FROM shrimptask WHERE execute_time >= :date GROUP BY name ORDER BY name)")
    LiveData<List<LocalDate>> getLastExecuteDate(LocalDate date);

    @Query("SELECT * FROM shrimptask ORDER BY execute_time asc, id")
    DataSource.Factory<Integer, ShrimpTask> getPagedAllShrimpTasks();

    @Query("SELECT * FROM shrimptask WHERE name LIKE :name ORDER BY execute_time asc, id")
    DataSource.Factory<Integer, ShrimpTask> getNameShrimpTasks(String name);

    @Query("SELECT * FROM shrimptask WHERE execute_time = :date ORDER BY id")
    LiveData<List<ShrimpTask>> getShrimpTaskDate(LocalDate date);

    @Query("SELECT * FROM shrimptask WHERE execute_time = :date ORDER BY id")
    LiveData<List<ShrimpTask>> getShrimpTaskToday(LocalDate date);

    @Query("SELECT COUNT(id) FROM shrimptask WHERE name = :name")
    LiveData<Integer> getShrimpTasksNameMatch(String name);

    @Query("SELECT COUNT(id) FROM shrimptask")
    LiveData<Integer> getCountShrimpTask();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(ShrimpTask shrimpTasks);

    @Query("DELETE FROM shrimptask")
    public void deleteAll();

    @Query("SELECT * FROM shrimptask WHERE id == :id")
    public ShrimpTask selectShrimpTaskID(int id);

    @Update
    public void updateShrimpTask(ShrimpTask... shrimpTasks);
}
