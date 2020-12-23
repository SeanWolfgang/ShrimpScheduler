package com.example.shrimpscheduler;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.time.LocalDate;
import java.util.List;

public class GroupViewModel extends AndroidViewModel {
    private GroupRepository groupRepository;
    private final LiveData<List<Group>> allGroups;
    //private final LiveData<Integer> totalCount;
    private LiveData<Integer> matchGroups;

    MutableLiveData<String> groupNameFilter = new MutableLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GroupViewModel (Application application) {
        super(application);
        groupRepository = new GroupRepository(application);
        allGroups = groupRepository.getAllGroups();
        matchGroups = Transformations.switchMap(groupNameFilter,
                name -> groupRepository.getGroupNameMatch(name));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    LiveData<Integer> getGroupNameMatch() {
        return matchGroups;
    }

    LiveData<List<Group>> getAllGroups() { return allGroups; }

    void setGroupNameFilter(String filter) { groupNameFilter.setValue(filter); }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insert(Group group) { groupRepository.insert(group); }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteAll() {groupRepository.deleteAll();}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteItem(int id) {groupRepository.deleteItem(id);}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateGroup(Group group) {groupRepository.updateGroup(group);}
}
