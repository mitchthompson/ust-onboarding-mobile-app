package com.example.practicumapp;

import android.content.Context;
import com.android.volley.Request;
import com.example.practicumapp.Interfaces.VolleyTaskResponseListener;
import com.example.practicumapp.Interfaces.VolleyUserResponseListener;
import com.example.practicumapp.Interfaces.VolleyResponseListener;
import com.example.practicumapp.Interfaces.VolleyWorkflowResponseListener;
import com.example.practicumapp.models.Task;
import com.example.practicumapp.models.User;
import com.example.practicumapp.models.Workflow;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * JSON parser class for User, Workflow and Task
 * This class sends and retrieves JSON data from the API using volley
 */

public class VolleyParser {

    private Context context; // Application Context
    private static final String API_ADDRESS = "https://virtserver.swaggerhub.com/kari_bullard/Cloud-Practicum/1.0.4"; // URL to retrieve JSON data

    /**
     * Constructor
     * @param context Context of current state of application/object
     */
    public VolleyParser(Context context) {
        this.context = context;
    }

//  Create (POST) functions

    //  TODO Function to add a new user (param: JSON user object)
    public void addNewUser(User user) {

    }

    //  TODO Function to add a completed task by a specific user (param:
    public void markTaskAsCompleted(String userID, String taskID) {

    }

    //  TODO Function to assign user to a workflow
    public void assignUserToWorkflow(String userID, String workflowID) {

    }

    //  TODO Function to assign an employee to a manager
    public void assignEmployeeToManager(String managerID, String employeeID) {

    }

    //  TODO Function to create a new workflow
    public void createNewWorkflow(Workflow workflow) {

    }

    //  TODO Function to create a new task
    public void createNewTask(Task task) {

    }

//  Read (GET) functions

    /**
     * Get the user info from the API and callback on success
     * @param userID Id of the user
     * @param volleyUserResponseListener Interface callback
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
                            String manager = response.getString("manager");
                            String workflow = response.getString("workflow");
                            JSONArray employeesJSONArray = response.getJSONArray("employees");
                            ArrayList<String> employees = new ArrayList<>();
                            for (int i = 0; i < employeesJSONArray.length(); i++) {
//                                employees.add(String.valueOf(employeesJSONArray.getInt(i)));
                            }
                            JSONArray tasksJSONArray = response.getJSONArray("tasks");
                            ArrayList<String> tasks = new ArrayList<>();
                            for (int i = 0; i < tasksJSONArray.length(); i++) {
//                                tasks.add(String.valueOf(tasksJSONArray.getInt(i)));
                            }
                            User user = new User(id, firstName, lastName, email, phone, type, manager, employees, workflow, tasks);
                            volleyUserResponseListener.onSuccess(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //  TODO Function to return all completed tasks, return task ids JSON array
    public void getCompletedTasks(String userID) {

    }

    //  TODO Function to return all employees assigned to a manager, return employee ids JSON array
    public void getAssignedEmployees(String managerID) {

    }

    /**
     * Get workflow info from the API and callback on success
     * @param workflowID Id of the workflow
     * @param volleyWorkflowResponseListener Interface callback
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
                                String taskDescription = jsonObject.getString("description");
                                String employeeInstructions = jsonObject.getString("employeeInstructions");
                                String managerInstructions = jsonObject.getString("managerInstructions");
                                Task singleTask = new Task(taskID, taskName, taskDescription, employeeInstructions, managerInstructions);
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
     * @param volleyTaskResponseListener Interface callback
     */
    public void getTask(String taskID, final VolleyTaskResponseListener volleyTaskResponseListener) {
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
                                Task task = new Task(taskID, taskName, taskDescription, employeeInstructions, managerInstructions);
                                volleyTaskResponseListener.onSuccess(task);
                            } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }

//  Update (UPDATE) functions

    //  TODO Function to update an existing user
    public void updateUser(String userID, User user) {

    }

    //  TODO Function to update an existing workflow
    public void updateWorkflow(String workflowID, Workflow workflow) {

    }

    //  TODO Function to update an existing task
    public void updateTask(String taskID, Task task) {

    }

//  Delete (DELETE) functions

    //  TODO Function to delete an existing user
    public void deleteUser(String userID) {

    }

    //  TODO Function to delete a completed task
    public void markTaskAsIncomplete(String userID, String taskID) {

    }

    //  TODO Function to unassign a user from a workflow
    public void removeUserFromWorkflow(String userID) {

    }

    //  TODO Function to unassign an employee from a manager
    public void removeUserFromManager(String managerID, String userID) {

    }

    //  TODO Function to delete an existing workflow
    public void deleteWorkflow(String workflowID) {

    }

    //  TODO Function to delete an existing task
    public void deleteTask(String taskID) {

    }

}
