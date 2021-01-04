package com.example.shrimpscheduler.Template;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

public class TaskTemplateViewModel  extends AndroidViewModel {
    private TaskTemplateRepository taskTemplateRepository;
    private final LiveData<List<TaskTemplate>> allTaskTemplates;
    private LiveData<TaskTemplate> taskTemplateSpecifiedID;

    MutableLiveData<Integer> IDFilter = new MutableLiveData<>();

    public TaskTemplateViewModel (Application application) {
        super(application);
        taskTemplateRepository = new TaskTemplateRepository(application);
        allTaskTemplates = taskTemplateRepository.getAllTaskTemplates();
        taskTemplateSpecifiedID = Transformations.switchMap(IDFilter,
                ID -> taskTemplateRepository.getSelectTaskTemplateID(ID));
    }

    public LiveData<List<TaskTemplate>> getAllTaskTemplates() { return allTaskTemplates; }

    public void insert(TaskTemplate taskTemplate) { taskTemplateRepository.insert(taskTemplate); }

    public void deleteAll() {taskTemplateRepository.deleteAll();}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteItem(int id) {taskTemplateRepository.deleteItem(id); }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateTemplate(TaskTemplate taskTemplate) {taskTemplateRepository.updateTemplate(taskTemplate);}

    public LiveData<TaskTemplate> getTaskTemplateSpecifiedID() { return taskTemplateSpecifiedID;}

    public void setIDFilter(int ID) { IDFilter.setValue(ID); }
}