package com.example.practicumapp;

import android.content.Context;

import com.android.volley.Request;
import com.example.practicumapp.Interfaces.UserResponseCallback;
import com.example.practicumapp.Interfaces.VolleyCallback;
import com.example.practicumapp.models.User;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * User related json parser class
 * This class sends and retrieves user JSON data from the API using volley
 */

public class UserParser {

    private Context context; // Application Context
    private static final String USER_API_ADDRESS = "https://virtserver.swaggerhub.com/kari_bullard/Cloud-Practicum/1.0.2/user"; // URL to retrieve JSON data

    /*
     * Constructor
     * @param context Context of current state of application/object
     */
    public UserParser(Context context) {
        this.context = context;
    }


//  Create (POST) functions

//  TODO Function to add a new user (param: JSON user object)
    public void addNewUser(JSONObject user) {

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


//  Read (GET) functions

//  TODO Function to retrieve a user by id, return user JSON object
    /*
     * Get the user info from the API and callback on success
     * @param userID Id of the user
     * @param UserDataCallback Interface callback
     * @exception JSONException On JSON error
     */
    public void getUser(String userID, final UserResponseCallback userResponseCallback ) {
        String urlWithParams = String.format(USER_API_ADDRESS + "/$s", userID);
        MyVolleySingleton.getInstance(context)
        .sendVolleyRequest(Request.Method.GET, urlWithParams, null, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    String id = response.getString("id");
                    String userName = response.getString("userName");
                    String firstName = response.getString("firstName");
                    String lastName = response.getString("lastName");
                    String email = response.getString("email");
                    String phone = response.getString("phone");
                    String type = response.getString("type");
                    User user = new User(id, userName, firstName, lastName, email, phone, type);
                    userResponseCallback.onSuccess(user);
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


//  Update (UPDATE) functions

//  TODO Function to update an existing user
    public void updateUser(String userID, JSONObject userObject) {

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

}