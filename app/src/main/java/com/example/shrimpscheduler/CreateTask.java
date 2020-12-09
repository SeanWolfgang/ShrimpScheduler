package com.example.shrimpscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateTask extends AppCompatActivity {
    public static final String EXTRA_REPLY1 = "REPLY1";
    public static final String EXTRA_REPLY2 = "REPLY2";

    private EditText nameEditText;
    private EditText descriptionEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        nameEditText = findViewById(R.id.task_name);
        descriptionEditText = findViewById(R.id.description);

        final Button saveButton = findViewById(R.id.save_user_button);
        saveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(nameEditText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else if (TextUtils.isEmpty(descriptionEditText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY1, name);
                replyIntent.putExtra(EXTRA_REPLY2, description);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}
