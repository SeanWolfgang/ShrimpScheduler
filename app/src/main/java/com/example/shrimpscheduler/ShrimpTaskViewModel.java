package com.example.shrimpscheduler;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.util.List;

public class ShrimpTaskViewModel extends AndroidViewModel {
    private ShrimpTaskRepository shrimpRepository;
    private final LiveData<List<ShrimpTask>> allShrimpTasks;
    private final LiveData<List<ShrimpTask>> dateShrimpTasks;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ShrimpTaskViewModel (Application application) {
        super(application);
        shrimpRepository = new ShrimpTaskRepository(application);
        allShrimpTasks = shrimpRepository.getAllShrimpTasks();
        dateShrimpTasks = shrimpRepository.getShrimpTaskDate(LocalDate.now());
    }

    LiveData<List<ShrimpTask>> getAllShrimpTasks() { return allShrimpTasks; }

    LiveData<List<ShrimpTask>> getShrimpTaskDate(LocalDate date) { return dateShrimpTasks;}

    public void insert(ShrimpTask shrimpTask) { shrimpRepository.insert(shrimpTask); }

    public void deleteAll() {shrimpRepository.deleteAll();}

    public void updateShrimpTask(ShrimpTask shrimpTask) {shrimpRepository.updateShrimpTask(shrimpTask);}


}
