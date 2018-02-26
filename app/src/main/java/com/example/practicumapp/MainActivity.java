package com.example.practicumapp;

import android.content.Intent;
import android.os.Bundle;
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


public class MainActivity extends AppCompatActivity {

    private String TAG = "Main Activity";
    protected AuthenticationContext mContext;
    protected AuthenticationResult mResult;
    private AuthenticationCallback<AuthenticationResult> callback;
    private Button login_button;
    private final static String EMPLOYEE = "employee@tkarp87live.onmicrosoft.com";
    private final static String MANAGER = "manager@tkarp87live.onmicrosoft.com";

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
                    loginRouter();
                }
            }
        };
        login_button = findViewById(R.id.loginButton);
        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG,"Requesting login token");
                mContext.acquireToken(MainActivity.this, Constants.RESOURCE_ID, Constants.CLIENT_ID,
                        Constants.REDIRECT_URL, Constants.USER_HINT, PromptBehavior.Auto, "", callback);

            }
        });
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mContext != null) {
            mContext.onActivityResult(requestCode, resultCode, data);
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_debug:
                startActivity(new Intent(MainActivity.this, DebugActivity.class));
                Log.d(TAG,"debug menu activity selected");
                break;
            case R.id.action_logout:
                CookieSyncManager.createInstance(getApplicationContext());
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeSessionCookie();
                CookieSyncManager.getInstance().sync();
                mContext.getCache().removeAll();
                mResult = null;
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                Log.d(TAG,"User Logout");
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

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
}
