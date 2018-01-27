package com.example.practicumapp.Interfaces;

import com.example.practicumapp.models.User;

/*
 * User Volley Response callback interface
 * This interface enable callbacks to any activity after user response is received using volley
 */

public interface UserResponseCallback {

    void onSuccess(User user); // Called after user json response is received successfully

//    TODO Handle onError as well (if needed)
}
