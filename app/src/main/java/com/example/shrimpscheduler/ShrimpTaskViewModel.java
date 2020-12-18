package com.example.shrimpscheduler;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.time.LocalDate;
import java.util.List;

public class ShrimpTaskViewModel extends AndroidViewModel {
    private ShrimpTaskRepository shrimpRepository;
    private final LiveData<List<ShrimpTask>> allShrimpTasks;
    private final LiveData<List<ShrimpTask>> dateShrimpTasks;
    private LiveData<Integer> nameCountList;
    private final LiveData<Integer> totalCount;

    MutableLiveData<String> nameCountSearchName = new MutableLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ShrimpTaskViewModel (Application application) {
        super(application);
        shrimpRepository = new ShrimpTaskRepository(application);
        allShrimpTasks = shrimpRepository.getAllShrimpTasks();
        dateShrimpTasks = shrimpRepository.getShrimpTaskDate(shrimpRepository.getQueryDate());
        totalCount = shrimpRepository.getCountShrimpTask();
        nameCountList = Transformations.switchMap(nameCountSearchName,
                name -> shrimpRepository.getShrimpTasksNameMatch(name));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    LiveData<Integer> getShrimpTasksNameMatch() {
        return nameCountList;
    }
    void setFilter(String filter) { nameCountSearchName.setValue(filter); }

    LiveData<List<ShrimpTask>> getAllShrimpTasks() { return allShrimpTasks; }

    LiveData<List<ShrimpTask>> getShrimpTaskDate(LocalDate date) { return dateShrimpTasks;}

    LiveData<Integer> getCountShrimpTask() { return totalCount;}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insert(ShrimpTask shrimpTask) { shrimpRepository.insert(shrimpTask); }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteAll() {shrimpRepository.deleteAll();}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateShrimpTask(ShrimpTask shrimpTask) {shrimpRepository.updateShrimpTask(shrimpTask);}

}
