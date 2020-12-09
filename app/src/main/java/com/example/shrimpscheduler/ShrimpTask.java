package com.example.shrimpscheduler;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;

@Entity
public class ShrimpTask {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name= "name")
    private String name;

    @ColumnInfo(name= "parent_name")
    private String parentName;

    @ColumnInfo(name= "execute_time")
    private OffsetDateTime executeTime;

    @ColumnInfo(name= "description")
    private String description;

    @ColumnInfo(name= "done")
    private boolean done = false;

    public ShrimpTask(String name, String parentName, OffsetDateTime executeTime, String description) {
        this.name = name;
        this.parentName = parentName;
        this.executeTime = executeTime;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(OffsetDateTime executeTime) {
        this.executeTime = executeTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public void setId(int id) {
        this.id = id;
    }
}
