package com.example.shrimpscheduler;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskTemplateDao {
    @Query("SELECT * FROM tasktemplate")
    LiveData<List<TaskTemplate>> getAllTaskTemplates();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TaskTemplate taskTemplate);

    @Query("DELETE FROM tasktemplate")
    void deleteAll();
}
