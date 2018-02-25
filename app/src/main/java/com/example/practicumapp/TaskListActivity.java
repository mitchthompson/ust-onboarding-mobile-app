package com.example.practicumapp;

import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.practicumapp.Interfaces.VolleyUserResponseListener;
import com.example.practicumapp.Interfaces.VolleyWorkflowResponseListener;
import com.example.practicumapp.adapters.TaskListAdapter;
import com.example.practicumapp.models.Task;
import com.example.practicumapp.models.TaskDescriptionListItem;
import com.example.practicumapp.models.TaskListItem;
import com.example.practicumapp.models.User;
import com.example.practicumapp.models.Workflow;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.HashMap;

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
 * TODO: create onclick listeners for checking the checkboxes and expanding each item's description
 * TODO: update progress bar when checkbox is clicked
 */

public class TaskListActivity extends OptionsMenu {
    //TAG for logging
    private static final String TAG = TaskListActivity.class.getName(); // Constant for logging data

    //Adapter is the only one that the expanding recycler folks declared outside of their OnCreate
    private TaskListAdapter adapter;
    private String[] testData;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;

    private ProgressBar simpleProgressBar;


    // variables for...why?
    private ArrayList taskList;
    private String employeeId = "72AD9DBC60AE485782D43A1AE09279A4";
    private String employeeName;
    private String workflowId;
    private ArrayList completedTasks;

    private ExpandableGroup<TaskListItem> expandableTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        // Go and grab all our UI Elements from the layouts
        TextView employeeNameTextView = (TextView)findViewById(R.id.EmployeeName);
        TextView employeeIdTextView = (TextView)findViewById(R.id.EmployeeID);
        Toolbar myToolbar = findViewById(R.id.main_toolbar);
        ActionMenuView progressActionMenu = (ActionMenuView) findViewById(R.id.progress_toolbar);
        simpleProgressBar = (ProgressBar) findViewById(R.id.task_progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.task_list_recycler);

        // set layout manager for Recycler View
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        myToolbar.setTitle("Task List");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //check for a passed in bundle of userID/name and set it if it exists
        if(getIntent().hasExtra("userID")) {
            Bundle bundle = getIntent().getExtras();
            employeeId = bundle.getString("userID");
            employeeName = bundle.getString("name");
        }
        // else tell the user that something got weird in the UI
        else {
            employeeName = "Not Included in the Bundle";
        }

        employeeNameTextView.setText(employeeName);
        employeeIdTextView.setText(employeeId);

        VolleyParser volleyParser = new VolleyParser(this.getApplicationContext());

        //Get the data we need from the User
        volleyParser.getUser(employeeId, new VolleyUserResponseListener() {
            @Override
            public void onSuccess(User user) {
                // Get the workflow ID specific to the User!
                workflowId = user.getWorkflow();

                // confirm receipt of something...
                Log.d(TAG, "VolleyParser User First Name : " + user.getFirstName());

                // Get the tasks that the user has completed!
                completedTasks = user.getTasks();
            }
        });

        //Get the data we need from the workflow.
        volleyParser.getWorkflow(workflowId, new VolleyWorkflowResponseListener() {
            @Override
            public void onSuccess(Workflow workflow) {
                // confirm receipt of something
                Log.d(TAG, "VolleyParser Workflow Task Name : " + workflow.getTasks().get(0).getName());

                // ArrayList of Tasks
                taskList = workflow.getTasks();

                // taskListItems ArrayList is needed to feed to the RecyclerView
                ArrayList<TaskListItem> taskListItems = new ArrayList<>();

                // iterate through taskList and populate taskListItems
                for(int i = 0; i < taskList.size(); i++) {
                    Task task = (Task) taskList.get(i);
                    String taskName = task.getName();
                    HashMap<String, String> taskDescriptionMap = task.getDescriptions();
                    ArrayList<TaskDescriptionListItem> taskDescriptionListItems = new ArrayList<>();
                    if(taskDescriptionMap.containsKey("manager")) {
                        taskDescriptionListItems.add(new TaskDescriptionListItem(taskDescriptionMap.get("manager")));
                    }
                    if(taskDescriptionMap.containsKey("employee")) {
                        taskDescriptionListItems.add(new TaskDescriptionListItem(taskDescriptionMap.get("employee")));
                    }
                    taskListItems.add(new TaskListItem(taskName,taskDescriptionListItems));
                }

                // TODO: Logic to determine whether User has completed a task or not

                // recyclerView gets kicked off in here, because we know we have data to display.
                relativeLayout = (RelativeLayout) findViewById(R.id.activity_task_list);
                adapter = new TaskListAdapter(taskListItems);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                // progress bar size depends on the size of the tasklist.
                simpleProgressBar.setMax(taskList.size());

                // TODO: OnClick Listener for the Checkbox
            }
        });
    }

    /**
     * Helper method to calculate and redraw the progress bar whenever a checkbox is checked
     */
    public void ProgressBarIncrement(int increment) {

        /* I might need this code later
        //int maxValue=simpleProgressBar.getMax(); // get maximum value of the progress bar
        //simpleProgressBar.setProgress(50); // 50 default progress value for the progress bar
        //int progressValue=simpleProgressBar.getProgress(); // get progress value from the progress bar
        */

        // let the built in increment method do the work. Also works on a negative increment.
        simpleProgressBar.incrementProgressBy(increment);
    }
}
