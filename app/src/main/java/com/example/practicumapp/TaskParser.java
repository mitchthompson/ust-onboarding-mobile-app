package com.example.practicumapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * This class sends and retrieves JSON data from the API using REST
 */

class TaskParser {

    private Context context; // Application Context

    private static final String TAG = TaskParser.class.getName(); // Constant for logging data

    private static final String URL = "http://www.mocky.io/v2/5a5d7d6033000056001917d7"; // URL to retrieve JSON data

    /*
     * Constructor
     * @param context Context of current state of application/object
     * @return Nothing
     */
    TaskParser(Context context) {
        this.context = context;
    }

    /*
     * Request the JSON file for all tasks from the URL and parse the JSON file
     * @exception JSONException On JSON error
     * @exception VolleyError on volley error
     * @return Nothing
     */
    void fetchAllTasks() {

        RequestQueue requestQueue = Volley.newRequestQueue(context); // Create a new request queue

        // Request the JSON file from the URL for all tasks
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Get proper objects and data from JSON file
                        try {
                            JSONArray tasks = response.getJSONArray("tasks");
                            for (int i = 0; i < tasks.length(); i++) {
                                JSONObject oneTask = tasks.getJSONObject(i);
                                Log.d(TAG, "Task : " + oneTask.get("task") + ", isCompleted: " + oneTask.get("isCompleted"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Handle volley error response
                        Log.d(TAG, "Error: " + error.toString());
                    }
                });
        requestQueue.add(jsonObjectRequest); // Add the JSON request to the request queue
    }

}
