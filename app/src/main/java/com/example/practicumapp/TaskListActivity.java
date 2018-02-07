package com.example.practicumapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.practicumapp.adapters.TaskListAdapter;
import com.example.practicumapp.models.Task;
import com.example.practicumapp.models.TaskDescription;

import java.util.ArrayList;

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
 *
 * TODO: add description under each item
 * TODO: create onclick listeners for checking the checkboxes and expanding each item's description
 * TODO: add progress bar
 * TODO: update progress bar when checkbox is clicked
 */

public class TaskListActivity extends AppCompatActivity {

    private Context context;
    private String[] testData;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private TaskListAdapter taskViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ArrayList<String> taskList;
    private TextView employeeNameTextView;
    private TextView employeeNameIdTextView;

    // Progress Bar
    private static ProgressBar simpleProgressBar;
    private int countOfCompletedTasks = 0;

    //test variables
    private String employeeName = "LukeTestEmployee";
    private String employeeId = "LukeTest123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        context = getApplicationContext();

         /*Enables tool bar & sets title displayed
        Can customize menu items in res/menu/main_menu.xml
        Can customize toolbar in res/layout/toolbar_layout.xml*/
        Toolbar myToolbar = findViewById(R.id.main_toolbar);
        myToolbar.setTitle("Task List");
        setSupportActionBar(myToolbar);

        //Declare the ActionBar with the employee name and ID in it
        ActionMenuView employeeProgressBar = (ActionMenuView) findViewById(R.id.progress_toolbar);

        // Set the employee name and ID,
        // this can be done with the API functions,
        // or from the stored information we get from the API later,
        // perhaps this would be more effective in a method later.
        employeeNameTextView = (TextView)findViewById(R.id.EmployeeName);
        employeeNameTextView.setText(employeeName);

        employeeNameIdTextView = (TextView)findViewById(R.id.EmployeeID);
        employeeNameIdTextView.setText(employeeId);

        // contains dummy data from arrays.xml
        // either we change this variable name and continue to get the data from the array, or we
        // call the API directly here?
        testData = getResources().getStringArray(R.array.task_list);

        // converts array into a list for use with the adapter
        //taskList = new ArrayList<>(Arrays.asList(testData));
        //TESTING DATA
        ArrayList<Task> taskList = new ArrayList<Task>();

        for(int i = 0; i < testData.length; i++) {
            String id = "" + i;

            TaskDescription expandableDescription = new TaskDescription(id, testData[i]);
            ArrayList<TaskDescription> expandableDescriptionArrayList = new ArrayList<TaskDescription>();
            expandableDescriptionArrayList.add(expandableDescription);

            taskList.add(new Task(id, testData[i], testData[i], testData[i],  testData[i], expandableDescriptionArrayList ));
        }

        // initiate the progress bar and set it's initial max
        simpleProgressBar=(ProgressBar) findViewById(R.id.task_progressBar);
        // use the array from onCreate to set the progress bars max value
        simpleProgressBar.setMax(testData.length); // 100 maximum value for the progress bar

        relativeLayout = (RelativeLayout) findViewById(R.id.activity_task_list);

        recyclerView = (RecyclerView) findViewById(R.id.task_list_recycler);
        recyclerViewLayoutManager = new LinearLayoutManager(this);

        taskViewAdapter = new TaskListAdapter(context, taskList);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(taskViewAdapter);



    }

    /**
     * Helper method to calculate and redraw the progress bar whenever a checkbox is checked
     */
    public static void ProgressBarIncrement(int increment) {

        /* I might need this code later
        //int maxValue=simpleProgressBar.getMax(); // get maximum value of the progress bar
        //simpleProgressBar.setProgress(50); // 50 default progress value for the progress bar
        //int progressValue=simpleProgressBar.getProgress(); // get progress value from the progress bar
        */

        // let the built in increment method do the work. Also works on a negative increment.
        simpleProgressBar.incrementProgressBy(increment);
    }
}
