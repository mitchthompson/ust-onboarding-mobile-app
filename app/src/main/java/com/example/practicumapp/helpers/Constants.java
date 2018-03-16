package com.example.practicumapp.helpers;

import com.microsoft.aad.adal.AuthenticationResult;

/**
 * Group of constant string variables necessary for logging into AAD using ADAL
 * @author  Joseph Sayler
 *
 **/

public class Constants {

    public static final String SDK_VERSION = "1.0";
    /**
     * UTF-8 encoding
     */
    public static final String UTF8_ENCODING = "UTF-8";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_AUTHORIZATION_VALUE_PREFIX = "Bearer ";
    public static String SERVICE_URL = "http://10.0.1.44:8080/tasks";
    // these are not used or changed
    public static String CORRELATION_ID = "";
    public static String EXTRA_QP = "";
    public static boolean FULL_SCREEN = true;
    public static AuthenticationResult CURRENT_RESULT = null;
    // Endpoint we are targeting for the deployed WebAPI service
    // from Jason (using Travis' AAD setup)
    public static String CLIENT_ID = "88c46aac-a41b-4015-9cf4-16f13b093156";
    public static String RESOURCE_ID = "https://tkarp87live.onmicrosoft.com/ExampleAPI";
    public static String REDIRECT_URL =  "https://exampleapiaadintegration.portal.azure-api.net/docs/services/5a79288d3a7fa7123837add2/console/oauth2/authorizationcode/callback";
    public static String AUTHORITY_URL = "https://login.windows.net/tkarp87live.onmicrosoft.com/";
    // user hint auto populates the username with the following:
    public static String USER_HINT = ""; // = "JasonPanzer@tkarp87live.onmicrosoft.com";
    static final String TABLE_WORKITEM = "WorkItem";
    // updated the above lines with what I **think** they should be (replaced com.example.test
    // with com.example.practicumapp or practicumapp
    public static final String SHARED_PREFERENCE_NAME = "com.example.practicumapp.settings";
    public static final String KEY_NAME_ASK_BROKER_INSTALL = "practicumapp.settings.ask.broker";
    public static final String KEY_NAME_CHECK_BROKER = "practicumapp.settings.check.broker";

    public static final String SUBSCRIPTION_KEY = "725634f731424f9faf8efe31aab443ed";

    // example parameters from the test code on microsoft's github site
    //public static String AUTHORITY_URL = "https://login.microsoftonline.com/common";
    //public static String CLIENT_ID = "e6d65cc4-f9e8-444a-9ba6-c3c82ce8086b";
    //public static String RESOURCE_ID = "http://kidventus.com/TodoListService";
    //public static String REDIRECT_URL = "mstodo://com.microsoft.windowsazure.activedirectory.samples.microsofttasks";
    //public static final String SHARED_PREFERENCE_NAME = "com.example.com.test.settings";
    //public static final String KEY_NAME_ASK_BROKER_INSTALL = "test.settings.ask.broker";
    //public static final String KEY_NAME_CHECK_BROKER = "test.settings.check.broker";
}