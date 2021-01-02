package com.example.shrimpscheduler.MainFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shrimpscheduler.R;

public class OkCancelButtonFooterFragment extends Fragment {
    private OkCancelButtonFooterFragment.OkCancelButtonFooterFragmentListener listener;

    private Button confirmButton;
    private Button cancelButton;

    public interface OkCancelButtonFooterFragmentListener{
        void confirmButtonListener();
        void cancelButtonListener();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ok_cancel_button_footer_fragment, container, false);

        confirmButton = v.findViewById(R.id.footer_confirm_button);
        cancelButton = v.findViewById(R.id.footer_cancel_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.confirmButtonListener();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cancelButtonListener();
            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OkCancelButtonFooterFragment.OkCancelButtonFooterFragmentListener) {
            listener = (OkCancelButtonFooterFragment.OkCancelButtonFooterFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OkCancelButtonFooterFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}