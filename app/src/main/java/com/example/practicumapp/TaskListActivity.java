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
 * This activity will show all tasks that an employee needs to complete
 * Data is fetched using API calls to get employee info and then workflow info
 */

public class TaskListActivity extends MainActivity {
    //TAG for logging
    private static final String TAG = TaskListActivity.class.getName(); // Constant for logging data

    //Adapter is the only one that the expanding recycler folks declared outside of their OnCreate
    private TaskListAdapter adapter;
    private String[] testData;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;

    public static ProgressBar simpleProgressBar;


    // taskList is the list of Tasks from the workflow
    private ArrayList taskList;

    public static String employeeId = "72AD9DBC60AE485782D43A1AE09279A4";
    private String employeeName = "";
    private String userType = "";
    private String workflowId;
    private ArrayList completedTasks;

    private ExpandableGroup<TaskListItem> expandableTaskList;

    private VolleyParser volleyParser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        // Go and grab all our UI Elements from the layouts
        TextView employeeNameTextView = (TextView)findViewById(R.id.EmployeeName);
        //TextView employeeIdTextView = (TextView)findViewById(R.id.EmployeeID);
        Toolbar myToolbar = findViewById(R.id.main_toolbar);
        ActionMenuView progressActionMenu = (ActionMenuView) findViewById(R.id.progress_toolbar);
        simpleProgressBar = (ProgressBar) findViewById(R.id.task_progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.task_list_recycler);

        // set layout manager for Recycler View
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        myToolbar.setTitle("Task List");
        setSupportActionBar(myToolbar);

        //check for a passed in bundle of userID/name and set it if it exists
        if(getIntent().hasExtra("userID")) {
            Bundle bundle = getIntent().getExtras();
            employeeId = bundle.getString("userID");
            employeeName = bundle.getString("name");
        }

        // else tell the user that something got weird in the UI
        else {
            employeeName = "Employee Name Not Included in the Bundle";
        }

        employeeNameTextView.setText(employeeName);
        //employeeIdTextView.setText(employeeId);

        volleyParser = new VolleyParser(this.getApplicationContext());

        //Get the data we need from the User
        volleyParser.getUser(employeeId, new VolleyUserResponseListener() {
            @Override
            public void onSuccess(User user) {
                // Get the workflow ID specific to the User!
                workflowId = user.getWorkflow();

                // confirm receipt of something...
                Log.d(TAG, "TaskListActivity: User First Name : " + user.getFirstName());
                Log.d(TAG, "TaskListActivity: User Type Returns : " + user.getType());

                // need the user type to know what task descriptions to show.
                userType = user.getType();

                // Get the tasks that the user has completed!
                completedTasks = user.getTasks();

                //TODO: Removefollowing, for testing only
                //adding a completed task, "Setup employee Id"
                completedTasks.add("LKJLKLIOC54D2DA2389CVBV98XCCVV");

                //Get the data we need from the workflow.
                volleyParser.getWorkflow(workflowId, new VolleyWorkflowResponseListener() {
                    @Override
                    public void onSuccess(Workflow workflow) {

                        // confirm receipt of something
                        Log.d(TAG, "TaskListActivity: Workflow Task Name : " + workflow.getTasks().get(0).getName());

                        // ArrayList of Tasks
                        taskList = workflow.getTasks();

                        // progress bar size depends on the size of the tasklist.
                        simpleProgressBar.setMax(taskList.size());
                        // set progress to 0 initially
                        simpleProgressBar.setProgress(0);

                        // taskListItems ArrayList is needed to feed to the RecyclerView
                        ArrayList<TaskListItem> taskListItems = new ArrayList<>();

                        // iterate through taskList and populate taskListItems
                        for(int i = 0; i < taskList.size(); i++) {
                            Task task = (Task) taskList.get(i);
                            String taskId = task.getId();
                            String taskName = task.getName();
                            HashMap<String, String> taskDescriptionMap = task.getDescriptions();
                            ArrayList<TaskDescriptionListItem> taskDescriptionListItems = new ArrayList<>();
                            if(userType.equals("manager")) {
                                taskDescriptionListItems.add(new TaskDescriptionListItem(taskDescriptionMap.get("manager")));
                            }
                            if(userType.equals("employee")) {
                                taskDescriptionListItems.add(new TaskDescriptionListItem(taskDescriptionMap.get("employee")));
                            }
                            TaskListItem taskListItemToAdd = new TaskListItem(taskName,taskDescriptionListItems);
                            taskListItemToAdd.setTaskID(taskId);

                            // Check if this taskItem has been completed.
                            if(completedTasks.contains(taskId)) {
                                taskListItemToAdd.setChecked(true);
                                ProgressBarIncrement(1);
                            }
                            taskListItems.add(taskListItemToAdd);
                        }

                        // recyclerView gets kicked off in here, because we know we have data to display.
                        relativeLayout = (RelativeLayout) findViewById(R.id.activity_task_list);
                        adapter = new TaskListAdapter(taskListItems);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);


                    }
                });
            }
        });
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

    public static String SendUserId() {
        return employeeId;
    }
}
