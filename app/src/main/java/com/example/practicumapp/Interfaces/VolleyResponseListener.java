package com.example.practicumapp.Interfaces;

import org.json.JSONObject;

/*
 * Generic Volley callback interface
 * This interface allows callback after any volley request is successful
 */

public interface VolleyResponseListener {

    void onSuccess(JSONObject response); // Called after a successful response is received

    //    TODO Handle onError as well (if needed)

}
