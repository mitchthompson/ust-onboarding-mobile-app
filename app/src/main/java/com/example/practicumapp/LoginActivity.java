package com.example.practicumapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practicumapp.Interfaces.VolleyUserResponseListener;
import com.example.practicumapp.models.User;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationResult;


/**
 *
 * very basic login activity -- Joseph Sayler
 *
 * at this time, this activity accepts a username and password, compares them to hard coded
 * username/password and if correct redirects user to the tasks list activity.
 *
 * TODO: implement methods from a login library to authenticate with API
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
        Login();
    }
/**
// ADAL AAD login code TODO: use loginactivity UI to login, not MS's website in a webview
//------------------------------------------------------------------------------------------------\\
        // creates new AuthenticationContext object
        mContext = new AuthenticationContext(LoginActivity.this, Constants.AUTHORITY_URL,
                true);
        // callback used when asking for a token
        callback = new AuthenticationCallback<AuthenticationResult>() {

            @Override
            public void onError(Exception exc) {
                if (exc instanceof AuthenticationException) {
                    //textViewStatus.setText("Cancelled");
                    Log.d(TAG, "Cancelled");
                } else {
                    //textViewStatus.setText("Authentication error:" + exc.getMessage());
                    Log.d(TAG, "Authentication error:" + exc.getMessage());
                }
            }

            @Override
            public void onSuccess(AuthenticationResult result) {
                //mResult = result;

                if (result == null || result.getAccessToken() == null || result.getAccessToken().isEmpty()) {
                    //textViewStatus.setText("Token is empty");
                    Log.d(TAG, "Token is empty");
                } else {
                    // request is successful
                    Log.d(TAG, "Status:" + result.getStatus() + " Expires:" + result.getExpiresOn().toString());
                    //textViewStatus.setText(PASSED);
                }
            }
        };
        // asks for a token by using the callback
        mContext.acquireToken(LoginActivity.this, Constants.RESOURCE_ID, Constants.CLIENT_ID,
                Constants.REDIRECT_URL, Constants.USER_HINT, PromptBehavior.Auto, "", callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mContext != null) {
            mContext.onActivityResult(requestCode, resultCode, data);
        }
    }
//------------------------------------------------------------------------------------------------\\
**/
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
            }
        });
    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
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
        UserLoginTask(String id, String password) {
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
}