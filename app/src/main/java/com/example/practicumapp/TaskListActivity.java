package com.example.practicumapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.example.practicumapp.adapters.TaskListAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * JS - main task activity; this will show all of the tasks that an employee needs to complete
 * dummy data is in an arrayList form - this can be changed depending on how the API team sends over
 * info to us (probably as json?). we can convert this to an arraylist (keeping the rest of the
 * code the same) or we can find some other data structure to use
 *
 * onclick listeners will need to be created to support interacting with the list of data (checking
 * the checkboxes, etc)
 *
 * the current (1/14/18) wireframe details line item descriptions that drop down upon tapping
 * an item. this will probably involve creating a more robust adapter for the recyclerview that
 * displays its contents upon tapping
 *
 * TODO: add check boxes next to each item
 * TODO: add description under each item
 * TODO: create onclick listeners for checking the checkboxes and expanding each item's description
 */

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

        /*Enables tool bar & sets title displayed
        Can customize menu items in res/menu/main_menu.xml
        Can customize toolbar in res/layout/toolbar_layout.xml*/

        // contains dummy data from arrays.xml
        testData = getResources().getStringArray(R.array.task_list);
        // converts array into a list for use with the adapter
        taskList = new ArrayList<>(Arrays.asList(testData));

        relativeLayout = (RelativeLayout) findViewById(R.id.activity_task_list);
        recyclerView = (RecyclerView) findViewById(R.id.task_list_recycler);
        recyclerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        taskViewAdapter = new TaskListAdapter(context, taskList);
        recyclerView.setAdapter(taskViewAdapter);
    }
}
