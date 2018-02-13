package com.example.practicumapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
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

// TODO: use loginactivity UI to login, not MS's website in a webview (??)

public class LoginActivity extends AppCompatActivity {

    private EditText unameField, pswdField;
    private Button submitLogin;
    private String userID, userPass;

    private AuthenticationContext mContext;
    //private AuthenticationCallback<AuthenticationResult> callback;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Login();
//-----------------------------Begin ADAL AAD login code------------------------------------------\\
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
    }
    /**
     * Initiates the login process when activated
     * @param v Holds the current view being displayed
     */
    public void adalLogin(View v) {
        Log.d(TAG,"user login");
        popUp("Logging in");
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
        popUp("Logged out");
    }
    /**
     * Displays login info on button click
     * @param v Holds the current view being displayed
     */
    public void getLoginRestuls(View v) {
        dataOutput(v, loginResults);
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
                    popUp("Canceled");
                } else {
                    //textViewStatus.setText("Authentication error:" + exc.getMessage());
                    Log.d(TAG, "Authentication error:" + exc.getMessage());
                    popUp("Authentication error:" + exc.getMessage());
                }
            }
            @Override
            public void onSuccess(AuthenticationResult result) {
                if (result == null || result.getAccessToken() == null || result.getAccessToken().isEmpty()) {
                    Log.d(TAG, "Token is empty");
                    popUp("Token is empty");
                } else {
                    // request is successful
                    loginResults = result;
                    //popUp("Status: " + loginStatus + " | Expires: " + expiryDate);
                }
            }
        };
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
        }
    }
//---------------------------------End ADAL AAD login code----------------------------------------\\
/**
 void Login(){
 unameField = (EditText)findViewById(R.id.username);
 pswdField = (EditText)findViewById(R.id.password);
 submitLogin = (Button)findViewById(R.id.loginButton);

 submitLogin.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
userID = unameField.getText().toString().trim();
userPass = pswdField.getText().toString().trim();

UserLoginTask login = new UserLoginTask(userID, userPass);
login.execute();
/*
if(userID.equals("admin") && userPass.equals("admin")){
Toast.makeText(LoginActivity.this, "Username and Password is correct", Toast.LENGTH_SHORT).show();
startActivity(new Intent(LoginActivity.this, TaskListActivity.class));
}else{
Toast.makeText(LoginActivity.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
}
 */
/**            }
 });
 }

 /**
 * Represents an asynchronous login task used to authenticate
 * the user.
 */
/**
 public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

 private final String mUserID;
 private final String mPassword;

 /**
 * Constructor for UserLoginTask that accepts two parameters (user's ID and password), uses
 * these parameters to send a call to the API to get the specified user. If the user's password
 * matches, the user is redirected to the TaskListActivity. If the password doesn't match,
 * the LoginActivity is reloaded and the user is notified that the user/pass is incorrect.
 *
 * @param id user's ID
 * @param password user's password
 */
/**        UserLoginTask(String id, String password) {
 mUserID = id;
 mPassword = password;
 }

 @Override
 protected Boolean doInBackground(Void... params) {
 // TODO: Request username and compare password with specified username

 try {
 VolleyParser getJson = new VolleyParser(getApplicationContext());
 getJson.getUser(mUserID, new VolleyUserResponseListener() {
 @Override
 public void onSuccess(User user) {
 if (mUserID.equals(user.getId().replace("-",""))) {
 String temp = "Email: " + user.getEmail() + "\nFirst: " + user.getFirstName() + "\nLast: " + user.getLastName()
 + "\nStartDate: " + user.getStartDate() + "\nType: " + user.getType() + "\nID: " + user.getId()
 + "\nPhone: " + user.getPhone();
 Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
 startActivity(new Intent(LoginActivity.this, TaskListActivity.class));
 }
 else {
 startActivity(new Intent(LoginActivity.this, LoginActivity.class));
 Toast.makeText(getApplicationContext(), "Incorrect User/Pass", Toast.LENGTH_LONG).show();
 }
 }
 });

 Thread.sleep(2000);
 } catch (InterruptedException e) {
 return false;
 }

 return true;
 }

 @Override
 protected void onPostExecute(final Boolean success) {
 //mAuthTask = null;
 //showProgress(false);

 if (success) {
 finish();
 } else {
 // mPasswordView.setError(getString(R.string.error_incorrect_password));
 // mPasswordView.requestFocus();
 }
 }

 @Override
 protected void onCancelled() {
 //mAuthTask = null;
 //showProgress(false);
 }
 }
 **/
}