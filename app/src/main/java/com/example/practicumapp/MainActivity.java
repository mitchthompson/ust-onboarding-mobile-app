package com.example.practicumapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.practicumapp.Interfaces.UserResponseCallback;
import com.example.practicumapp.Interfaces.WorkflowCallback;
import com.example.practicumapp.models.User;
import com.example.practicumapp.models.Workflow;


public class MainActivity extends AppCompatActivity {

    private Button taskListButton;
    private Button loginButton;
    private static final String TAG = MainActivity.class.getName(); // Constant for logging data

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

        /**
         * TODO: remove temporary test buttons when not needed
         **/

        taskListButton = findViewById(R.id.task_list_button);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        taskListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TaskListActivity.class));
            }
        });

        // TODO Remove UserParser and WorkflowParser code below (For testing purposes only)
        UserParser userParser = new UserParser(this.getApplicationContext());
        userParser.getUser("72AD9DBC-60AE-4857-82D4-3A1AE09279A4", new UserResponseCallback() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "User First Name : " + user.getFirstName());
            }
        });

        WorkflowParser workflowParser = new WorkflowParser(this.getApplicationContext());
        workflowParser.getWorkflow("01", new WorkflowCallback() {
            @Override
            public void onSuccess(Workflow workflow) {
                Log.d(TAG, "Workflow Task Name : " + workflow.getTasks().get(0).getName());
            }
        });
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
                Toast.makeText(getApplicationContext(), "Logout toast. Cheers!",
                        Toast.LENGTH_SHORT).show();

                return true;

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
                //startActivity(new Intent(MainActivity.this, ActivityName.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
