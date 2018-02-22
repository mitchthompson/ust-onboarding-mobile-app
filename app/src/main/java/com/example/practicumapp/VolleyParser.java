package com.example.practicumapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.example.practicumapp.Interfaces.VolleyResponseListener;
import com.example.practicumapp.Interfaces.VolleyUserResponseListener;
import com.example.practicumapp.Interfaces.VolleyWorkflowResponseListener;
import com.example.practicumapp.models.Task;
import com.example.practicumapp.models.User;
import com.example.practicumapp.models.Workflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * JSON parser class for User, Workflow and Task
 * This class sends and retrieves JSON data from the API using volley
 */

public class VolleyParser {

    private Context context; // Application Context
    private static final String API_ADDRESS = "https://virtserver.swaggerhub.com/kari_bullard/Cloud-Practicum/1.0.6"; // URL to retrieve JSON data

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
    public void addNewUser(final User user, final VolleyUserResponseListener volleyUserResponseListener) {
        String urlWithParams = API_ADDRESS + "/user";
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("firstName", user.getFirstName());
        parameters.put("lastName", user.getLastName());
        parameters.put("email", user.getEmail());
        parameters.put("phone", user.getPhone());
        parameters.put("startDate", user.getStartDate());
        parameters.put("workflow", user.getWorkflow());

        MyVolleySingleton.getInstance(context)
                .sendGETRequest(Request.Method.POST, urlWithParams, null, parameters, new VolleyResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
//                      TODO Return newly created user id
                        Log.d("VolleyParser", "User ID : " + response.toString());
                        volleyUserResponseListener.onSuccess(user);
                    }
                });
    }

    /**
     * Add a new workflow
     * @param workflow workflow object of a new workflow
     */
    public void createNewWorkflow(final Workflow workflow, final VolleyWorkflowResponseListener volleyWorkflowResponseListener) {
        //  TODO Code to create a new workflow
        String urlWithParams = API_ADDRESS + "/workflow";
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("id", workflow.getId());
        parameters.put("name", workflow.getName());
        parameters.put("description", workflow.getDescription());
        parameters.put("tasks", workflow.getTasks().toString());

        MyVolleySingleton.getInstance(context)
                .sendGETRequest(Request.Method.POST, urlWithParams, null, parameters, new VolleyResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
//                      TODO Return newly created workflow id
                        workflow.setID("f1f183b8169e11e8b6420ed5f89f718b");
                        volleyWorkflowResponseListener.onSuccess(workflow);
                        Log.d("VolleyParser", "Workflow ID : " + response.toString());
                    }
                });
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
                .sendGETRequest(Request.Method.GET, urlWithParams, null, new HashMap<String, String>(), new VolleyResponseListener() {
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

//                          TODO Remove hardcoded employees id and names
                            employees.put("aabca894f000444fab8fb5fba882c445", "Mitch Thompson");
                            employees.put("b8545e53bc484eb8869cd9e674ab5b2b", "Joseph Sayler");
                            employees.put("89de97e9c89249498abe48526fc801af", "Luke Schwarz");
                            employees.put("f2c9f53c0dc911e8ba890ed5f89f718b", "Suraj Upreti");
                            employees.put("9262e89b2a9a4d0ba07dd7a406f585a0", "Bonnie Peterson");
                            employees.put("3575121d5d9e417d85300d641f2828b7", "Justin Simmons");

                            JSONArray tasksObject = response.getJSONArray("tasks");
                            ArrayList<String> tasks = new ArrayList<>();
                            for (int i = 0; i < tasksObject.length(); i++) {
                                tasks.add(tasksObject.get(i).toString());
                            }

//                          TODO Remove hardcoded completed tasks ids
                            tasks.add("LKJLKLIOC54D2DA2389CVBV98XCCVV");
                            tasks.add("POIPIEJEWWC54D2DA23894IO098DSF");
                            tasks.add("NNBMBBBEE54D2DA23892300293ERIU");

                            User user = new User(id, firstName, lastName, email, phone, type, startDate, employees, workflow, tasks);
                            volleyUserResponseListener.onSuccess(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        String urlWithParams = API_ADDRESS + "/workflow/" + workflowID;
        MyVolleySingleton.getInstance(context).
                sendGETRequest(Request.Method.GET, urlWithParams, null, new HashMap<String, String>(), new VolleyResponseListener() {
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
                                    taskDescriptions.put(singleTaskDescription.names().getString(j),
                                            singleTaskDescription.get(singleTaskDescription.names().getString(j)).toString());
                                }

                                String viewers = jsonObject.getString("viewers");
                                Task singleTask = new Task(taskID, taskName, taskDescriptions, viewers);
                                tasks.add(singleTask);
                            }
//                            TODO Remove getSampleTasks method with tasks
                            Workflow workflow = new Workflow(id, name, description, getSampleTasks(tasks));
                            volleyWorkflowResponseListener.onSuccess(workflow);
                        } catch (JSONException e) {
                            e.printStackTrace();
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

//  Delete (DELETE) functions

    /**
     * Delete an existing user
     * @param userID User id
     */
    public void deleteUser(String userID) {
        //  TODO Code to delete an existing user
    }

    /**
     * Delete an existing workflow
     * @param workflowID Workflow id
     */
    public void deleteWorkflow(String workflowID) {
        //  TODO Code to delete an existing workflow
    }

//  TODO Remove getSampleTasks function (For testing purpose only)
    public ArrayList getSampleTasks(ArrayList tasks) {
        ArrayList sampleTasks = tasks;
        HashMap<String, String> taskDescriptions = new HashMap<String, String>();
        taskDescriptions.put("manager", "Do something to complete this task.");
        taskDescriptions.put("employee", "Do something to complete this task.");
        Task firstTask = new Task("ECCD3A6ED4C54D2DA23894IODSJKF3", "Setup AD login id and password", taskDescriptions, "manager");
        Task secondTask = new Task("DSKLFDJ4C54D2DA23894IO3894DSJK", "Setup company email address", taskDescriptions, "manager");
        Task thirdTask = new Task("LKJLKLIOC54D2DA2389CVBV98XCCVV", "Setup employee id", taskDescriptions, "manager");
        Task fourthTask = new Task("WEOIURE234C54D2DA209JKLSDFIUIO", "Get documents from HR", taskDescriptions, "manager");
        Task fifthTask = new Task("POIPIEJEWWC54D2DA23894IO098DSF", "Check with manager", taskDescriptions, "manager");
        Task sixthTask = new Task("NNBMBBBEE54D2DA23892300293ERIU", "Work on your first project", taskDescriptions, "manager");
        sampleTasks.add(firstTask);
        sampleTasks.add(secondTask);
        sampleTasks.add(thirdTask);
        sampleTasks.add(fourthTask);
        sampleTasks.add(fifthTask);
        sampleTasks.add(sixthTask);
        return sampleTasks;
    }
}