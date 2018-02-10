package com.example.practicumapp;

import android.content.Context;
import android.util.Log;


import com.android.volley.Request;
import com.example.practicumapp.Interfaces.VolleyResponseListener;
import com.example.practicumapp.Interfaces.VolleyTaskResponseListener;
import com.example.practicumapp.Interfaces.VolleyUserResponseListener;
import com.example.practicumapp.Interfaces.VolleyWorkflowResponseListener;
import com.example.practicumapp.models.Task;
import com.example.practicumapp.models.TaskDescription;
import com.example.practicumapp.models.User;
import com.example.practicumapp.models.Workflow;
import java.util.ArrayList;
import java.util.HashMap;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * JSON parser class for User, Workflow and Task
 * This class sends and retrieves JSON data from the API using volley
 */

public class VolleyParser {

    private Context context; // Application Context
    private static final String API_ADDRESS = "https://virtserver.swaggerhub.com/kari_bullard/Cloud-Practicum/1.0.5"; // URL to retrieve JSON data

    /**
     * Constructor
     * @param context Context of current state of application/object
     */
    public VolleyParser(Context context) {
        this.context = context;
    }

//  Create (POST) functions

    /**
     * Add a new user
     * @param user user object of the new user
     */
    public void addNewUser(User user) {
        //  TODO Code to add a new user
    }

    /**
     * Assign a user to a workflow
     * @param userID User id of a user
     * @param workflowID Workflow id
     */
    public void assignUserToWorkflow(String userID, String workflowID) {
        //  TODO Code to assign user to a workflow
    }

    /**
     * Assign an employee to a manager
     * @param managerID Manager id
     * @param employeeID Employee id
     */
    public void assignEmployeeToManager(String managerID, String employeeID) {
        //  TODO Code to assign an employee to a manager
    }

    /**
     * Add a new workflow
     * @param workflow workflow object of a new workflow
     */
    public void createNewWorkflow(Workflow workflow) {
        //  TODO Code to create a new workflow
    }

    /**
     * Add a new task
     * @param task Task object of a new task
     */
    public void createNewTask(Task task) {
        //  TODO Code to create a new task
    }

//  Read (GET) functions

