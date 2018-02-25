package com.example.practicumapp;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class MainActivity extends OptionsMenu {


    private static final String TAG = "Main Activity"; // Constant for logging data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        myToolbar.setTitle("Main Activity");
        setSupportActionBar(myToolbar);
     }
}
