package com.example.shrimpscheduler.MainFragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shrimpscheduler.DataViewing.Data;
import com.example.shrimpscheduler.DataViewing.DataAdapter;
import com.example.shrimpscheduler.Group.Group;
import com.example.shrimpscheduler.Group.GroupViewModel;
import com.example.shrimpscheduler.MainActivity;
import com.example.shrimpscheduler.R;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTask;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskViewModel;
import com.example.shrimpscheduler.Template.TaskTemplate;
import com.example.shrimpscheduler.Template.TaskTemplateViewModel;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DataRecyclerFragment extends Fragment {
    private RecyclerView recyclerView;
    private DataAdapter adapter = new DataAdapter(new DataAdapter.DataDiff());

    private MainActivity mainActivity;

    private ShrimpTaskViewModel shrimpTaskViewModel;

    private ArrayList<String> distinctNames = new ArrayList<>();
    private ArrayList<String> distinctTemplates = new ArrayList<>();
    private ArrayList<String> distinctGroups = new ArrayList<>();

    private ArrayList<Data> namesDataList = new ArrayList<>();
    private ArrayList<Data> templatesDataList = new ArrayList<>();
    private ArrayList<Data> groupsDataList = new ArrayList<>();

    private HashMap<String, Data> nameDataHash = new HashMap<String, Data>();
    private HashMap<String, Data> templateDataHash = new HashMap<String, Data>();
    private HashMap<String, Data> groupDataHash = new HashMap<String, Data>();

    private String filterMode = "name";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_task_content_main, container, false);

        recyclerView = view.findViewById(R.id.data_task_content_main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        mainActivity = (MainActivity) getActivity();

        shrimpTaskViewModel = mainActivity.getShrimpTaskViewModel();

        shrimpTaskViewModel.getPastDistinctShrimpTaskNames().observe(this, pastDistinctNames -> {
            distinctNames.clear();
            nameDataHash.clear();

            for (String name : pastDistinctNames) {
                distinctNames.add(name);
                nameDataHash.put(name, new Data(name));
            }
        });

        shrimpTaskViewModel.getPastDistinctShrimpTaskTemplates().observe(this, pastDistinctTemplates -> {
            distinctTemplates.clear();
            templateDataHash.clear();

            for (String template : pastDistinctTemplates) {
                distinctTemplates.add(template);
                templateDataHash.put(template, new Data(template));
            }
        });

        shrimpTaskViewModel.getPastDistinctShrimpTaskGroups().observe(this, pastDistinctGroups -> {
            distinctGroups.clear();
            groupDataHash.clear();

            for (String group : pastDistinctGroups) {
                distinctGroups.add(group);
                groupDataHash.put(group, new Data(group));
            }
        });

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                setFilterMode("name");
            }
        }, 100);

        return view;
    }

    public void submitListToAdapter(ArrayList<Data> submitDataList) {
        adapter.submitList(submitDataList);
    }

    public DataAdapter getAdapter() {
        return adapter;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setFilterMode(String inputMode) {
        if (inputMode.equals("name") || inputMode.equals("template") || inputMode.equals("group")) {
            filterMode = inputMode;

            shrimpTaskViewModel.getPastShrimpTasks().observe(this, allTasks -> {
                if (filterMode.equals("name")) {
                    nameDataHash.forEach((k, v) -> {
                        v.setNotDisposedCount(0);
                        v.setDoneCount(0);
                        v.setNotDoneCount(0);
                    });
                } else if (filterMode.equals("template")) {
                    templateDataHash.forEach((k, v) -> {
                        v.setNotDisposedCount(0);
                        v.setDoneCount(0);
                        v.setNotDoneCount(0);
                    });
                } else if (filterMode.equals("group")) {
                    groupDataHash.forEach((k, v) -> {
                        v.setNotDisposedCount(0);
                        v.setDoneCount(0);
                        v.setNotDoneCount(0);
                    });
                }

                for (ShrimpTask task : allTasks) {
                    Data tempData;

                    if (filterMode.equals("name")) {
                        tempData = nameDataHash.get(task.getName());
                    } else if (filterMode.equals("template")) {
                        tempData = templateDataHash.get(task.getName());
                    } else if (filterMode.equals("group")) {
                        tempData = groupDataHash.get(task.getName());
                    } else {
                        break;
                    }

                    tempData.addShrimpTask(task);

                    if (task.isDisposed()) {
                        if (task.isDone()) {
                            tempData.addOneDone();
                        } else {
                            tempData.addOneNotDone();
                        }
                    } else {
                        tempData.addOneNotDisposed();
                    }
                }

                if (filterMode.equals("name")) {
                    namesDataList.clear();
                    nameDataHash.forEach((k, v) -> namesDataList.add(v));
                    adapter.submitList(namesDataList);
                } else if (filterMode.equals("template")) {
                    templatesDataList.clear();
                    templateDataHash.forEach((k, v) -> templatesDataList.add(v));
                    adapter.submitList(templatesDataList);
                } else if (filterMode.equals("group")) {
                    groupsDataList.clear();
                    groupDataHash.forEach((k, v) -> groupsDataList.add(v));
                    adapter.submitList(groupsDataList);
                }
            });
        } else {
            // Do nothing since the filter mode is invalid
        }
    }
}