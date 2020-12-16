package com.example.shrimpscheduler;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskTemplateViewModel  extends AndroidViewModel {
    private TaskTemplateRepository taskTemplateRepository;
    private final LiveData<List<TaskTemplate>> allTaskTemplates;

    public TaskTemplateViewModel (Application application) {
        super(application);
        taskTemplateRepository = new TaskTemplateRepository(application);
        allTaskTemplates = taskTemplateRepository.getAllTaskTemplates();
    }

    LiveData<List<TaskTemplate>> getAllTaskTemplates() { return allTaskTemplates; }

    public void insert(TaskTemplate taskTemplate) { taskTemplateRepository.insert(taskTemplate); }

    public void deleteAll() {taskTemplateRepository.deleteAll();}
}