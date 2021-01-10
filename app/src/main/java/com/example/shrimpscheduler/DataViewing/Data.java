package com.example.shrimpscheduler.DataViewing;

import com.example.shrimpscheduler.ShrimpTask.ShrimpTask;

import java.util.ArrayList;

public class Data {
    private ArrayList<ShrimpTask> taskList = new ArrayList<>();
    private String title;
    private int notDisposedCount;
    private int doneCount;
    private int notDoneCount;

    public Data(String inputTitle) {
        taskList = new ArrayList<>();
        title = inputTitle;
        notDisposedCount = 0;
        doneCount = 0;
        notDoneCount = 0;
    }


    public ArrayList<ShrimpTask> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<ShrimpTask> taskList) {
        this.taskList = taskList;
    }

    public int getNotDisposedCount() {
        return notDisposedCount;
    }

    public void setNotDisposedCount(int notDisposedCount) {
        this.notDisposedCount = notDisposedCount;
    }

    public int getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(int doneCount) {
        this.doneCount = doneCount;
    }

    public int getNotDoneCount() {
        return notDoneCount;
    }

    public void setNotDoneCount(int notDoneCount) {
        this.notDoneCount = notDoneCount;
    }

    public void addOneNotDisposed() {
        notDisposedCount++;
    }

    public void addOneDone() {
        doneCount++;
    }

    public void addOneNotDone() {
        notDoneCount++;
    }

    public void addShrimpTask(ShrimpTask addTask) {
        taskList.add(addTask);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}