package com.example.shrimpscheduler.Group;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GroupDao {
    @Query("SELECT * FROM `group` ORDER BY id")
    LiveData<List<Group>> getAllGroups();

    @Query("SELECT COUNT(id) FROM `group` WHERE name = :name")
    LiveData<Integer> getGroupNameMatch(String name);

    @Query("SELECT * FROM `group` WHERE id = :id")
    public Group getGroup(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Group group);

    @Query("DELETE FROM `group`")
    public void deleteAll();

    @Query("DELETE FROM `group` WHERE id = :id")
    public void deleteItem(int id);

    @Update
    public void updateGroup(Group... groups);
}