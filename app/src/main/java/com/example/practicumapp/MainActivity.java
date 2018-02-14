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

import com.example.practicumapp.Interfaces.VolleyTaskResponseListener;
import com.example.practicumapp.Interfaces.VolleyUserResponseListener;
import com.example.practicumapp.Interfaces.VolleyWorkflowResponseListener;
import com.example.practicumapp.models.Task;
import com.example.practicumapp.models.User;
import com.example.practicumapp.models.Workflow;


public class MainActivity extends AppCompatActivity {

    private Button taskListButton;
    private Button loginButton;

    private static final String TAG = MainActivity.class.getName(); // Constant for logging data
    private String authToken;
    private String accessToken;

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

        /*final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

            }
        }, 10000);*/
        try {
            validateUser();
        }catch (Exception e){
            Log.e("TOKENS", "No tokens passed");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        // TODO: remove temporary test buttons when not needed


        taskListButton = findViewById(R.id.task_list_button);
        loginButton = findViewById(R.id.login_button);


        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        taskListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                intent.putExtra("authToken", authToken);
                intent.putExtra("accessToken", accessToken);
                startActivity(intent);
            }
        });



        // TODO Remove volleyParser code below (For testing purposes only)
        VolleyParser volleyParser = new VolleyParser(this.getApplicationContext());
        volleyParser.getUser("72AD9DBC60AE485782D43A1AE09279A4", new VolleyUserResponseListener() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "VolleyParser User First Name : " + user.getFirstName());
                Log.d(TAG, "VolleyParser User Completed Tasks : " + user.getTasks().toString());
                Log.d(TAG, "VolleyParser Employees assigned to User : " + user.getEmployees().toString());
            }
        });
        volleyParser.getWorkflow("ECCD3A6ED4C54D2DA28C9CDD28F6417E", new VolleyWorkflowResponseListener() {
            @Override
            public void onSuccess(Workflow workflow) {
                for (int i = 0; i < workflow.getTasks().size(); i++) {
                    Log.d(TAG, "Workflow Task Name : " + workflow.getTasks().get(i).getName());
                }
            }
        });
        volleyParser.getTask("ECCD3A6ED4C54D2DA28C9CDD28F6417E", new VolleyTaskResponseListener() {
            @Override
            public void onSuccess(Task task) {
                Log.d(TAG, "VolleyParser Task Name : " + task.getName());
            }
        });

    }

    private void validateUser(){
        // Gets the authentication tokens from previous activity
        Bundle bundle = getIntent().getExtras();
        authToken = bundle.getString("authToken");
        accessToken = bundle.getString("accessToken");
        if (authToken.isEmpty() || accessToken.isEmpty()){
            Toast.makeText(getApplicationContext(), "User is not validated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "User is validated with tokens \nAuth: " +
                    authToken + "\nAccess: " + accessToken, Toast.LENGTH_LONG).show();
        }
    }

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
                //Toast.makeText(getApplicationContext(), "Logout toast. Cheers!", Toast.LENGTH_SHORT).show();
                // clean up browser cookies
                CookieSyncManager.createInstance(MainActivity.this);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookie();
                CookieSyncManager.getInstance().sync();
                Log.d("Main Activity Logout", "clearing cookies and logging out");
                //return true;

            // TODO Remove following action items from actionbar here and in res/main_menu.xml

            case R.id.action_login:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
