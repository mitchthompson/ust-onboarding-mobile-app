package com.example.practicumapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.practicumapp.Interfaces.VolleyResponseListener;
import com.example.practicumapp.helpers.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Suraj Upreti
 * Singleton class for volley
 * This singleton class for volley allows to set up a single RequestQueue instance that will last the lifetime of the app,
 * instead of recreating the queue every time the activity is recreated (Example : when the device is rotated).
 */

public class MyVolleySingleton {

    private static MyVolleySingleton singletonInstance; // Instance of MyVolleySingleton class
    private RequestQueue requestQueue; // Volley's request queue
    private static Context appContext; // Application context

    /**
     * Constructor
     *
     * @param context Application Context
     */
    private MyVolleySingleton(Context context) {
        appContext = context;
        requestQueue = getRequestQueue(); // Get the request queue
    }

    /**
     * Check and create a new instance if not created
     *
     * @param context Application context
     * @return singletonInstance MyVolleySingleton instance
     */
    public static synchronized MyVolleySingleton getInstance(Context context) {
        if (singletonInstance == null) { // If Instance is null
            singletonInstance = new MyVolleySingleton(context); // Initialize new Instance
        }
        return singletonInstance;
    }

    /**
     * Check and create a new request queue for volley, if null
     *
     * @return requestQueue Current request queue for volley
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) { // If RequestQueue is null the initialize new RequestQueue
            requestQueue = Volley.newRequestQueue(appContext.getApplicationContext());
        }
        return requestQueue; // Return RequestQueue
    }

    /**
     * Add a request to the request queue
     *
     * @param request Request of generic type
     */
    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request); // Add the specified request to the request queue
    }

    /**
     * Request a JSON file from the API and call an interface method on success
     *
     * @param requestMethod  Request method (GET/POST/PUT/DELETE)
     * @param url            URL to call API
     * @param callback       Interface callback
     * @throws VolleyError   On volley error
     * @throws JSONException On JSON error
     */
    public void sendObjectRequest(final String accessToken, int requestMethod, String url, final HashMap<String, String> parameters, final VolleyResponseListener callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (requestMethod, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("MyVolleySingleton", "Object Error : " + error.toString());
                    }
                }) {

            @Override
            public byte[] getBody() {
                JSONObject object = new JSONObject(parameters);
                if (object.has("Tasks")) {
                    JSONArray tasks;
                    try {
                        tasks = new JSONArray(parameters.get("Tasks"));
                        object.put("Tasks", tasks);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (object.has("employees")) {
                    JSONArray jsonArray;
                    try {
                        JSONObject employees = new JSONObject(parameters.get("employees"));
                        object.put("employees", employees);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    object.put("StartDate", "12-22-2000");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("MyVolleySingleton", object.toString());
                return object.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Ocp-Apim-Subscription-Key", Constants.SUBSCRIPTION_KEY);
                headers.put("Authorization", accessToken);
                return headers;
            }
        };
        this.addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Request a JSON array from the API and call an interface method on success
     *
     * @param url      URL to call API
     * @param callback Interface callback
     * @throws JSONException on volley error
     */
    public void sendArrayRequest(final String accessToken, String url, final VolleyResponseListener callback) {
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("list", response);
                    callback.onSuccess(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MyVolleySingleton", "Array Error : " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Ocp-Apim-Subscription-Key", Constants.SUBSCRIPTION_KEY);
                headers.put("Authorization", accessToken);
                return headers;
            }
        };
        this.addToRequestQueue(jsonObjectRequest);
    }
}