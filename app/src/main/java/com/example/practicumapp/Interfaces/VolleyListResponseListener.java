package com.example.practicumapp.Interfaces;

import java.util.ArrayList;

/**
 * @author Suraj Upreti
 * Workflows list Volley Response callback interface
 * This interface enable callbacks to any activity after workflows list response is received using volley
 */

public interface VolleyListResponseListener {

    void onSuccess(ArrayList list); // Called after list list json response is received successfully

    //    TODO Handle onError as well (if needed)

}
