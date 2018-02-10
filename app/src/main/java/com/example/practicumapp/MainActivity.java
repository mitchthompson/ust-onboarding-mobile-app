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
import android.widget.Button;
import android.widget.Toast;

import com.example.practicumapp.Interfaces.VolleyUserResponseListener;
import com.example.practicumapp.Interfaces.VolleyWorkflowResponseListener;
import com.example.practicumapp.helpers.Constants;
import com.example.practicumapp.models.User;
import com.example.practicumapp.models.Workflow;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationException;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.PromptBehavior;

public class MainActivity extends AppCompatActivity {

    private Button taskListButton;
    //private Button loginButton;
    private Button newHireListButton;
    private Button addNewHireButton;
    private static final String TAG = MainActivity.class.getName(); // Constant for logging data
    private AuthenticationCallback<AuthenticationResult> callback;
    private AuthenticationContext mContext;
    private static final String TAG1 = "ADAL MAIN ACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         /*Enables tool bar & sets title displayed
        Can customize menu items in res/menu/main_menu.xml
        Can customize toolbar in res/layout/toolbar_layout.xml*/
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        myToolbar.setTitle("Main Activity");
        setSupportActionBar(myToolbar);

        // TODO: remove temporary test buttons when not needed

        taskListButton = findViewById(R.id.task_list_button);
        //loginButton = findViewById(R.id.login_button);
        newHireListButton = findViewById(R.id.newhire_list_button);
        addNewHireButton = findViewById(R.id.newhire_add_button);

        /**loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });**/

        taskListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TaskListActivity.class));
            }
        });

        newHireListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewHireListActivity.class));
            }
        });

        addNewHireButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNewHireActivity.class));
            }
        });

        // TODO Remove volleyParser code below (For testing purposes only)
        VolleyParser volleyParser = new VolleyParser(this.getApplicationContext());
        volleyParser.getUser("72AD9DBC60AE485782D43A1AE09279A4", new VolleyUserResponseListener() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "VolleyParser User First Name : " + user.getFirstName());
            }
        });
        volleyParser.getWorkflow("01", new VolleyWorkflowResponseListener() {
            @Override
            public void onSuccess(Workflow workflow) {
                Log.d(TAG, "VolleyParser Workflow Task Name : " + workflow.getTasks().get(0).getName());
            }
        });

        // ADAL AAD login code
//------------------------------------------------------------------------------------------------\\
        // creates new AuthenticationContext object
        mContext = new AuthenticationContext(MainActivity.this, Constants.AUTHORITY_URL,
                true);
        // callback used when asking for a token
        callback = new AuthenticationCallback<AuthenticationResult>() {

            @Override
            public void onError(Exception exc) {
                if (exc instanceof AuthenticationException) {
                    //textViewStatus.setText("Cancelled");
                    Log.d(TAG1, "Cancelled");
                } else {
                    //textViewStatus.setText("Authentication error:" + exc.getMessage());
                    Log.d(TAG1, "Authentication error:" + exc.getMessage());
                }
            }

            @Override
            public void onSuccess(AuthenticationResult result) {
                //mResult = result;

                if (result == null || result.getAccessToken() == null || result.getAccessToken().isEmpty()) {
                    //textViewStatus.setText("Token is empty");
                    Log.d(TAG1, "Token is empty");
                } else {
                    // request is successful
                    Log.d(TAG1, "Status:" + result.getStatus() + " Expires:" + result.getExpiresOn().toString());
                    //textViewStatus.setText(PASSED);
                }
            }
        };
        // asks for a token by using the callback
        mContext.acquireToken(MainActivity.this, Constants.RESOURCE_ID, Constants.CLIENT_ID,
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Defines the actions after user selection of the menu items in the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                // User chose the "Log Out" item...
                Toast.makeText(getApplicationContext(), "This does nothing at the moment",
                        Toast.LENGTH_SHORT).show();

                return true;

            // TODO Remove following action items from actionbar here and in res/main_menu.xml

            case R.id.action_login:
                //startActivity(new Intent(MainActivity.this, LoginActivity.class));
                Toast.makeText(getApplicationContext(), "This does nothing at the moment",
                        Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_tasklist:
                startActivity(new Intent(MainActivity.this, TaskListActivity.class));
                return true;

            case R.id.action_newhirelist:
                startActivity(new Intent(MainActivity.this, NewHireListActivity.class));
                return true;

            case R.id.action_addhire:
                startActivity(new Intent(MainActivity.this, AddNewHireActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
