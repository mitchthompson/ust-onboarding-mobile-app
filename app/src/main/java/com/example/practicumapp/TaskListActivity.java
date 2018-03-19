package com.example.practicumapp;

import android.graphics.Color;
import android.content.SharedPreferences;
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
import com.example.practicumapp.volley.VolleyParser;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This activity will show all tasks that an employee needs to complete
 * Data is fetched using API calls to get employee info and then workflow info
 */

public class TaskListActivity extends MainActivity {
    //TAG for logging
    private static final String TAG = "TaskListActivity"; // Constant for logging data

    //Adapter is the only one that the expanding recycler folks declared outside of their OnCreate
    private TaskListAdapter adapter;
    private String[] testData;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;

    public static ProgressBar simpleProgressBar;


    // taskList is the list of Tasks from the workflow
    private ArrayList taskList;

    public static String employeeId = "";
    private String employeeName = "";
    private String userType = "";
    private String workflowId;
    private ArrayList completedTasks;
    private static float countComplete = 0;
    private static float countTotal = 0;
    private int percentageComplete;

    private ExpandableGroup<TaskListItem> expandableTaskList;

    private VolleyParser volleyParser;

    static TextView completedPercentageTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        // Go and grab all our UI Elements from the layouts
        TextView employeeNameTextView = (TextView)findViewById(R.id.EmployeeName);
        completedPercentageTextView = (TextView) findViewById(R.id.task_completion_percentage);
        Toolbar myToolbar = findViewById(R.id.main_toolbar);
        ActionMenuView progressActionMenu = (ActionMenuView) findViewById(R.id.progress_toolbar);
        simpleProgressBar = (ProgressBar) findViewById(R.id.task_progressBar);

        //set height for progress bar
        simpleProgressBar.setScaleY(3f);

        // set color programmatically
        simpleProgressBar.getProgressDrawable().setColorFilter(
                Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

        recyclerView = (RecyclerView) findViewById(R.id.task_list_recycler);

        // set layout manager for Recycler View
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        myToolbar.setTitle("Task List");
        setSupportActionBar(myToolbar);

        // enables back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Retrieve access token from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        final String accessToken = sharedPreferences.getString("AccessToken", "");

        employeeId = sharedPreferences.getString("UserADID", "");
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("userID")) {
            employeeId = getIntent().getExtras().getString("userID");
        }
        volleyParser = new VolleyParser(this.getApplicationContext(), accessToken);

        //Get the data we need from the User
        volleyParser.getUser(employeeId, new VolleyUserResponseListener() {
            @Override
            public void onSuccess(User user) {
                // Get the workflow ID specific to the User!
                workflowId = user.getWorkflow();
                TextView employeeNameTextView = (TextView)findViewById(R.id.EmployeeName);
                employeeName = user.getFirstName() + " " + user.getLastName();
                employeeNameTextView.setText(employeeName);

                /* confirm receipt of something... */
                Log.d(TAG, "TaskListActivity: User First Name : " + user.getFirstName());
                Log.d(TAG, "TaskListActivity: User Type Returns : " + user.getType());

                // need the user type to know what task descriptions to show.
                userType = user.getType();

                // Get the tasks that the user has completed!
                completedTasks = user.getTasks();

                //TODO: Removefollowing, for testing only
                //adding a completed task, "Setup employee Id"
//                completedTasks.add("LKJLKLIOC54D2DA2389CVBV98XCCVV");

                //Get the data we need from the workflow.
                volleyParser.getWorkflow(workflowId, new VolleyWorkflowResponseListener() {
                    @Override
                    public void onSuccess(Workflow workflow) {

                        // confirm receipt of something for testing
                        // Log.d(TAG, "TaskListActivity: Workflow Task Name : " + workflow.getTasks().get(0).getName());

                        // ArrayList of Tasks
                        taskList = workflow.getTasks();

                        // progress bar size depends on the size of the tasklist.
                        simpleProgressBar.setMax(taskList.size());
                        countTotal = taskList.size();

                        // set progress to 0 initially
                        simpleProgressBar.setProgress(0);
                        countComplete = 0;

                        // taskListItems ArrayList is needed to feed to the RecyclerView
                        ArrayList<TaskListItem> taskListItems = new ArrayList<>();

                        // iterate through taskList and populate taskListItems
                        for(int i = 0; i < taskList.size(); i++) {
                            Task task = (Task) taskList.get(i);
                            Integer taskId = task.getId();
                            String taskName = task.getName();
                            HashMap<String, String> taskDescriptionMap = task.getDescriptions();
                            ArrayList<TaskDescriptionListItem> taskDescriptionListItems = new ArrayList<>();

                            // TODO Remove sample if codes and uncomment if code below it
                            if (taskDescriptionMap.get("employee") != null) {
                                taskDescriptionListItems.add(new TaskDescriptionListItem(taskDescriptionMap.get("employee")));
                            }
                            if (taskDescriptionMap.get("manager") != null) {
                                taskDescriptionListItems.add(new TaskDescriptionListItem(taskDescriptionMap.get("manager")));
                            }

//                            if(userType.equals("manager")) {
//                                taskDescriptionListItems.add(new TaskDescriptionListItem(taskDescriptionMap.get("manager")));
//                            }
//                            if(userType.equals("employee")) {
//                                taskDescriptionListItems.add(new TaskDescriptionListItem(taskDescriptionMap.get("employee")));
//                            }


                            TaskListItem taskListItemToAdd = new TaskListItem(taskName,taskDescriptionListItems);
                            taskListItemToAdd.setTaskID(taskId);

                            // Check if this taskItem has been completed.
                            if(completedTasks.contains(taskId)) {
                                taskListItemToAdd.setChecked(true);
                                IncrementCompletedTasks(1);
                            }
                            taskListItems.add(taskListItemToAdd);
                        }

                        // recyclerView gets kicked off in here, because we know we have data to display.
                        relativeLayout = (RelativeLayout) findViewById(R.id.activity_task_list);
                        adapter = new TaskListAdapter(taskListItems, accessToken);
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
    public static void IncrementCompletedTasks(int increment) {
        // let the built in increment method do the work. Also works on a negative increment.
        simpleProgressBar.incrementProgressBy(increment);
        countComplete = countComplete + increment;
        float completedPercentageDecimal = (countComplete / countTotal) * 100;
        int completedPercentageInt = (int) completedPercentageDecimal;
        String completedPercentageText = "Complete: " + completedPercentageInt + "%" ;
        completedPercentageTextView.setText(completedPercentageText);
    }

    /**
     * Helper method to fetch the UserId of the user we are working on
     * @return employeeId
     */
    public static String SendUserId() {
        return employeeId;
    }
}