package com.example.shrimpscheduler;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ShrimpTaskViewModel extends AndroidViewModel {
    private ShrimpTaskRepository shrimpRepository;
    private final LiveData<List<ShrimpTask>> allShrimpTasks;

    public ShrimpTaskViewModel (Application application) {
        super(application);
        shrimpRepository = new ShrimpTaskRepository(application);
        allShrimpTasks = shrimpRepository.getAllShrimpTasks();
    }

    LiveData<List<ShrimpTask>> getAllShrimpTasks() { return allShrimpTasks; }

    public void insert(ShrimpTask shrimpTask) { shrimpRepository.insert(shrimpTask); }

    public void deleteAll() {shrimpRepository.deleteAll();}
}
