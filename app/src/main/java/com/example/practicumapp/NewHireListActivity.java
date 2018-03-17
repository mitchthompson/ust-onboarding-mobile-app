package com.example.practicumapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class NewHireListActivity extends MainActivity {

    private static final String TAG = NewHireListActivity.class.getName(); // Constant for logging data

    private Context context;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private NewHireListAdapter newHireListAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private Map<String, String> employees;
    private ArrayList<String> newHireList;
    private ArrayList<String> newHireIDs;
    SearchView searchView;

    private Button addNewHireButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_hire_list);
        context = getApplicationContext();

        searchView = (SearchView) findViewById(R.id.searchView);

        //Enables tool bar, back button, & sets title displayed
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        myToolbar.setTitle("Employees");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Retrieve access token from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("AccessToken", "");
        String userADID = sharedPreferences.getString("UserADID", "");

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
        VolleyParser volleyParser = new VolleyParser(this.getApplicationContext(), accessToken);
        volleyParser.getUser(userADID, new VolleyUserResponseListener() {

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

                //events
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        newHireListAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

            }
        });
    }


}
