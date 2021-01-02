package com.example.shrimpscheduler.Template;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskTemplateDao {
    @Query("SELECT * FROM tasktemplate")
    LiveData<List<TaskTemplate>> getAllTaskTemplates();

    @Query("SELECT name FROM tasktemplate WHERE name = :name")
    String pullName(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TaskTemplate taskTemplate);

    @Query("DELETE FROM tasktemplate")
    void deleteAll();

    @Update
    public void updateTaskTemplate(TaskTemplate... taskTemplates);
}
