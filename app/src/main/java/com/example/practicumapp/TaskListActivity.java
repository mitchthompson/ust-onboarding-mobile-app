package com.example.practicumapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.example.practicumapp.adapters.TaskListAdapter;
import com.example.practicumapp.models.Tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private Context context;
    private String[] testData;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private TaskListAdapter taskViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ArrayList<String> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        context = getApplicationContext();
        testData = getResources().getStringArray(R.array.task_list);
        taskList = new ArrayList<>(Arrays.asList(testData));

        relativeLayout = (RelativeLayout) findViewById(R.id.activity_task_list);
        recyclerView = (RecyclerView) findViewById(R.id.task_list_recycler);
        recyclerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        taskViewAdapter = new TaskListAdapter(context, taskList);
        recyclerView.setAdapter(taskViewAdapter);
    }
}
