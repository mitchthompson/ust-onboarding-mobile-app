package com.example.practicumapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.practicumapp.Interfaces.VolleyUserResponseListener;
import com.example.practicumapp.adapters.NewHireListAdapter;
import com.example.practicumapp.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * New Hire List Activity. Displays list of new hires in a recyclerview. (Will use) logged in
 * manager's userID to make a API call with VolleyParser. Uses NewHireListAdapter to build recyclerview.
 *
 * @author Mitch Thompson
 * @since 1/22/2018
 * @see NewHireListAdapter
 * @see VolleyParser
 *
 *
 */

public class NewHireListActivity extends AppCompatActivity {
    private static final String TAG = NewHireListActivity.class.getName(); // Constant for logging data

    private Context context;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private NewHireListAdapter newHireListAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private HashMap<String, String> employees;
    private ArrayList<String> newHireList;
    private ArrayList<String> newHireIDs;

    private Button addNewHireButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_hire_list);
        context = getApplicationContext();

        //Enables tool bar & sets title displayed
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        myToolbar.setTitle("Employees");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //TODO Add functionality for user to search through list using searchview

        //sets click listener for add_new_hire_btn to launch AddNewHireActivity
        addNewHireButton = findViewById(R.id.add_new_hire_btn);
        addNewHireButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(NewHireListActivity.this, AddNewHireActivity.class));
            }
        });

        //TODO: get userID from logged in manager user
        //Uses the Volleyparser to get all employees assigned to a manager using managers userID
        employees = new HashMap<>();
        newHireList = new ArrayList<>();
        newHireIDs = new ArrayList<>();
        VolleyParser volleyParser = new VolleyParser(this.getApplicationContext());
        volleyParser.getUser("72AD9DBC60AE485782D43A1AE09279A4", new VolleyUserResponseListener() {

            //if successful response assign employees & IDs to ArrayList then pass to NewHireListAdapter
            @Override
            public void onSuccess(User user) {
                employees = user.getEmployees();
                for(Map.Entry<String, String> entry : employees.entrySet()){
                    Log.d(TAG, "Key: " + entry.getKey() + "Value: " + entry.getValue());
                    newHireList.add(entry.getValue());
                    newHireIDs.add(entry.getKey());
                }
                relativeLayout = findViewById(R.id.new_hire_relativeLayout);
                recyclerView = findViewById(R.id.new_hire_list_recycler);
                recyclerViewLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(recyclerViewLayoutManager);

                newHireListAdapter = new NewHireListAdapter(context, newHireList, newHireIDs);
                recyclerView.setAdapter(newHireListAdapter);

            }
        });

    }

    /**
     * Creates actionbar menu and inflates menu hierarchy from menu/main_menu.xml.
     * @param menu The options menu which items are placed
     * @return boolean
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // Defines the actions after user selection of the menu items from the drawer menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                // User chose the "Log Out" item...
                //Toast.makeText(getApplicationContext(), "Logout toast. Cheers!",Toast.LENGTH_SHORT).show();
                Log.d(TAG,"logout pressed");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
