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
import android.widget.Toast;

import com.example.practicumapp.helpers.Constants;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationException;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.PromptBehavior;

/**
 *
 * basic login activity using ADAL-- Joseph Sayler
 *
 * basic code to access AAD using ADAL
 *
 * TODO: implement methods from a login library to authenticate with API (done)
 * TODO: remove hardcoded username/password
 * TODO: implement better error handling / alerting user if username/password incorrect
 * TODO: re-write these comments to conform with our JavaDoc standards
 *
 **/
public class LoginActivity extends AppCompatActivity {

    private EditText unameField, pswdField;
    private Button submitLogin;
    private String userID, userPass;

    private AuthenticationContext mContext;
    private String TAG = "LoginActivity";
    private AuthenticationCallback<AuthenticationResult> callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Login();

        // ADAL AAD login code TODO: use loginactivity UI to login, not MS's website in a webview (??)
//------------------------------------------------------------------------------------------------\\
        // creates new AuthenticationContext object
        mContext = new AuthenticationContext(LoginActivity.this, Constants.AUTHORITY_URL,
                true);
    }

    // listens for button press and then calls adalLogin
    public void loadADAL(View v) {
        Log.d(TAG,"user login");
        Toast.makeText(LoginActivity.this, "Logging in", Toast.LENGTH_SHORT).show();
        adalLogin(mContext);
    }

    // checks the cache for a token then outputs it as string to debug log
    public void checkForToken(View v) {
        // debug code used to display cached token as a string
        // will display a 'default' token if user is not logged in
        Log.d(TAG, mContext.getCache().toString());
        Toast.makeText(this, mContext.getCache().toString(), Toast.LENGTH_SHORT).show();
    }

    public void removeAllTokens(View v) {
        // clear auth context tokens
        mContext.getCache().removeAll();
        // clean up browser cookies
        CookieSyncManager.createInstance(LoginActivity.this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();

        Log.d(TAG, "user logout");
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
    }

    // handles the end of AuthenticationActivity after user enters creds and gets an auth code
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mContext != null) {
            mContext.onActivityResult(requestCode, resultCode, data);
        }
        // shows the request/result codes in logcat
        Log.d(TAG, "request code: " + requestCode + " | resultCode: " + resultCode);
        //Toast.makeText(LoginActivity.this, "request code: " + requestCode + " | resultCode: " + resultCode, Toast.LENGTH_SHORT).show();
    }

    // used to create a callback and obtain a token from aad
    protected void adalLogin(AuthenticationContext context) {
        Log.d(TAG,"trying to obtain token from AAD" + context);
        // callback used when asking for a token
        callback = new AuthenticationCallback<AuthenticationResult>() {
            @Override
            public void onError(Exception exc) {
                if (exc instanceof AuthenticationException) {
                    //textViewStatus.setText("Cancelled");
                    Log.d(TAG, "Cancelled");
                    Toast.makeText(LoginActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                } else {
                    //textViewStatus.setText("Authentication error:" + exc.getMessage());
                    Log.d(TAG, "Authentication error:" + exc.getMessage());
                    Toast.makeText(LoginActivity.this, "Authentication error:" + exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onSuccess(AuthenticationResult result) {
                if (result == null || result.getAccessToken() == null || result.getAccessToken().isEmpty()) {
                    //textViewStatus.setText("Token is empty");
                    Log.d(TAG, "Token is empty");
                    Toast.makeText(LoginActivity.this, "Token is empty", Toast.LENGTH_SHORT).show();
                } else {
                    // request is successful
                    Log.d(TAG, "Status:" + result.getStatus() + " Expires:" + result.getExpiresOn().toString());
                    Toast.makeText(LoginActivity.this, "Status:" + result.getStatus() + " Expires:" + result.getExpiresOn().toString(), Toast.LENGTH_SHORT).show();
                    //textViewStatus.setText(PASSED);
                }
            }
        };
        // asks for a token by using the callback
        mContext.acquireToken(LoginActivity.this, Constants.RESOURCE_ID, Constants.CLIENT_ID,
                Constants.REDIRECT_URL, Constants.USER_HINT, PromptBehavior.Auto, "", callback);
    }

//------------------------------------------------------------------------------------------------\\
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
                                    + "\nUsername: " + user.getManager() + "\nType: " + user.getType() + "\nID: " + user.getId()
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