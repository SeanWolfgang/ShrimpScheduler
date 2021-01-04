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

    @Query("DELETE FROM tasktemplate WHERE id = :id")
    public void deleteItem(int id);

    @Query("SELECT * FROM tasktemplate WHERE id == :id")
    public LiveData<TaskTemplate> getSelectTaskTemplateID(int id);

    @Update
    public void updateTaskTemplate(TaskTemplate... taskTemplates);
}
