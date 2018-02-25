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


public class MainActivity extends AppCompatActivity {

    private Button taskListButton, newHireButton;

    private static final String TAG = MainActivity.class.getName(); // Constant for logging data

    // Used for retrieving/sending auth tokens
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
        // TODO: remove temporary test buttons when not needed
        taskListButton = findViewById(R.id.task_list_button);
        newHireButton = findViewById(R.id.new_hire_button);
        taskListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                intent.putExtra("authToken", authToken);
                intent.putExtra("accessToken", accessToken);
                startActivity(intent);
            }
        });
        newHireButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewHireListActivity.class));
            }
        });
        // TODO Remove volleyParser code below (For testing purposes only)
        /*VolleyParser volleyParser = new VolleyParser(this.getApplicationContext());
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
        User user = new User("Suraj", "Upreti", "upretisuraj11@gmail.com", "206-000-0000", "manager", "2018/2/12", "72AD9DBC60AE485782D43A1AE09279A4");
        volleyParser.addNewUser(user, new VolleyUserResponseListener() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "VolleyParser Created User id : " + user.getId());
            }
        });
        validateUser();*/
    }

    /*
    * Attempts to validate the user by getting the intent's extras, where auth tokens are stored.
    * If they exist, the user is validated and able to use the current activity.
    * If they do not exist, the user is redirected to the LoginActivity.
    */
    /*private void validateUser(){
        try {
            Bundle bundle = getIntent().getExtras();
            authToken = bundle.getString("authToken");
            accessToken = bundle.getString("accessToken");
            if (authToken.isEmpty() || accessToken.isEmpty()){
                //Toast.makeText(getApplicationContext(), "User is not validated", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "User is not validated");
            } else {
                //Toast.makeText(getApplicationContext(), "User is validated with tokens for MainActivity", Toast.LENGTH_LONG).show();
                Log.d(TAG, "User is validated with tokens for MainActivity");
            }
        }catch (Exception e){
            Log.e("TOKENS", "No tokens passed");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }*/

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
                Log.d(TAG,"logout pressed");
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO Add onResume, onPause, onStop, onDestroy, etc. to determine what to do when user leaves the app
}
