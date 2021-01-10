package com.example.shrimpscheduler.MainFragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.shrimpscheduler.MainActivity;
import com.example.shrimpscheduler.R;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTask;
import com.example.shrimpscheduler.ShrimpTask.ShrimpTaskViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class DataPreviewFragment extends Fragment {
    private BarChart chart;
    ArrayList<ShrimpTask> dateRangeTasks = new ArrayList<>();
    ArrayList<float[]> dayTaskNumbersList = new ArrayList<>();
    ShrimpTaskViewModel shrimpTaskViewModel;
    MainActivity mainActivity;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_preview, container, false);

        mainActivity = (MainActivity) getActivity();

        shrimpTaskViewModel = mainActivity.getShrimpTaskViewModel();

        chart = (BarChart) view.findViewById(R.id.chart);

        updateCharts();

        chart.animateY(500);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setEnabled(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        //l.setFormSize(8f);
        //l.setFormToTextSpace(4f);
        //l.setXEntrySpace(6f);

        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.invalidate();

        return view;

    }

    private void setData(ArrayList<float[]> taskStatuses) {

        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        int i = (int) start;

        for (float[] taskStatus : taskStatuses) {
            float totalTask = taskStatus[0] + taskStatus[1] + taskStatus[2];

            float notDisposedPercentage;
            float donePercentage;
            float notDonePercentage;

            if (totalTask > 0) {
                notDisposedPercentage = (taskStatus[0] / totalTask) * 100;
                donePercentage = (taskStatus[1] / totalTask) * 100;
                notDonePercentage = (taskStatus[2] / totalTask) * 100;
            } else {
                notDisposedPercentage = 0;
                donePercentage = 0;
                notDonePercentage = 0;
            }

            values.add(new BarEntry(i, new float[]{donePercentage, notDonePercentage, notDisposedPercentage}));

            i++;
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "");

            set1.setDrawIcons(false);
            set1.setColors(getColors());
            set1.setStackLabels(new String[]{"Done", "Not Done", "Not Disposed"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextColor(Color.WHITE);

            data.setDrawValues(false);

            chart.setData(data);
        }
    }

    private ArrayList getXAxisValues() {
        ArrayList xAxis = new ArrayList();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }

    private int[] getColors() {

        // have as many colors as stack-values per entry
        int[] colors = new int[3];

        colors[0] = Color.GREEN;
        colors[1] = Color.RED;
        colors[2] = Color.GRAY;

        return colors;
    }

    private void displayTextScreen(String inputText) {
        Toast.makeText(
                getContext(),
                inputText,
                Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateCharts() {
        shrimpTaskViewModel.getShrimpTaskDateRange().observe(this, shrimpTaskDateRange -> {
            LocalDate startDay = LocalDate.now().minusDays(4);
            LocalDate labelDay = LocalDate.now().minusDays(4);
            LocalDate endDay = LocalDate.now().plusDays(2);

            dayTaskNumbersList.clear();

            String[] labels = new String[8];
            labels[0] = "--";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E");

            for (int i = 0; i <= DAYS.between(startDay, endDay); i++ ) {
                dayTaskNumbersList.add(i, new float[]{0,0,0});
                labels[i+1] = labelDay.format(formatter);
                labelDay = labelDay.plusDays(1);
            }
            labels[5] = "today";
            chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

            for (ShrimpTask eachTask : shrimpTaskDateRange) {
                int dayDifference = (int) DAYS.between(startDay, eachTask.getExecuteTime());

                if (eachTask.isDisposed()) {
                    if (eachTask.isDone()) {
                        dayTaskNumbersList.get(dayDifference)[1] = dayTaskNumbersList.get(dayDifference)[1] + 1;
                    } else {
                        dayTaskNumbersList.get(dayDifference)[2] = dayTaskNumbersList.get(dayDifference)[2] + 1;
                    }
                } else {
                    dayTaskNumbersList.get(dayDifference)[0] = dayTaskNumbersList.get(dayDifference)[0] + 1;
                }
            }
            setData(dayTaskNumbersList);

            chart.invalidate();
        });
    }
}
