package com.example.shrimpscheduler;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.time.LocalDate;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ShrimpTaskRepository {

    private ShrimpTaskDao shrimpTaskDao;
    private LiveData<List<ShrimpTask>> allShrimpTasks;
    //private LiveData<PagedList<ShrimpTask>> allPagedShrimpTasks;
    private LiveData<List<ShrimpTask>> todayShrimpTasks;
    private LiveData<Integer> totalCount;

    private LocalDate today = LocalDate.now();
    private LocalDate queryDate = LocalDate.now();

    // Note that in order to unit test the ShrimpTaskRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    @RequiresApi(api = Build.VERSION_CODES.O)
    ShrimpTaskRepository(Application application) {
        ShrimpTaskDatabase db = ShrimpTaskDatabase.getDatabase(application);
        shrimpTaskDao = db.shrimpTaskDao();
        allShrimpTasks = shrimpTaskDao.getAllShrimpTasks();
        todayShrimpTasks = shrimpTaskDao.getShrimpTaskToday(today);
        totalCount = shrimpTaskDao.getCountShrimpTask();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<ShrimpTask>> getAllShrimpTasks() { return allShrimpTasks; }

    LiveData<List<ShrimpTask>> getShrimpTaskDate(LocalDate date) { return shrimpTaskDao.getShrimpTaskDate(date); }

    LiveData<List<ShrimpTask>> getShrimpTaskToday(LocalDate date) { return shrimpTaskDao.getShrimpTaskToday(date); }

    LiveData<Integer> getShrimpTasksNameMatch(String name) { return shrimpTaskDao.getShrimpTasksNameMatch(name); }

    LiveData<Integer> getCountShrimpTask() { return totalCount; }

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

    void updateShrimpTask(ShrimpTask shrimpTask) {
        ShrimpTaskDatabase.databaseWriteExecutor.execute(() -> {
            shrimpTaskDao.updateShrimpTask(shrimpTask);
        });
    }

    public void setQueryDate(LocalDate queryDate) {
        this.queryDate = queryDate;
    }

    public LocalDate getQueryDate() {
        return queryDate;
    }

    public ShrimpTaskDao getShrimpTaskDao() {
        return shrimpTaskDao;
    }
}
