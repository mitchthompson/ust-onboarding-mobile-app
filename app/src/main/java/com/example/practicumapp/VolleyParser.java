package com.example.practicumapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.example.practicumapp.Interfaces.VolleyResponseListener;
import com.example.practicumapp.Interfaces.VolleyUserResponseListener;
import com.example.practicumapp.Interfaces.VolleyWorkflowResponseListener;
import com.example.practicumapp.Interfaces.VolleyListResponseListener;
import com.example.practicumapp.models.Task;
import com.example.practicumapp.models.User;
import com.example.practicumapp.models.Workflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Suraj Upreti
 * JSON parser class for User, Workflow and Task
 * This class sends and retrieves JSON data from the API using volley
 */

public class VolleyParser {

    private Context context; // Application Context
    private String accessToken;
    private static final String TAG = "VolleyParser";
    private static final String API_ADDRESS = "http://nsc420winter2018practicum.azure-api.net/dev/"; // URL to retrieve JSON data

    /**
     * Constructor
     * @param context Context of current state of application/object
     */
    public VolleyParser(Context context, String accessToken) {
        this.context = context;
        this.accessToken = accessToken;
    }

    /**
     * Add a new employee
     * @param newHire user object of the new user
     */
    public void addUser(final User newHire, final VolleyUserResponseListener volleyUserResponseListener) {
        String urlWithParams = API_ADDRESS + "user/";
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("ActiveDirectoryId", newHire.getActiveDirectoryID());
        parameters.put("firstName", newHire.getFirstName());
        parameters.put("lastName", newHire.getLastName());
        parameters.put("email", newHire.getEmail());
        parameters.put("phone", newHire.getPhone());
        parameters.put("type", newHire.getType());
        if (newHire.getType().equals("employee")) {
            parameters.put("manager", newHire.getManager());
        }
        parameters.put("StartDate", newHire.getStartDate());
        parameters.put("Workflow", newHire.getWorkflow());
        MyVolleySingleton.getInstance(context)
                .sendObjectRequest(accessToken, Request.Method.POST, urlWithParams, parameters, new VolleyResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        Log.d(TAG, "User Added Successfully");
//                        volleyUserResponseListener.onSuccess(newHire);
                        if (newHire.getType().equals("employee")) {
                            Log.d(TAG, "Adding Manager");
                            String newHireName = newHire.getFirstName() + " " + newHire.getLastName();
                            assignEmployeeToManager(newHire.getManager(), newHire.getActiveDirectoryID(), newHireName, volleyUserResponseListener);
                        }
                    }
                });
    }

    /**
     * Get a user info from the API and callback on success
     * @param userID User id
     * @param volleyUserResponseListener Interface definition for a callback to be invoked when a response is received
     * @exception JSONException JSON parsing error exception
     */
    public void getUser(String userID, final VolleyUserResponseListener volleyUserResponseListener) {
        String urlWithParams = API_ADDRESS + "user/" + userID;
        MyVolleySingleton.getInstance(context)
                .sendObjectRequest(accessToken, Request.Method.GET, urlWithParams, new HashMap<String, String>(), new VolleyResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        volleyUserResponseListener.onSuccess(getUserObject(response));
                    }
                });
    }

    /**
     * Get all users from the API and callback on success
     * @param volleyListResponseListener Interface definition for a callback to be invoked when a response is received
     * @exception JSONException JSON parsing error exception
     */
    public void getUsers(final VolleyListResponseListener volleyListResponseListener) {
        String urlWithParams = API_ADDRESS + "user/";
        MyVolleySingleton.getInstance(context).sendArrayRequest(accessToken, urlWithParams, new VolleyResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                ArrayList<User> users = new ArrayList<>();
                try {
                    JSONArray usersList = response.getJSONArray("list");
                    for (int j = 0; j < usersList.length(); j++) {
                        JSONObject singleUser = usersList.getJSONObject(j);
                        users.add(getUserObject(singleUser));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyListResponseListener.onSuccess(users);
            }
        });
    }

    /**
     * Update an existing user
     * @param userID User id
     * @param user Updated user object of an existing user
     */
    public void updateUser(String userID, final User user, final VolleyUserResponseListener userResponseListener) {
        final String urlWithParams = API_ADDRESS + "user/" + userID;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("id", user.getId());
        parameters.put("ActiveDirectoryId", user.getActiveDirectoryID());
        parameters.put("firstName", user.getFirstName());
        parameters.put("lastName", user.getLastName());
        parameters.put("email", user.getEmail());
        parameters.put("phone", user.getPhone());
        parameters.put("type", user.getType());
        if (user.getType().equals("employee")) {
            parameters.put("manager", user.getManager());
        }
        parameters.put("StartDate", user.getStartDate());
        parameters.put("Workflow", user.getWorkflow());
        parameters.put("Tasks", user.getTasks().toString());


        JSONObject jsonObject = new JSONObject();
        Map<String, String> employees = user.getEmployees();
        if (employees != null) {
            try {
                for (Map.Entry<String, String> employee : employees.entrySet()) {
                    jsonObject.put(employee.getKey(), employee.getValue());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        parameters.put("employees", jsonObject.toString());
        MyVolleySingleton.getInstance(context)
                .sendObjectRequest(accessToken, Request.Method.PUT, urlWithParams, parameters, new VolleyResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        Log.d(TAG, "Updated User Data : " + response.toString());
                        Log.d(TAG, "User Updated Successfully");
                        userResponseListener.onSuccess(getUserObject(response));
                    }
                });
    }

    /**
     * Delete an existing user
     * @param userID User id
     */
    public void deleteUser(String userID) {
        //  TODO Code to delete an existing user
    }

    /**
     * Add a new workflow
     * @param workflow workflow object of a new workflow
     */
    public void addWorkflow(final Workflow workflow, final VolleyWorkflowResponseListener volleyWorkflowResponseListener) {
        //  TODO Code to create a new workflow
        String urlWithParams = API_ADDRESS + "workflow";
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("id", workflow.getId());
        parameters.put("name", workflow.getName());
        parameters.put("description", workflow.getDescription());
        parameters.put("tasks", workflow.getTasks().toString());

        MyVolleySingleton.getInstance(context)
                .sendObjectRequest(accessToken, Request.Method.POST, urlWithParams, parameters, new VolleyResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        volleyWorkflowResponseListener.onSuccess(workflow);
                        Log.d(TAG, "Workflow Added Successfully");
                    }
                });
    }

    /**
     * Get workflow info from the API and callback on success
     * @param workflowID Id of the workflow
     * @param volleyWorkflowResponseListener Interface definition for a callback to be invoked when a response is received
     * @exception JSONException JSON parsing error exception
     */
    public void getWorkflow(String workflowID, final VolleyWorkflowResponseListener volleyWorkflowResponseListener) {
        String urlWithParams = API_ADDRESS + "workflows/" + workflowID;
        MyVolleySingleton.getInstance(context).
                sendObjectRequest(accessToken, Request.Method.GET, urlWithParams, new HashMap<String, String>(), new VolleyResponseListener() {
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
                                Integer taskID = jsonObject.getInt("Id");
                                String taskName = jsonObject.getString("Name");
                                JSONObject taskDescriptionsObject = jsonObject.getJSONObject("Descriptions");
                                HashMap<String, String> taskDescriptions = new HashMap<String, String>();
                                if (taskDescriptionsObject.has("Employee")) {
                                    taskDescriptions.put("employee", taskDescriptionsObject.getString("Employee"));
                                }
                                if (taskDescriptionsObject.has("Manager")) {
                                    taskDescriptions.put("manager", taskDescriptionsObject.getString("Manager"));
                                }

                                String viewers = jsonObject.getString("Viewers");
                                Task singleTask = new Task(taskID, taskName, taskDescriptions, viewers);
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
     * Get workflows list from the API and callback on success
     * @param volleyListResponseListener Interface definition for a callback to be invoked when a response is received
     * @exception JSONException JSON parsing error exception
     */
    public void getWorkflows(final VolleyListResponseListener volleyListResponseListener) {
        String urlWithParams = API_ADDRESS + "workflows/";
        MyVolleySingleton.getInstance(context).sendArrayRequest(accessToken, urlWithParams, new VolleyResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                ArrayList<Workflow> workflows = new ArrayList<>();
                try {
                    JSONArray workflowsList = response.getJSONArray("list");
                    for (int j = 0; j < workflowsList.length(); j++) {
                        JSONObject singleObject= workflowsList.getJSONObject(j);
                        Workflow workflow = new Workflow(singleObject.getString("id"), singleObject.getString("name"));
                        workflows.add(workflow);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyListResponseListener.onSuccess(workflows);
            }
        });
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
     * Delete an existing workflow
     * @param workflowID Workflow id
     */
    public void deleteWorkflow(String workflowID) {
        //  TODO Code to delete an existing workflow
    }

    /**
     * Assign an employee to a manager
     * @param managerID Manager id
     * @param employeeID Employee id
     * @param employeeName Employee name
     * @param volleyUserResponseListener
     */
    public void assignEmployeeToManager(final String managerID, final String employeeID, final String employeeName, final VolleyUserResponseListener volleyUserResponseListener) {
        getUser(managerID, new VolleyUserResponseListener() {
            @Override
            public void onSuccess(User user) {
                User manager = user;
                manager.addEmployee(employeeID, employeeName);
                updateUser(manager.getActiveDirectoryID(), manager, volleyUserResponseListener);
            }
        });
    }

    /**
     * Mark a task as completed
     * @param userID User id
     * @param taskID Task id
     */
    public void markTaskAsCompleted(final String userID, final Integer taskID, final VolleyUserResponseListener volleyUserResponseListener) {
        getUser(userID, new VolleyUserResponseListener() {
            @Override
            public void onSuccess(User user) {
                user.markTaskAsCompleted(taskID);
                updateUser(userID, user, volleyUserResponseListener);
            }
        });
    }

    /**
     * Mark a task as incomplete
     * @param userID User id
     * @param taskID Task id
     */
    public void markTaskAsInComplete(final String userID, final Integer taskID, final VolleyUserResponseListener volleyUserResponseListener) {
        getUser(userID, new VolleyUserResponseListener() {
            @Override
            public void onSuccess(User user) {
                user.markTaskAsIncomplete(taskID);
                updateUser(userID, user, volleyUserResponseListener);
            }
        });
    }

    /**
     * Parse a JSON object and return an user object
     * @param object User JSON object
     * @return user User object
     */
    public User getUserObject(JSONObject object) {
        try {
            JSONObject employeesObject = new JSONObject();
            JSONArray tasksObject = new JSONArray();
            String managerID = "";
            String id = object.getString("id");
            String activeDirectoryID = object.getString("ActiveDirectoryId");
            String firstName = object.getString("firstName");
            String lastName = object.getString("lastName");
            String email = object.getString("email");
            String phone = object.getString("phone");
            String type = object.getString("type");
            if (type.equals("employee") && !object.isNull("manager")) {
                managerID = object.getString("manager");
            }
            String startDate = object.getString("StartDate");
            String workflow = object.getString("Workflow");
            if (!object.isNull("employees") && type.equals("manager") ) {
                employeesObject = object.getJSONObject("employees");
            }
            if (!object.isNull("Tasks") ) {
                tasksObject = object.getJSONArray("Tasks");
            }
            HashMap<String, String> employees = new HashMap<String, String>();
            ArrayList<Integer> tasks = new ArrayList<>();
            for (int i = 0; i < employeesObject.length(); i++) {
                employees.put(employeesObject.names().getString(i), employeesObject.get(employeesObject.names().getString(i)).toString());
            }
            for (int i = 0; i < tasksObject.length(); i++) {
                tasks.add(Integer.parseInt(tasksObject.get(i).toString()));
            }
            if (type.equals("employee")) {
                return new User(id, activeDirectoryID, firstName, lastName, email, phone, type, managerID, startDate, workflow, tasks);
            } else {
                return new User(id, activeDirectoryID, firstName, lastName, email, phone, type, startDate, employees, workflow, tasks);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}