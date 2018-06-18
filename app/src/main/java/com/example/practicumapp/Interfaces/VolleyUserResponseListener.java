package com.example.practicumapp.Interfaces;

import com.example.practicumapp.models.User;

/**
 * @author Suraj Upreti
 * User Volley Response callback interface
 * This interface enable callbacks to any activity after user response is received using volley
 */

public interface VolleyUserResponseListener {

    void onSuccess(User user); // Called after user json response is received successfully

}
