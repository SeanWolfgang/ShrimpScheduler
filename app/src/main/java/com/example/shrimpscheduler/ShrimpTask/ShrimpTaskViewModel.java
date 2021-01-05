package com.example.shrimpscheduler.ShrimpTask;

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

@RequiresApi(api = Build.VERSION_CODES.O)
public class ShrimpTaskViewModel extends AndroidViewModel {
    private ShrimpTaskRepository shrimpRepository;
    private final LiveData<List<ShrimpTask>> allShrimpTasks;
    private LiveData<PagedList<ShrimpTask>> allPagedShrimpTasks;
    private final LiveData<List<ShrimpTask>> todayShrimpTasks;
    private final LiveData<List<String>> distinctShrimpTaskNames;
    private final LiveData<List<LocalDate>> lastExecuteDate;
    private final LiveData<Integer> totalCount;
    private LiveData<List<ShrimpTask>> dateShrimpTasks;
    private LiveData<List<ShrimpTask>> dateRangeShrimpTasks;
    private LiveData<List<ShrimpTask>> futureShrimpTaskNameSpecified;
    private LiveData<List<ShrimpTask>> shrimpTasksGroupMatch;
    private LiveData<List<ShrimpTask>> shrimpTasksTemplateMatch;
    private LiveData<Integer> nameCountList;
    private LiveData<ShrimpTask> shrimpTaskSpecifiedID;

    private ShrimpTaskDao shrimpTaskDao;

    private LocalDate today = LocalDate.now();

    MutableLiveData<String> nameCountSearchName = new MutableLiveData<>();
    MutableLiveData<LocalDate> dateFilter = new MutableLiveData<>();
    MutableLiveData<String> nameFilter = new MutableLiveData<>();
    MutableLiveData<Integer> IDFilter = new MutableLiveData<>();
    MutableLiveData<String> groupFilter = new MutableLiveData<>();
    MutableLiveData<String> templateFilter = new MutableLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ShrimpTaskViewModel (Application application) {
        super(application);
        shrimpRepository = new ShrimpTaskRepository(application);
        allShrimpTasks = shrimpRepository.getAllShrimpTasks();
        todayShrimpTasks = shrimpRepository.getShrimpTaskToday(today);
        distinctShrimpTaskNames = shrimpRepository.getDistinctShrimpTaskNames();
        lastExecuteDate = shrimpRepository.getLastExecuteDate();
        dateShrimpTasks = shrimpRepository.getShrimpTaskDate(shrimpRepository.getQueryDate());
        dateRangeShrimpTasks = shrimpRepository.getShrimpTaskDateRange(shrimpRepository.getBeginDate(), shrimpRepository.getEndDate());
        totalCount = shrimpRepository.getCountShrimpTask();
        nameCountList = Transformations.switchMap(nameCountSearchName,
                name -> shrimpRepository.getShrimpTasksNameMatch(name));
        dateShrimpTasks = Transformations.switchMap(dateFilter,
                date -> shrimpRepository.getShrimpTaskDate(date));
        futureShrimpTaskNameSpecified = Transformations.switchMap(nameFilter,
                name -> shrimpRepository.getFutureShrimpTaskNameSpecified(name));
        shrimpTaskSpecifiedID = Transformations.switchMap(IDFilter,
                ID -> shrimpRepository.getSelectShrimpTaskID(ID));
        shrimpTasksGroupMatch = Transformations.switchMap(groupFilter,
                group -> shrimpRepository.getShrimpTasksGroupMatch(group));
        shrimpTasksTemplateMatch = Transformations.switchMap(templateFilter,
                template -> shrimpRepository.getShrimpTasksTemplateMatch(template));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LiveData<Integer> getShrimpTasksNameMatch() {
        return nameCountList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LiveData<List<ShrimpTask>> getShrimpTaskDate() {
        return dateShrimpTasks;
    }

    public void setFilter(String filter) { nameCountSearchName.setValue(filter); }

    public void setDate(LocalDate date) { dateFilter.setValue(date); }

    public void setNameFilter(String name) { nameFilter.setValue(name); }

    public void setGroupFilter(String group) { groupFilter.setValue(group); }

    public void setTemplateFilter(String template) { templateFilter.setValue(template); }

    public void setIDFilter(int ID) { IDFilter.setValue(ID); }

    public LiveData<List<ShrimpTask>> getAllShrimpTasks() { return allShrimpTasks; }

    public LiveData<List<ShrimpTask>> getShrimpTaskToday() { return todayShrimpTasks; }

    public LiveData<List<String>> getDistinctShrimpTaskNames() { return distinctShrimpTaskNames; }

    public LiveData<List<LocalDate>> getLastExecuteDate() { return lastExecuteDate; }

    public LiveData<List<ShrimpTask>> getShrimpTaskDate(LocalDate date) { return dateShrimpTasks;}

    public LiveData<List<ShrimpTask>> getShrimpTaskDateRange() { return dateRangeShrimpTasks;}

    public LiveData<List<ShrimpTask>> getFutureShrimpTaskNameSpecified() { return futureShrimpTaskNameSpecified;}

    public LiveData<List<ShrimpTask>> getShrimpTasksGroupMatch() { return shrimpTasksGroupMatch;}

    public LiveData<List<ShrimpTask>> getShrimpTasksTemplateMatch() { return shrimpTasksTemplateMatch;}

    public LiveData<ShrimpTask> getSelectShrimpTaskID() { return shrimpTaskSpecifiedID;}

    public LiveData<Integer> getCountShrimpTask() { return totalCount;}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insert(ShrimpTask shrimpTask) { shrimpRepository.insert(shrimpTask); }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteAll() {shrimpRepository.deleteAll();}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteItem(int id) {shrimpRepository.deleteItem(id); }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateShrimpTask(ShrimpTask shrimpTask) {shrimpRepository.updateShrimpTask(shrimpTask);}

    public void initAllTasks() {

        shrimpTaskDao = shrimpRepository.getShrimpTaskDao();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setPageSize(10)
                .build();

        allPagedShrimpTasks = new LivePagedListBuilder<>(shrimpTaskDao.getPagedAllShrimpTasks(), config)
                        .build();

/*
        this.shrimpTaskDao = shrimpTaskDao;

        allPagedShrimpTasks = Transformations.switchMap(filterTaskNameSearch, input -> {
            if (input == null || input.equals("") || input.equals("%%")) {
                return new LivePagedListBuilder<>(shrimpTaskDao.getPagedAllShrimpTasks(), config)
                        .build();
            } else {
                return new LivePagedListBuilder<>(shrimpTaskDao.getNameShrimpTasks(input), config)
                        .build();
            }
        });

 */

    }

    public LiveData<PagedList<ShrimpTask>> getAllPagedShrimpTasks() {
        return allPagedShrimpTasks;
    }
}
