package com.example.shrimpscheduler.Group;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class GroupRepository {

    private GroupDao groupDao;
    private LiveData<List<Group>> allGroups;

    // Note that in order to unit test the ShrimpTaskRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    GroupRepository(Application application) {
        GroupDatabase db = GroupDatabase.getDatabase(application);
        groupDao = db.groupDao();
        allGroups = groupDao.getAllGroups();
    }


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Group>> getAllGroups() { return allGroups; }

    LiveData<Integer> getGroupNameMatch(String name) { return groupDao.getGroupNameMatch(name); }

    public LiveData<Group> getSelectGroupID(int ID) { return groupDao.getSelectGroupID(ID); }

    void insert(Group group) {
        GroupDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.insert(group);
        });
    }

    void deleteAll() {
        GroupDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.deleteAll();
        });
    }

    void deleteItem(int id) {
        GroupDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.deleteItem(id);
        });
    }

    void updateGroup(Group group) {
        GroupDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.updateGroup(group);
        });
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }
}
