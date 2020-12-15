package com.example.shrimpscheduler;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ShrimpTaskRepository {

    private ShrimpTaskDao shrimpTaskDao;
    private LiveData<List<ShrimpTask>> allShrimpTasks;

    // Note that in order to unit test the ShrimpTaskRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    ShrimpTaskRepository(Application application) {
        ShrimpTaskDatabase db = ShrimpTaskDatabase.getDatabase(application);
        shrimpTaskDao = db.shrimpTaskDao();
        allShrimpTasks = shrimpTaskDao.getAllShrimpTasks();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<ShrimpTask>> getAllShrimpTasks() {
        return allShrimpTasks;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(ShrimpTask shrimpTask) {
        ShrimpTaskDatabase.databaseWriteExecutor.execute(() -> {
            shrimpTaskDao.insert(shrimpTask);
        });
    }

    void deleteAll() {
        ShrimpTaskDatabase.databaseWriteExecutor.execute(() -> {
            shrimpTaskDao.deleteAll();
        });
    }
}
