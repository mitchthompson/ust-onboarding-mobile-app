package com.example.practicumapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.practicumapp.Interfaces.VolleyUserResponseListener;
import com.example.practicumapp.adapters.NewHireListAdapter;
import com.example.practicumapp.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NewHireListActivity extends AppCompatActivity {
    private static final String TAG = NewHireListActivity.class.getName(); // Constant for logging data
    private Button addNewHireButton;

    private Context context;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private NewHireListAdapter newHireListAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    private Map<String, String> employees;
    private ArrayList<String> newHireList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_hire_list);
        context = getApplicationContext();

        /*Enables tool bar & sets title displayed
        Can customize menu items in res/menu/main_menu.xml*/
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        myToolbar.setTitle("New Hire List");
        setSupportActionBar(myToolbar);

        /*
        sets click listener for add_new_hire_btn to launch AddNewHireActivity
         */
        addNewHireButton = findViewById(R.id.add_new_hire_btn);
        addNewHireButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(NewHireListActivity.this, AddNewHireActivity.class));
            }
        });

        employees = new HashMap<>();
        newHireList = new ArrayList<>();
        VolleyParser volleyParser = new VolleyParser(this.getApplicationContext());
        volleyParser.getUser("72AD9DBC60AE485782D43A1AE09279A4", new VolleyUserResponseListener() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "New Hire Employees : " + user.getEmployees().toString());
                employees = user.getEmployees();

                for(Map.Entry<String, String> entry : employees.entrySet()){
                    Log.d(TAG, "Key: " + entry.getKey() + "Value: " + entry.getValue());
                    newHireList.add(entry.getValue());
                }

                relativeLayout = (RelativeLayout) findViewById(R.id.new_hire_relativeLayout);
                recyclerView = (RecyclerView) findViewById(R.id.new_hire_list_recycler);
                recyclerViewLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(recyclerViewLayoutManager);

                newHireListAdapter = new NewHireListAdapter(context, newHireList);
                recyclerView.setAdapter(newHireListAdapter);

            }
        });

        /**
         * TODO: get data from JSON parser, remove test dada
         */
//        newHireList = new ArrayList<>(Arrays.asList("John Doe", "Susan Smith", "Blake Bortles",
//                "Michael Barnum", "Jason Mendoza", "Jill Riker", "Andrew Ryan", "Frank Fontaine", "Generic Name", "Reference Name"));

//        relativeLayout = (RelativeLayout) findViewById(R.id.new_hire_relativeLayout);
//        recyclerView = (RecyclerView) findViewById(R.id.new_hire_list_recycler);
//        recyclerViewLayoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(recyclerViewLayoutManager);
//
//        newHireListAdapter = new NewHireListAdapter(context, newHireList);
//        recyclerView.setAdapter(newHireListAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
