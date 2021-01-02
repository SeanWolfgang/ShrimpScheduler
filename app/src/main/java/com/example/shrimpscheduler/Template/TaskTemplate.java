package com.example.shrimpscheduler.Template;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskTemplate {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name= "name")
    private String name;

    @ColumnInfo(name= "default_name")
    private String defaultName;

    @ColumnInfo(name= "default_description")
    private String defaultDescription;

    @ColumnInfo(name= "days_between_repeat")
    private int daysBetweenRepeat;

    @ColumnInfo(name= "repeat")
    private boolean repeat;

    public TaskTemplate(String name, String defaultName, String defaultDescription, int daysBetweenRepeat, boolean repeat) {
        this.name = name;
        this.defaultName = defaultName;
        this.defaultDescription = defaultDescription;
        this.daysBetweenRepeat = daysBetweenRepeat;
        this.repeat = repeat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public String getDefaultDescription() {
        return defaultDescription;
    }

    public void setDefaultDescription(String defaultDescription) {
        this.defaultDescription = defaultDescription;
    }

    public int getDaysBetweenRepeat() {
        return daysBetweenRepeat;
    }

    public void setDaysBetweenRepeat(int daysBetweenRepeat) {
        this.daysBetweenRepeat = daysBetweenRepeat;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }
}