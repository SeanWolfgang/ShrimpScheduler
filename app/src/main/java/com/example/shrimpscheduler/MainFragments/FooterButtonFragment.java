package com.example.shrimpscheduler.MainFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shrimpscheduler.R;

public class FooterButtonFragment extends Fragment {
    private FooterButtonFragment.FooterButtonFragmentListener listener;
    private ImageButton taskButton;
    private ImageButton templateButton;
    private ImageButton settingsButton;
    private ImageButton groupButton;
    private ImageButton dataButton;

    public interface FooterButtonFragmentListener{
        void onTaskButtonClicked();
        void onTemplateButtonClicked();
        void onSettingsButtonClicked();
        void onGroupButtonClicked();
        void onDataButtonClicked();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_screen_footer_buttons_fragment, container, false);

        taskButton = (ImageButton) view.findViewById(R.id.footer_buttons_task);
        templateButton = (ImageButton) view.findViewById(R.id.footer_buttons_template);
        settingsButton = (ImageButton) view.findViewById(R.id.footer_buttons_settings);
        groupButton = (ImageButton) view.findViewById(R.id.footer_buttons_group);
        dataButton = (ImageButton) view.findViewById(R.id.footer_buttons_data);

        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTaskButtonClicked();
            }
        });

        templateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTemplateButtonClicked();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSettingsButtonClicked();
            }
        });

        groupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onGroupButtonClicked();
            }
        });

        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDataButtonClicked();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FooterButtonFragment.FooterButtonFragmentListener) {
            listener = (FooterButtonFragment.FooterButtonFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FooterButtonFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}