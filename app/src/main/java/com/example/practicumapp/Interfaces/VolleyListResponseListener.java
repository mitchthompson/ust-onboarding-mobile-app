package com.example.practicumapp.Interfaces;

import java.util.HashMap;

/**
 * Workflows list Volley Response callback interface
 * This interface enable callbacks to any activity after workflows list response is received using volley
 */

public interface VolleyListResponseListener {

    void onSuccess(HashMap<String, String> workflows); // Called after workflows list json response is received successfully

    //    TODO Handle onError as well (if needed)

}
