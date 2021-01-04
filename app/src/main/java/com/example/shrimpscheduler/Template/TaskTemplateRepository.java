package com.example.shrimpscheduler.Template;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskTemplateRepository {

    private TaskTemplateDao taskTemplateDao;
    private LiveData<List<TaskTemplate>> allTaskTemplates;

    // Note that in order to unit test the TaskTemplateRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    TaskTemplateRepository(Application application) {
        TaskTemplateDatabase db = TaskTemplateDatabase.getDatabase(application);
        taskTemplateDao = db.taskTemplateDao();
        allTaskTemplates = taskTemplateDao.getAllTaskTemplates();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<TaskTemplate>> getAllTaskTemplates() {
        return allTaskTemplates;
    }

    public LiveData<TaskTemplate> getSelectTaskTemplateID(int ID) { return taskTemplateDao.getSelectTaskTemplateID(ID); }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(TaskTemplate taskTemplate) {
        TaskTemplateDatabase.databaseWriteExecutor.execute(() -> {
            taskTemplateDao.insert(taskTemplate);
        });
    }

    void deleteAll() {
        TaskTemplateDatabase.databaseWriteExecutor.execute(() -> {
            taskTemplateDao.deleteAll();
        });
    }

    void deleteItem(int id) {
        TaskTemplateDatabase.databaseWriteExecutor.execute(() -> {
            taskTemplateDao.deleteItem(id);
        });
    }

    public void updateTemplate(TaskTemplate taskTemplate) {
        TaskTemplateDatabase.databaseWriteExecutor.execute(() -> {
            taskTemplateDao.updateTaskTemplate(taskTemplate);
        });
    }

}
