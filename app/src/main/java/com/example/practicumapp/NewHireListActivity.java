package com.example.practicumapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.practicumapp.adapters.NewHireListAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class NewHireListActivity extends AppCompatActivity {
    private Button addNewHireButton;
    private SearchView searchView;

    private Context context;
    private String[] testData;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private NewHireListAdapter newHireListAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
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

        /**
         * TODO: get data from JSON parser, remove test dada
         */
        newHireList = new ArrayList<>(Arrays.asList("John Doe", "Susan Smith", "Blake Bortles",
                "Michael Barnum", "Jason Mendoza", "Jill Riker", "Andrew Ryan", "Frank Fontaine", "Generic Name", "Reference Name"));

        relativeLayout = (RelativeLayout) findViewById(R.id.new_hire_relativeLayout);
        recyclerView = (RecyclerView) findViewById(R.id.new_hire_list_recycler);
        recyclerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        newHireListAdapter = new NewHireListAdapter(context, newHireList);
        recyclerView.setAdapter(newHireListAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
