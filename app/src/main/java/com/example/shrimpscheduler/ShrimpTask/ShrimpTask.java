package com.example.shrimpscheduler.ShrimpTask;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class ShrimpTask {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name= "name")
    private String name;

    @ColumnInfo(name= "parent_name")
    private String parentName;

    @ColumnInfo(name= "execute_time")
    private LocalDate executeTime;

    @ColumnInfo(name= "description")
    private String description;

    @ColumnInfo(name= "done")
    private boolean done = false;

    @ColumnInfo(name= "disposed")
    private boolean disposed = false;

    public ShrimpTask(String name, String parentName, LocalDate executeTime, String description) {
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

    public LocalDate getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(LocalDate executeTime) {
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

    public boolean isDisposed() {
        return disposed;
    }

    public void setDisposed(boolean disposed) {
        this.disposed = disposed;
    }
}