    /**
     * Get the user info from the API and callback on success
     * @param userID User id
     * @param volleyUserResponseListener Interface definition for a callback to be invoked when a response is received
     * @exception JSONException JSON parsing error exception
     */
    public void getUser(String userID, final VolleyUserResponseListener volleyUserResponseListener) {
        String urlWithParams = API_ADDRESS + "/user/" + userID;
        MyVolleySingleton.getInstance(context)
                .sendVolleyRequest(Request.Method.GET, urlWithParams, null, new VolleyResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            String id = response.getString("id");
                            String firstName = response.getString("firstName");
                            String lastName = response.getString("lastName");
                            String email = response.getString("email");
                            String phone = response.getString("phone");
                            String type = response.getString("type");
                            String startDate = response.getString("startDate");
                            String workflow = response.getString("workflow");
                            JSONObject employeesObject = response.getJSONObject("employees");
                            HashMap<String, String> employees = new HashMap<String, String>();
                            for (int i = 0; i < employeesObject.length(); i++) {
                                employees.put(employeesObject.names().getString(i), employeesObject.get(employeesObject.names().getString(i)).toString());
                            }
//                            TODO GET task ids from the API and remove hardcoded task ids
//                            JSONArray tasksObject = response.getJSONArray("tasks");
                            ArrayList<String> tasks = new ArrayList<>();
                            for (int i = 0; i < 5; i++) {
//                                tasks.add(tasksObject.get(i).toString());
                                tasks.add("ECCD3A6ED4C54D2DA28C9CDD28F6417E");
                            }
                            User user = new User(id, firstName, lastName, email, phone, type, startDate, employees, workflow, tasks);
                            volleyUserResponseListener.onSuccess(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * Get completed task ids from the API
     * @param userID User id
     */
//    public void getCompletedTasks(String userID) {
        //  TODO Code to return all completed tasks, return task ids JSON array
//    }

    /**
     * Get all employees assigned to a manager
     * @param managerID Manager id
     */
    public void getAssignedEmployees(String managerID) {
        //  TODO Code to return all employees assigned to a manager, return employee ids JSON array
    }

    /**
     * Get workflow info from the API and callback on success
     * @param workflowID Id of the workflow
     * @param volleyWorkflowResponseListener Interface definition for a callback to be invoked when a response is received
     * @exception JSONException JSON parsing error exception
     */
    public void getWorkflow(String workflowID, final VolleyWorkflowResponseListener volleyWorkflowResponseListener) {
        String urlWithParams = API_ADDRESS + "/workflow/" + workflowID;
        MyVolleySingleton.getInstance(context).
                sendVolleyRequest(Request.Method.GET, urlWithParams, null, new VolleyResponseListener() {
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
                                JSONArray taskDescriptionsObject = jsonObject.getJSONArray("descriptions");
                                HashMap<String, String> taskDescriptions = new HashMap<String, String>();
                                for (int j = 0; j < taskDescriptionsObject.length(); j++) {
                                    JSONObject singleTaskDescription = taskDescriptionsObject.getJSONObject(j);
                                    taskDescriptions.put(singleTaskDescription.names().getString(j), singleTaskDescription.get(singleTaskDescription.names().getString(j)).toString());
                                }
                                String viewers = jsonObject.getString("viewers");

                                Task singleTask = new Task(taskID, taskName, taskDescriptions, viewers);
                                String taskDescription = jsonObject.getString("description");
                                String employeeInstructions = jsonObject.getString("employeeInstructions");
                                String managerInstructions = jsonObject.getString("managerInstructions");
                                TaskDescription expandableDescription = new TaskDescription(taskID, taskDescription);
                                ArrayList<TaskDescription> expandableDescriptionArrayList = new ArrayList<TaskDescription>();
                                expandableDescriptionArrayList.add(expandableDescription);

                                Task singleTask = new Task(taskID, taskName, taskDescription, employeeInstructions, managerInstructions, expandableDescriptionArrayList);
                                tasks.add(singleTask);
                            }
                            Workflow workflow = new Workflow(id, name, description, tasks);
                            volleyWorkflowResponseListener.onSuccess(workflow);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * Get task info from the API and callback on success
     * @param taskID Id of the workflow
     * @param volleyTaskResponseListener Interface definition for a callback to be invoked when a response is received
     * @exception JSONException JSON parsing error exception
     */
    public void getTask(String taskID, final VolleyTaskResponseListener volleyTaskResponseListener) {

        String id = "ECCD3A6ED4C54D2DA28C9CDD28F6417E";
        String taskName = "Cloud";
        HashMap<String, String> taskDescriptions = new HashMap<String, String>();
        taskDescriptions.put("manager", "Do something to complete this task.");
        taskDescriptions.put("employee", "Do something to complete this task.");
        String viewers = "manager";
        Task singleTask = new Task(id, taskName, taskDescriptions, viewers);
        volleyTaskResponseListener.onSuccess(singleTask);

//        TODO Remove hardcoded task details, uncomment code below after the API is updated
//        String urlWithParams = API_ADDRESS + "/task/" + taskID;
//        MyVolleySingleton.getInstance(context).
//                sendVolleyRequest(Request.Method.GET, urlWithParams, null, new VolleyResponseListener() {
//                    @Override
//                    public void onSuccess(JSONObject response) {
//                        Log.d("VOLLEYPARSER", "Task : " + response.toString());
//                        try {
//                            String taskID = response.getString("id");
//                            String taskName = response.getString("name");
//                            JSONArray taskDescriptionsObject = response.getJSONArray("descriptions");
//                            HashMap<String, String> taskDescriptions = new HashMap<String, String>();
//                            for (int j = 0; j < taskDescriptionsObject.length(); j++) {
//                                JSONObject singleTaskDescription = taskDescriptionsObject.getJSONObject(j);
//                                taskDescriptions.put(singleTaskDescription.names().getString(j), singleTaskDescription.get(singleTaskDescription.names().getString(j)).toString());
//                            }
//                            String viewers = response.getString("viewers");
//                            Log.d("VOLLEYPARSER", "Viewers : " + viewers);
//
//                            Task task = new Task(taskID, taskName, taskDescriptions, viewers);
//                            volleyTaskResponseListener.onSuccess(task);
//                        } catch (JSONException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                });
        String urlWithParams = API_ADDRESS + "/task/" + taskID;
        MyVolleySingleton.getInstance(context).
                sendVolleyRequest(Request.Method.GET, urlWithParams, null, new VolleyResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                                String taskID = response.getString("id");
                                String taskName = response.getString("name");
                                String taskDescription = response.getString("description");
                                String employeeInstructions = response.getString("employeeInstructions");
                                String managerInstructions = response.getString("managerInstructions");

                                TaskDescription expandableDescription = new TaskDescription(taskID, taskDescription);
                                ArrayList<TaskDescription> expandableDescriptionArrayList = new ArrayList<TaskDescription>();
                                expandableDescriptionArrayList.add(expandableDescription);

                                Task task = new Task(taskID, taskName, taskDescription, employeeInstructions, managerInstructions, expandableDescriptionArrayList);
                                volleyTaskResponseListener.onSuccess(task);
                            } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }

//  Update (UPDATE) functions

    /**
     * Update an existing user
     * @param userID User id
     * @param user Updated user object of an existing user
     */
    public void updateUser(String userID, User user) {
        //  TODO Code to update an existing user
    }

    /**
     * Update an existing workflow
     * @param workflowID Workflow id
     * @param workflow Updated workflow object of an existing workflow
     */
    public void updateWorkflow(String workflowID, Workflow workflow) {
        //  TODO Function to update an existing workflow
    }

    /**
     * Mark a specific task by a user as completed
     * @param userID User id of a user
     * @param taskID Task id
     */
    public void markTaskAsCompleted(String userID, String taskID) {
        //  TODO Code to mark a task as completed by a specific user
    }

    /**
     * Update an existing task
     * @param taskID Task id
     * @param task Updated task object of an existing task
     */
    public void updateTask(String taskID, Task task) {
        //  TODO Function to update an existing task
    }

//  Delete (DELETE) functions

    /**
     * Delete an existing user
     * @param userID User id
     */
    public void deleteUser(String userID) {
        //  TODO Code to delete an existing user
    }

    /**
     * Mark an existing task as incomplete
     * @param userID User id
     * @param taskID Task id
     */
    public void markTaskAsIncomplete(String userID, String taskID) {
        //  TODO Code to delete a completed task
    }

    /**
     * Remove an existing user from a workflow
     * @param userID User id
     */
    public void removeUserFromWorkflow(String userID) {
        //  TODO Code to unassign a user from a workflow
    }

    /**
     * Unassign a user from a manager
     * @param managerID Manager id
     * @param userID User id
     */
    public void removeUserFromManager(String managerID, String userID) {
        //  TODO Code to unassign an employee from a manager
    }

    /**
     * Delete an existing workflow
     * @param workflowID Workflow id
     */
    public void deleteWorkflow(String workflowID) {
        //  TODO Code to delete an existing workflow
    }

    /**
     * Delete an existing task
     * @param taskID Task id
     */
    public void deleteTask(String taskID) {
        //  TODO Code to delete an existing task
    }

}