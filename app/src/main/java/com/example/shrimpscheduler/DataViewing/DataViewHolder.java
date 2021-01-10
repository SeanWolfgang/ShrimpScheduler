package com.example.shrimpscheduler.DataViewing;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shrimpscheduler.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataViewHolder extends RecyclerView.ViewHolder {
    private final PieChart recyclerChart;
    private final TextView recyclerTitle;
    private final TextView recyclerDescriptions;

    private DataViewHolder(View itemView, DataAdapter.OnDataClickTaskListener listener) {
        super(itemView);
        recyclerChart = itemView.findViewById(R.id.data_recycler_data);
        recyclerTitle = itemView.findViewById(R.id.data_recycler_title);
        recyclerDescriptions = itemView.findViewById(R.id.data_recycler_results);

        recyclerTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onTaskClick(position);
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bind(String title, int notDisposed, int done, int notDone) {
        String resultsString = "Done: " + done + " Not Done: " + notDone + " Not Specified: " + notDisposed;

        recyclerTitle.setText(title);
        recyclerDescriptions.setText(resultsString);

        showPieChart(notDisposed, done, notDone);

    }

    public static DataViewHolder create(ViewGroup parent, DataAdapter.OnDataClickTaskListener listener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_task_recycler, parent, false);
        return new DataViewHolder(view, listener);
    }

    private void showPieChart(int notDisposed, int done, int notDone){

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        typeAmountMap.put("Not Disposed",notDisposed);
        typeAmountMap.put("Done",done);
        typeAmountMap.put("Not Done",notDone);

        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();


        if (done > 0) {
            colors.add(Color.GREEN);
        }
        if (notDone > 0) {
            colors.add(Color.RED);
        }
        if (notDisposed > 0) {
            colors.add(Color.GRAY);
        }

        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            float entry = typeAmountMap.get(type).floatValue();
            if (entry != 0) {
                pieEntries.add(new PieEntry(entry, type));
            }

        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(false);
        pieData.setValueFormatter(new MyValueFormatter());

        recyclerChart.setDrawHoleEnabled(false);
        recyclerChart.setDrawEntryLabels(false);
        recyclerChart.getDescription().setEnabled(false);
        recyclerChart.getLegend().setEnabled(false);
        recyclerChart.animateY(500, Easing.EaseInOutQuad);

        recyclerChart.setData(pieData);
        recyclerChart.invalidate();
    }

    private class MyValueFormatter extends ValueFormatter {
        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("#");
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value);
        }
    }
}
