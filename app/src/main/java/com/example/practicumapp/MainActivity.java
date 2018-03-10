package com.example.practicumapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.practicumapp.helpers.Constants;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationException;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.PromptBehavior;

/**
 * Basic login activity using ADAL. Displays a sign in page to enter credentials and gain access to system resources.
 * This version runs from MainActivity instead of its own LoginActivity.
 * @author Joseph Sayler
 * @version 1.2
 **/
/*
TODO work on preventing user from returning to login screen using back button, and prevent back button from exiting app if pressed too many times
 */

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getName();
    private AuthenticationContext mContext;
    private AuthenticationCallback<AuthenticationResult> callback;
    private Button login_button;
    private final static String EMPLOYEE = "employee@tkarp87live.onmicrosoft.com";
    private final static String MANAGER = "manager@tkarp87live.onmicrosoft.com";
    /**
     * Allows login data to be accessed by other activities by using mResult.getAccessToken or any
     * other method associated with AuthenticationResult.
     */
    protected static AuthenticationResult mResult;

    /**
     * AuthContext and AuthCallback are created upon activity creation.
     *
     * AuthenticationContext is required to set up the 'context' of the authentication - it
     * provides URL and other necessary info to start the login process.
     * AuthenticationCallback is required to get a token from AAD.
     * AuthenticationContext uses callback to request the token. The act of requesting the token
     * opens a WebView that loads the AAD login screen. The results of authentication (if successful)
     * are stored in AuthenticationResult and provide information such as token ID, username, etc.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        mContext = new AuthenticationContext(MainActivity.this, Constants.AUTHORITY_URL, true);
        callback = new AuthenticationCallback<AuthenticationResult>() {
            @Override
            public void onError(Exception exc) {
                if (exc instanceof AuthenticationException) {
                    Log.d(TAG, "Cancelled");
                } else {
                    Log.d(TAG, "Authentication error:" + exc.getMessage());
                }
            }
            @Override
            public void onSuccess(AuthenticationResult result) {
                mResult = result;
                if (result == null || result.getAccessToken() == null
                        || result.getAccessToken().isEmpty()) {
                    Log.d(TAG,"Token is empty");
                } else {
                    // request is successful
                    Log.d(TAG, "Status:" + result.getStatus() + " Expired:"
                            + result.getExpiresOn().toString());
                    tokenLogData();
                    saveLoginData();
                    loginRouter();
                }
            }
        };
        login_button = findViewById(R.id.loginButton);
        // initiates login on button press
        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG,"Requesting login token");
                mContext.acquireToken(MainActivity.this, Constants.RESOURCE_ID, Constants.CLIENT_ID,
                        Constants.REDIRECT_URL, Constants.USER_HINT, PromptBehavior.Auto, "", callback);

            }
        });
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
    }

    /**
     * Removes user token and cleans up cache. Acts as a way to log out of AD. Takes user back to
     * main activity screen once called.
     */
    protected void adalLogout() {
        clearLogin();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
        Log.d(TAG,"User Logout");
        finish();
    }

    /**
     * Creates actionbar menu and inflates menu hierarchy from menu/main_menu.xml.
     * @param menu The options menu which items are placed
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Assigns functions to menu items
     * @param item the inflated menu where the functions are associated
     * @return returns true unless the default case is reached, then it returns super.onOptionsItemSelected(item)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_debug:
                startActivity(new Intent(MainActivity.this, DebugActivity.class));
                Log.d(TAG,"debug menu activity selected");
                break;
            case R.id.action_logout:
                adalLogout();
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * TEST CODE - based on one of 2 emails used, routes user to one of 2 activities
     */
    private void loginRouter() {
        if(mResult.getUserInfo().getDisplayableId().equals(EMPLOYEE)) {
            Log.d(TAG, "employee login - move to TaskListActivity");
            startActivity(new Intent(MainActivity.this, TaskListActivity.class));
        } else if(mResult.getUserInfo().getDisplayableId().equals(MANAGER)) {
            Log.d(TAG, "manager login - move to NewHireListActivity");
            startActivity(new Intent(MainActivity.this, NewHireListActivity.class));
        } else {
            Log.d(TAG, "non manager or non employee login - something wrong, not changing activities");
            Toast.makeText(this,"Something went wrong with your request. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * DISPLAYS DEBUG DATA IN LOGCAT
     */
    private void tokenLogData() {
        Log.d(TAG, "Status: " + mResult.getStatus().toString() +
                "\nLogin expires: " + mResult.getExpiresOn().toString() +
                "\nEmail: " + mResult.getUserInfo().getDisplayableId() +
                "\nFirst name: " + mResult.getUserInfo().getGivenName() +
                "\nLast name: " + mResult.getUserInfo().getFamilyName() +
                "\nUserID: " + mResult.getUserInfo().getUserId() +
                "\nTenant ID: " + mResult.getTenantId() +
                "\nToken ID:            " + mResult.getIdToken() +
                "\nAccess Token:        " + mResult.getAccessToken() +
                "\nAuth Header:  " + mResult.createAuthorizationHeader());
    }

    private void saveLoginData() {
        Log.d(TAG, "Access Token : " + mResult.getAccessToken());

        SharedPreferences preferences = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        preferences.edit().putString("AccessToken", mResult.getAccessToken()).apply();
    }

    /**
     * Clears stored cookies of login data
     */
    public void clearLogin(){
        CookieSyncManager.createInstance(getApplicationContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        CookieSyncManager.getInstance().sync();
        mContext.getCache().removeAll();
        mResult = null;
    }

    /**
     * When 'Back' button is pressed, this method with clear the login info and close the app.
     */
    @Override
    public void onBackPressed() {
        if (this.getClass().getSimpleName().equals(MainActivity.class.getSimpleName())){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Logout")
                    .setMessage("You are about to exit the app.");

            builder.setPositiveButton(
                    "Logout",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            clearLogin();
                            finish();
                        }
                    });

            builder.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }else {
            // finishes the activity and navigates to the parent
            finish();
            NavUtils.navigateUpFromSameTask(this);
        }
    }

    /**
     * When the app is destroyed, the user's login data is cleared.
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
        clearLogin();
        finish();
    }
}
