package com.example.practicumapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class DebugActivity extends MainActivity {

    private Button taskListButton, newHireButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        myToolbar.setTitle("Debug Menu");
        setSupportActionBar(myToolbar);

        taskListButton = findViewById(R.id.task_list_button);
        newHireButton = findViewById(R.id.new_hire_button);
        taskListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DebugActivity.this, TaskListActivity.class));
            }
        });
        newHireButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DebugActivity.this, NewHireListActivity.class));
            }
        });
    }
}
