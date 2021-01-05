package com.example.shrimpscheduler.ShrimpTask;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
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

    @Query("SELECT * FROM shrimptask WHERE execute_time >= :beginDate AND execute_time <= :endDate ORDER BY id")
    LiveData<List<ShrimpTask>> getShrimpTaskDateRange(LocalDate beginDate, LocalDate endDate);

    @Query("SELECT * FROM shrimptask WHERE name LIKE :name AND execute_time >= :date ORDER BY execute_time asc, id")
    LiveData<List<ShrimpTask>> getFutureShrimpTaskNameSpecified(String name, LocalDate date);

    @Query("SELECT * FROM shrimptask WHERE execute_time = :date ORDER BY id")
    LiveData<List<ShrimpTask>> getShrimpTaskToday(LocalDate date);

    @Query("SELECT COUNT(id) FROM shrimptask WHERE name = :name")
    LiveData<Integer> getShrimpTasksNameMatch(String name);

    @Query("SELECT * FROM shrimptask WHERE `group` = :group")
    LiveData<List<ShrimpTask>> getShrimpTasksGroupMatch(String group);

    @Query("SELECT * FROM shrimptask WHERE parent_name = :template")
    LiveData<List<ShrimpTask>> getShrimpTasksTemplateMatch(String template);

    @Query("SELECT COUNT(id) FROM shrimptask")
    LiveData<Integer> getCountShrimpTask();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(ShrimpTask shrimpTasks);

    @Query("DELETE FROM shrimptask")
    public void deleteAll();

    @Query("DELETE FROM shrimptask WHERE id = :id")
    public void deleteItem(int id);

    @Query("SELECT * FROM shrimptask WHERE id == :id")
    public LiveData<ShrimpTask> getSelectShrimpTaskID(int id);

    @Update
    public void updateShrimpTask(ShrimpTask... shrimpTasks);
}
