package com.example.practicumapp.Interfaces;

import com.example.practicumapp.models.Task;

/*
 * Task Volley Response callback interface
 * This interface enable callbacks to any activity after task response is received using volley
 */

public interface VolleyTaskResponseListener {

    void onSuccess(Task task); // Called after user json response is received successfully

    //    TODO Handle onError as well (if needed)

}
