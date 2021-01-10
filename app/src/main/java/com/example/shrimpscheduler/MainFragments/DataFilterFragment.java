package com.example.shrimpscheduler.MainFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shrimpscheduler.R;

import java.util.ArrayList;

public class DataFilterFragment extends Fragment {
    private DataFilterFragment.DataFilterFragmentListener listener;

    private TextView dataFilterLabel;
    private Spinner dataFilterSpinner;

    private ArrayList<String> spinnerStrings = new ArrayList<>();

    public interface DataFilterFragmentListener{
        void dataFilterSpinnerChanged(String selectedString);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.data_filter_picker, container, false);

        dataFilterLabel = v.findViewById(R.id.data_filter_label);
        dataFilterSpinner = v.findViewById(R.id.data_filter_spinner);

        spinnerStrings = new ArrayList<>();
        spinnerStrings.add(getResources().getString(R.string.data_filter_label_name));
        spinnerStrings.add(getResources().getString(R.string.data_filter_label_template));
        spinnerStrings.add(getResources().getString(R.string.data_filter_label_group));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerStrings);
        spinnerAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        dataFilterSpinner.setAdapter(spinnerAdapter);

        dataFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listener.dataFilterSpinnerChanged(spinnerStrings.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

   @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DataFilterFragment.DataFilterFragmentListener) {
            listener = (DataFilterFragment.DataFilterFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DataFilterFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}