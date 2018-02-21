package com.example.practicumapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.practicumapp.Interfaces.VolleyResponseListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class for volley
 * This singleton class for volley allows to set up a single RequestQueue instance that will last the lifetime of the app,
 * instead of recreating the queue every time the activity is recreated (Example : when the device is rotated).
 */

public class MyVolleySingleton {

        private static MyVolleySingleton singletonInstance; // instance of MyVolleySingleton class
        private RequestQueue requestQueue; // Volley's request queue
        private static Context appContext; // Application context

        /**
         * Constructor
         * @param context Application Context
         */
        private MyVolleySingleton(Context context){
            appContext = context;
            requestQueue = getRequestQueue(); // Get the request queue
        }

        /**
         * Check and create a new instance if not created
         * @param context Application context
         * @return singletonInstance MyVolleySingleton instance
         */
        public static synchronized MyVolleySingleton getInstance(Context context){
            if(singletonInstance == null){ // If Instance is null
                singletonInstance = new MyVolleySingleton(context); // Initialize new Instance
            }
            return singletonInstance;
        }

        /**
         * Check and create a new request queue for volley, if null
         * @return requestQueue Current request queue for volley
         */
        public RequestQueue getRequestQueue(){
            if(requestQueue == null){ // If RequestQueue is null the initialize new RequestQueue
                requestQueue = Volley.newRequestQueue(appContext.getApplicationContext());
            }
            return requestQueue; // Return RequestQueue
        }

        /**
         * Add a request to the request queue
         * @param request Request of generic type
         */
        public<T> void addToRequestQueue(Request<T> request){
            getRequestQueue().add(request); // Add the specified request to the request queue
        }

    /**
     * Request a JSON file from the API and call an interface method on success
     * @param requestMethod Request method (GET/POST/PUT/DELETE)
     * @param url URL to call API
     * @param jsonObject Json object for POST call
     * @param callback Interface callback
     * @exception VolleyError on volley error
     */
    public void sendGETRequest(int requestMethod, String url, JSONObject jsonObject, final HashMap<String, String> parameters, final VolleyResponseListener callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (requestMethod, url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            callback.onSuccess(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
    //                        TODO Handle volley error
                            Log.d("MyVolleySingleton", "Error : " + error.toString());
                        }
                })
                {

                    @Override
                    public byte[] getBody() {
                        return new JSONObject(parameters).toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
//                      TODO Add headers
//                        HashMap<String, String> headers = new HashMap<String, String>();
//                        headers.put("TOKEN", "99KI9Gj68CgCf70deM22Ka64chef2C40Gm2lFJ2J0G9JkDaaDAcbFfd19MfacGf3FFm8CM1hG0eDiIk8");
                        return super.getHeaders();
                    }
                };
        this.addToRequestQueue(jsonObjectRequest);
    }

}
