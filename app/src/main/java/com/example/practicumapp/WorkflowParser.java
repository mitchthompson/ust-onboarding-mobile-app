package com.example.practicumapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.example.practicumapp.Interfaces.VolleyCallback;
import com.example.practicumapp.Interfaces.WorkflowCallback;
import com.example.practicumapp.models.Task;
import com.example.practicumapp.models.Workflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Workflow related json parser class
 * This class sends and retrieves workflow JSON data from the API using volley
 */

public class WorkflowParser {

    private Context context; // Application Context
    private static final String WORKFLOW_API_ADDRESS = "https://virtserver.swaggerhub.com/kari_bullard/Cloud-Practicum/1.0.2/workflow"; // URL to retrieve JSON data

    /*
     * Constructor
     * @param context Context of current state of application/object
     */
    public WorkflowParser (Context context) {
        this.context = context;
    }

//  TODO Function to create a new workflow
    public void createNewWorkflow(JSONObject workflow) {

    }

//  TODO Function to retrieve a workflow by id, return workflow JSON object
    public void getWorkflow(String workflowID, final WorkflowCallback workflowCallback) {
        String urlWithParams = WORKFLOW_API_ADDRESS + "/" + workflowID;
        MyVolleySingleton.getInstance(context).
                sendVolleyRequest(Request.Method.GET, urlWithParams, null, new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            ArrayList<Task> tasks = new ArrayList<>();
                            String id = response.getString("id");
                            String name = response.getString("name");
                            String description = response.getString("description");
                            JSONArray jsonArray = response.getJSONArray("tasks");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String taskID = jsonObject.getString("id");
                                String taskName = jsonObject.getString("name");
                                String taskDescription = jsonObject.getString("description");
                                String employeeInstructions = jsonObject.getString("employeeInstructions");
                                String managerInstructions = jsonObject.getString("managerInstructions");
                                Task singleTask = new Task(taskID, taskName, taskDescription, employeeInstructions, managerInstructions);
                                tasks.add(singleTask);
                            }
                            Workflow workflow = new Workflow(id, name, description, tasks);
                            workflowCallback.onSuccess(workflow);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

//  TODO Function to update an existing workflow
    public void updateWorkflow(String workflowID, JSONObject workflow) {

    }

//  TODO Function to delete an existing workflow
    public void deleteWorkflow(String workflowID) {

    }
}
