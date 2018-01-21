package com.example.practicumapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNewHireActivity extends AppCompatActivity {

    //TODO add date picker - see wireframe

    private Spinner spinner1, spinner2;
    private Button btnFinish;

    //assign radio button variables
    private RadioGroup employeeTypeGroup;
    private RadioButton employeeTypeButton;
    private Button btnDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_hire);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

         /*Enables tool bar & sets title displayed
        Can customize menu items in res/menu/main_menu.xml
        Can customize toolbar in res/layout/toolbar_layout.xml*/
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        myToolbar.setTitle("Add New Hire");
        setSupportActionBar(myToolbar);
    }

    // Creates menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Defines the actions after user selection of the menu items in the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                // User chose the "Log Out" item...
                Toast.makeText(getApplicationContext(), "Logout toast. Cheers!",
                        Toast.LENGTH_SHORT).show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // Dropdown selections
    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
    }

    // Get the selected dropdown list value
    public void addListenerOnButton() {

        // Add listenters to all page buttons
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        btnFinish = (Button) findViewById(R.id.btnFinish);
        employeeTypeGroup = (RadioGroup) findViewById(R.id.radioEmployeeType);


        //TODO edit setOnClickListener to include all form elements
        btnFinish.setOnClickListener(new OnClickListener() {

            //add toast to display selection
            @Override
            public void onClick(View v) {

                Toast.makeText(AddNewHireActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }
}