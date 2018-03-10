package com.example.practicumapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practicumapp.helpers.Constants;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationException;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.PromptBehavior;

/**
 * Basic login activity using ADAL. Displays a sign in page to enter credentials and gain access to system resources.
 * @author Joseph Sayler
 * @version 1.1
 **/

public class LoginActivity extends AppCompatActivity {

    private String authToken, accessToken;
    private AuthenticationContext mContext;
    private AuthenticationResult loginResults;
    private String TAG = "LoginActivity";
    private ScrollView authOutput;
    private TextView testOutput_tenantID;
    private TextView testOutput_dispID;
    private TextView testOutput_status;
    private TextView testOutput_lname;
    private TextView testOutput_tokenID;
    private TextView testOutput_fname;
    private TextView testOutput_expiryDate;
    private TextView testOutput_authHeader;
    private TextView testOutput_userID;
    private TextView testOutput_accToken;
    private TextView noDataOutput;
    //private AuthenticationCallback<AuthenticationResult> callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // creates new AuthenticationContext object
        mContext = new AuthenticationContext(LoginActivity.this, Constants.AUTHORITY_URL,
                true);
        authOutput = (ScrollView) findViewById(R.id.aadOutput);
        testOutput_tenantID = (TextView) findViewById(R.id.tenantID);
        testOutput_dispID = (TextView) findViewById(R.id.dispID);
        testOutput_status = (TextView) findViewById(R.id.status);
        testOutput_lname = (TextView) findViewById(R.id.lname);
        testOutput_tokenID = (TextView) findViewById(R.id.tokenID);
        testOutput_fname = (TextView) findViewById(R.id.fname);
        testOutput_expiryDate = (TextView) findViewById(R.id.expiryDate);
        testOutput_authHeader = (TextView) findViewById(R.id.authHeader);
        testOutput_userID = (TextView) findViewById(R.id.userID);
        testOutput_accToken = (TextView) findViewById(R.id.accToken);
        noDataOutput = (TextView) findViewById(R.id.noData);
        // Passes Tester tokens for validation without the need to login.
        // TODO Delete this when testing is complete.
        Button btnSkipLogin = findViewById(R.id.btnSkipLogin);
        btnSkipLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("authToken", "Tester Auth");
                intent.putExtra("accessToken", "Test Access");
                startActivity(intent);
            }
        });
    }
    /**
     * Initiates the login process when activated
     * @param v Holds the current view being displayed
     */
    public void adalLogin(View v) {
        Log.d(TAG,"user login");
        //popUp("Logging in");
        // asks for a token by generating a callback that returns the necessary information
        mContext.acquireToken(LoginActivity.this, Constants.RESOURCE_ID, Constants.CLIENT_ID,
                Constants.REDIRECT_URL, Constants.USER_HINT, PromptBehavior.Auto, "", getAdalAuth());
        Log.d(TAG,"accessing login data");
    }
    /**
     * Removes user token and cleans up cache. Acts as a way to log out of AD.
     * @param v Holds the current view being displayed
     */
    public void adalLogout(View v) {
        // clear auth context tokens
        mContext.getCache().removeAll();
        // clean up browser cookies
        CookieSyncManager.createInstance(LoginActivity.this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
        // clears out values set after authentication
        loginResults = null;
        dataOutput(v, loginResults);
        Log.d(TAG, "logout - removing tokens and cookies");
        //popUp("Logged out");
        getIntent().removeExtra("authToken");
        getIntent().removeExtra("accessToken");
    }

    /**
     * Handles the end of AuthenticationActivity after user enters credentials and receives authorization code.
     * @param requestCode indicates the type of request
     * @param resultCode indicates result of the login attempt
     * @param data data returned after request has been made
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mContext != null) {
            mContext.onActivityResult(requestCode, resultCode, data);
        }
        Log.d(TAG, "request code: " + requestCode + " | result code: " + resultCode + " | data: "+ data);
        //popUp("request code: " + requestCode + " | resultCode: " + resultCode);
    }
    /**
     * Creates callback required to get a token from AAD. Then uses AuthenticationContext to request
     * the token. The act of requesting the token opens a WebView that loads the AAD login screen.
     * @return a new AuthenticationCallback containing information about state of the login request
     */
    protected AuthenticationCallback getAdalAuth() {
        Log.d(TAG,"Obtaining token from AAD");
        // returns auth info depending on success of request
        return new AuthenticationCallback<AuthenticationResult>() {
            @Override
            public void onError(Exception exc) {
                if (exc instanceof AuthenticationException) {
                    Log.d(TAG, "Cancelled");
                    //popUp("Canceled");
                } else {
                    //textViewStatus.setText("Authentication error:" + exc.getMessage());
                    Log.d(TAG, "Authentication error:" + exc.getMessage());
                    //popUp("Authentication error:" + exc.getMessage());
                }
            }
            @Override
            public void onSuccess(AuthenticationResult result) {
                if (result == null || result.getAccessToken() == null || result.getAccessToken().isEmpty()) {
                    Log.d(TAG, "Token is empty");
                    //popUp("Token is empty");
                } else {
                    // request is successful
                    loginResults = result;
                    //popUp("Status: " + loginStatus + " | Expires: " + expiryDate);
                }
            }
        };
    }

    /**
     * Displays login info on button click
     * @param v Holds the current view being displayed
     */
    public void getLoginRestuls(View v) {
        dataOutput(v, loginResults);
        try {
            if (loginResults == null){
                //Toast.makeText(getApplicationContext(), "User is not validated", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "User is not validated");
            } else {
                //Toast.makeText(getApplicationContext(), "User is validated with tokens for App", Toast.LENGTH_LONG).show();
                Log.d(TAG, "User is validated with tokens for App");
                // Redirect user to MainActivity and add authentication tokens to the intent
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("authToken", authToken);
                intent.putExtra("accessToken", accessToken);
                startActivity(intent);
            }
        }catch (Exception e){
            Log.e("TOKENS", "No tokens passed");
        }
    }

    /**
     * Helper method to display Toast pop ups for debugging purposes
     * @param inputText String that is to be displayed inside the Toast
     */
    private void popUp(String inputText){
        Toast.makeText(this, inputText, Toast.LENGTH_SHORT).show();
    }
    /**
     * Gathers data from login token and displays the information on screen
     * @param v Holds the current view being displayed
     * @param loginData AuthenticationResult object obtained after login attempt
     */
    // TODO: this needs to be cleaned up and compacted to be more efficient and easier to look at
    public void dataOutput(View v, AuthenticationResult loginData) {
        if (loginData == null || loginData.getAccessToken() == null || loginData.getAccessToken().isEmpty()) {
            authOutput.setVisibility(View.GONE);
            noDataOutput.setVisibility(View.VISIBLE);
            Log.d(TAG, "No login data to display");
            noDataOutput.setText("No login data to display");
        } else {
            authOutput.setVisibility(View.VISIBLE);
            noDataOutput.setVisibility(View.GONE);
            Log.d(TAG, "Status: " + loginData.getStatus().toString() +
                    "\nLogin expires: " + loginData.getExpiresOn().toString() +
                    "\nEmail: " + loginData.getUserInfo().getDisplayableId() +
                    "\nFirst name: " + loginData.getUserInfo().getGivenName() +
                    "\nLast name: " + loginData.getUserInfo().getFamilyName() +
                    "\nUserID: " + loginData.getUserInfo().getUserId() +
                    "\nTenant ID: " + loginData.getTenantId() +
                    "\nToken ID:            " + loginData.getIdToken() +
                    "\nAccess Token:        " + loginData.getAccessToken() +
                    "\nAuth Header:  " + loginData.createAuthorizationHeader());// +
            //"\nID Provider: " + loginResults.getUserInfo().getIdentityProvider() +
            //"\nPassword change URL: " + loginResults.getUserInfo().getPasswordChangeUrl() +
            //"\nPassword expires on: " + loginResults.getUserInfo().getPasswordExpiresOn());
            testOutput_status.setText("Status: " + loginData.getStatus().toString());
            testOutput_expiryDate.setText("Login expires: " + loginData.getExpiresOn().toString());
            testOutput_dispID.setText("Email: " + loginData.getUserInfo().getDisplayableId());
            testOutput_fname.setText("First name: " + loginData.getUserInfo().getGivenName());
            testOutput_lname.setText("Last name: " + loginData.getUserInfo().getFamilyName());
            testOutput_userID.setText("UserID: " + loginData.getUserInfo().getUserId());
            testOutput_tenantID.setText("Tenant ID: " + loginData.getTenantId());
            testOutput_tokenID.setText("Token ID\n" + loginData.getIdToken());
            testOutput_accToken.setText("Access Token\n" + loginData.getAccessToken());
            testOutput_authHeader.setText("Auth Header\n" + loginData.createAuthorizationHeader());
            // Assigns auth tokens to variables to be passed to MainActivity via intent
            authToken = loginData.createAuthorizationHeader();
            accessToken = loginData.getAccessToken();
        }
    }
//---------------------------------End ADAL AAD login code----------------------------------------\\
}
