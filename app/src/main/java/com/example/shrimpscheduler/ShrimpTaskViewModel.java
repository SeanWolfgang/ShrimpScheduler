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

@RequiresApi(api = Build.VERSION_CODES.O)
public class ShrimpTaskViewModel extends AndroidViewModel {
    private ShrimpTaskRepository shrimpRepository;
    private final LiveData<List<ShrimpTask>> allShrimpTasks;
    private final LiveData<List<ShrimpTask>> todayShrimpTasks;
    private final LiveData<Integer> totalCount;
    private LiveData<List<ShrimpTask>> dateShrimpTasks;
    private LiveData<Integer> nameCountList;

    private LocalDate today = LocalDate.now();

    MutableLiveData<String> nameCountSearchName = new MutableLiveData<>();
    MutableLiveData<LocalDate> dateFilter = new MutableLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ShrimpTaskViewModel (Application application) {
        super(application);
        shrimpRepository = new ShrimpTaskRepository(application);
        allShrimpTasks = shrimpRepository.getAllShrimpTasks();
        todayShrimpTasks = shrimpRepository.getShrimpTaskToday(today);
        dateShrimpTasks = shrimpRepository.getShrimpTaskDate(shrimpRepository.getQueryDate());
        totalCount = shrimpRepository.getCountShrimpTask();
        nameCountList = Transformations.switchMap(nameCountSearchName,
                name -> shrimpRepository.getShrimpTasksNameMatch(name));
        dateShrimpTasks = Transformations.switchMap(dateFilter,
                date -> shrimpRepository.getShrimpTaskDate(date));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    LiveData<Integer> getShrimpTasksNameMatch() {
        return nameCountList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    LiveData<List<ShrimpTask>> getShrimpTaskDate() {
        return dateShrimpTasks;
    }

    void setFilter(String filter) { nameCountSearchName.setValue(filter); }

    void setDate(LocalDate date) { dateFilter.setValue(date); }

    LiveData<List<ShrimpTask>> getAllShrimpTasks() { return allShrimpTasks; }

    LiveData<List<ShrimpTask>> getShrimpTaskToday() { return todayShrimpTasks; }

    LiveData<List<ShrimpTask>> getShrimpTaskDate(LocalDate date) { return dateShrimpTasks;}

    LiveData<Integer> getCountShrimpTask() { return totalCount;}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insert(ShrimpTask shrimpTask) { shrimpRepository.insert(shrimpTask); }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteAll() {shrimpRepository.deleteAll();}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateShrimpTask(ShrimpTask shrimpTask) {shrimpRepository.updateShrimpTask(shrimpTask);}

}
