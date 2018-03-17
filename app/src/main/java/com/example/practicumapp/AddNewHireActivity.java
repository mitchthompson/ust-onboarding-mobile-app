package com.example.practicumapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.practicumapp.Interfaces.VolleyListResponseListener;
import com.example.practicumapp.Interfaces.VolleyUserResponseListener;
import com.example.practicumapp.models.User;
import com.example.practicumapp.models.Workflow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


/**
 * Add New Hire Activity. A form that lets a manager user add a new employee. After user submission,
 * makes API with volleyparser to add new user.
 *
 * @author Bonnie Peterson
 * @author Mitch Thompson
 * @since 1/22/2018
 * @see VolleyParser
 *
 *
 */

public class AddNewHireActivity extends MainActivity {
    private static final String TAG = AddNewHireActivity.class.getName();
    private Spinner workflow, employeeType;
    private Button btnCancel, btnDone;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private EditText firstName, lastName, email, phone, date;
    private ArrayList workflowMap;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_hire);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        // Initialize toolbar and set page title
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        myToolbar.setTitle("Add New Hire");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Retrieve access token from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("AccessToken", "");

        //API call to get all workflows for spinner
        workflowMap = new ArrayList<>();
        VolleyParser volleyParser = new VolleyParser(this.getApplicationContext(), accessToken);
        volleyParser.getWorkflows(new VolleyListResponseListener() {
            @Override
            public void onSuccess(ArrayList map) {
                workflowMap = map;
                addItemsOnWorkflowSpinner();
            }
        } );

        // Views
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        date = findViewById(R.id.date);
        addItemsOnTypeSpinner();


        // Add calendar settings for date picker
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Creates calendar settings for date picker and sets background
             * @param View
             */
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog
                        (AddNewHireActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog,
                                onDateSetListener,
                                year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            /**
             * Sets date text for display in UI
             * @param DatePicker view, year, month, day (int type)
             */
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month +1;
                String dateDisplay = month + "/" + day + "/" + year;
                date.setText(dateDisplay);

            }
        };
    }

    /**
     * Adds listeners for drop down menu selection for workflows
     */
    public void addListenerOnSpinnerItemSelection() {
        workflow = findViewById(R.id.workflow_ID);
    }

    /**
     * Add listeners for spinner and buttons
     */
    public void addListenerOnButton() {

        // Add listeners to all page buttons
        workflow = findViewById(R.id.workflow_ID);
        btnDone = findViewById(R.id.btnDone);
        btnCancel = findViewById(R.id.btnCancel);

        // Perform activity when "Done" is selected
        btnDone.setOnClickListener(new OnClickListener() {
            //add toast to display selection
            @Override
            public void onClick(View v) {
                addEmployee();
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddNewHireActivity.this, NewHireListActivity.class));
            }
        });
    }

    /**
     * Add items into workflow spinner dynamically
     */
    public void addItemsOnWorkflowSpinner() {
        List<String> spinnerList = new ArrayList<>();
        spinnerList.add("Select Workflow");
        workflow = findViewById(R.id.workflow_ID);
        for(int i = 0; i < workflowMap.size(); i++){
            Workflow workflow = (Workflow) workflowMap.get(i);
            Log.d(TAG, "Key: " + workflow.getId() + "Value: " + workflow.getName());
            spinnerList.add(workflow.getName());
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workflow.setAdapter(dataAdapter);
    }

    /**
     * Add items into employee type spinner
     */
    public void addItemsOnTypeSpinner() {
        List<String> spinnerTypeList = new ArrayList<>();
        spinnerTypeList.add("Select Type");
        spinnerTypeList.add("Employee");
        spinnerTypeList.add("Manager");
        employeeType = findViewById(R.id.type_spinner);

        ArrayAdapter<String> typeDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerTypeList);
        typeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeType.setAdapter(typeDataAdapter);
    }

    /**
     * Will be called when user clicks "Done" button on form. Validates form inputs and adds employee via
     * the API. Errors generate a message and stop the execution.
     */
    public void addEmployee(){
        String inputFirstName = firstName.getText().toString().trim();
        String inputLastName = lastName.getText().toString().trim();
        String inputEmail = email.getText().toString().trim();
        String inputPhone = phone.getText().toString().trim();
        String inputDate = date.getText().toString().trim();
        String inputWorkflow = String.valueOf(workflow.getSelectedItem());
        String inputType = String.valueOf(employeeType.getSelectedItem()).toLowerCase();

        // Form validation for all fields
        if(TextUtils.isEmpty(inputFirstName)){
            // Show message
            Toast.makeText(this, "Please enter a first name", Toast.LENGTH_SHORT).show();

            // Stops function from executing
            return;
        }

        if(TextUtils.isEmpty(inputLastName)){
            // Show message
            Toast.makeText(this, "Please enter a last name", Toast.LENGTH_SHORT).show();

            // Stops function from executing
            return;
        }

        if(TextUtils.isEmpty(inputEmail) || !Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()){
            // Show message
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();

            // Stops function from executing
            return;
        }

        if(TextUtils.isEmpty(inputPhone) || !Patterns.PHONE.matcher(inputPhone).matches() ){
            // Show message
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();

            // Stops function from executing
            return;
        }

        if(TextUtils.isEmpty(inputDate)){
            // Show message
            Toast.makeText(this, "Please enter the hire date", Toast.LENGTH_SHORT).show();

            // Stops function from executing
            return;
        }

        if(String.valueOf(workflow.getSelectedItem()) == "Select Workflow"){
            // Show message
            Toast.makeText(this, "Please select a workflow", Toast.LENGTH_SHORT).show();

            // Stops function from executing
            return;
        }

        if(String.valueOf(employeeType.getSelectedItem()) == "Select Type"){
            // Show message
            Toast.makeText(this, "Please select type", Toast.LENGTH_SHORT).show();

            // Stops function from executing
            return;
        }

        //test Active directory ID data

        //Retrieve access token from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("AccessToken", "");
        String adManagerID = sharedPreferences.getString("UserADID", "");

//      TODO: Remove random UUID generation when API implements solution
        String adEmployeeID = UUID.randomUUID().toString();
        Log.d(TAG, "Employee id : " + adEmployeeID);

        Log.d(TAG, "Name : " + inputFirstName + inputLastName + " , email: " +inputEmail + " , phone: " + inputPhone
                + " , type: " +inputType + " , hire date: " + inputDate + " , workflow: " +inputWorkflow);

        //create User object
        newUser = new User(adEmployeeID, inputFirstName, inputLastName, inputEmail, inputPhone, inputType, adManagerID, inputDate, inputWorkflow);

        //API call to add the new user
        VolleyParser volleyParser = new VolleyParser(this.getApplicationContext(), accessToken);
        volleyParser.addUser(newUser, new VolleyUserResponseListener(){
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "User ID : " + user.getId());
                startActivity(new Intent(AddNewHireActivity.this, NewHireListActivity.class));
            }
        });
    }

}


