package com.example.practicumapp.Interfaces;

import com.example.practicumapp.models.Workflow;

/**
 * Workflow Volley Response callback interface
 * This interface enable callbacks to any activity after workflow response is received using volley
 */

public interface VolleyWorkflowResponseListener {

    void onSuccess(Workflow workflow); // Called after user json response is received successfully

    //    TODO Handle onError as well (if needed)

}
