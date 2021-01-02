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

public class TemplateHeaderFragment extends Fragment {
    private TemplateHeaderFragment.TemplateHeaderFragmentListener listener;

    private ImageButton newImageButton;

    public interface TemplateHeaderFragmentListener{
        void newTemplateListener();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.task_template_header_fragment, container, false);

        newImageButton = v.findViewById(R.id.template_header_imagebutton);

        newImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.newTemplateListener();
            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TemplateHeaderFragment.TemplateHeaderFragmentListener) {
            listener = (TemplateHeaderFragment.TemplateHeaderFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement TemplateHeaderFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}