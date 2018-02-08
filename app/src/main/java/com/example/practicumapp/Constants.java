package com.example.practicumapp;

import com.microsoft.aad.adal.AuthenticationResult;

/** Created by jsayler on 2/7/18.

Copyright (c) Microsoft
All Rights Reserved
Apache 2.0 License

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

See the Apache Version 2.0 License for specific language governing permissions and limitations under the License.
 **/

public class Constants {

    public static final String SDK_VERSION = "1.0";
    /**
     * UTF-8 encoding
     */
    public static final String UTF8_ENCODING = "UTF-8";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_AUTHORIZATION_VALUE_PREFIX = "Bearer ";

    // -------------------------------AAD PARAMETERS----------------------------------

    //public static String AUTHORITY_URL = "https://login.microsoftonline.com/common";
    //public static String CLIENT_ID = "e6d65cc4-f9e8-444a-9ba6-c3c82ce8086b";
    //public static String RESOURCE_ID = "http://kidventus.com/TodoListService";
    //public static String REDIRECT_URL = "mstodo://com.microsoft.windowsazure.activedirectory.samples.microsofttasks";
    //public static String USER_HINT = "";

    public static String CORRELATION_ID = "";
    public static String EXTRA_QP = "";
    public static boolean FULL_SCREEN = true;
    public static AuthenticationResult CURRENT_RESULT = null;
    // Endpoint we are targeting for the deployed WebAPI service

    // from Jason's personal AAD tenant
    public static String SERVICE_URL = "http://10.0.1.44:8080/tasks";
    public static String AUTHORITY_URL = "https://login.windows.net/jasondrewpanzergmail.onmicrosoft.com";
    public static String CLIENT_ID = "650a6609-5463-4bc4-b7c6-19df7990a8bc";
    public static String RESOURCE_ID = "https://jasondrewpanzergmail.onmicrosoft.com/TaskTrackerDemo";
    public static String REDIRECT_URL = "http://TaskTrackerDemo";
    public static String USER_HINT = "faruk@omercantest.onmicrosoft.com";

    // ------------------------------------------------------------------------------------------

    static final String TABLE_WORKITEM = "WorkItem";

    //public static final String SHARED_PREFERENCE_NAME = "com.example.com.test.settings";
    //public static final String KEY_NAME_ASK_BROKER_INSTALL = "test.settings.ask.broker";
    //public static final String KEY_NAME_CHECK_BROKER = "test.settings.check.broker";

    // updated the above lines with what I **think** they should be (replaced com.example.test
    // with com.example.practicumapp or practicumapp
    public static final String SHARED_PREFERENCE_NAME = "com.example.practicumapp.settings";
    public static final String KEY_NAME_ASK_BROKER_INSTALL = "practicumapp.settings.ask.broker";
    public static final String KEY_NAME_CHECK_BROKER = "practicumapp.settings.check.broker";

}