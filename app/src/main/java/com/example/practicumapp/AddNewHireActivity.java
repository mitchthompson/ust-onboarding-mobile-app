package com.example.practicumapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;

import com.example.practicumapp.Interfaces.VolleyUserResponseListener;
import com.example.practicumapp.models.User;

public class AddNewHireActivity extends AppCompatActivity {
    private static final String TAG = AddNewHireActivity.class.getName(); // Constant for logging data

    private Spinner workflow;
    private Button btnCancel, btnDone;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private EditText firstName, lastName, email, phone, date;
    private Map<String,String> newUser;


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

        // Views
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        date = findViewById(R.id.date);
        addItemsOnWorkflowSpinner();


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

        // Perform activity when "Done" is selected -- for now just displays toast confirming drop
        // down selection
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

    // add items into spinner dynamically
    public void addItemsOnWorkflowSpinner() {
        //TODO: get workflow list from api call
        workflow = findViewById(R.id.workflow_ID);
        List<String> list = new ArrayList<>();
        list.add("Select Workflow");
        list.add("Workflow 1");
        list.add("Workflow 2");
        list.add("Workflow 3");
        list.add("Workflow 4");
        list.add("Workflow 5");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workflow.setAdapter(dataAdapter);
    }

    /**
     * Will be called when user clicks "Done" button on form. Validates form inputs and adds employee via
     * the API. Errors generate a message and stop the execution.
     */
    //TODO: Call this method when "Done" button is clicked
    public void addEmployee(){
        String inputFirstName = firstName.getText().toString().trim();
        String inputLastName = lastName.getText().toString().trim();
        String inputEmail = email.getText().toString().trim();
        String inputPhone = phone.getText().toString().trim();
        //TODO: see if date picker EditText view can be cast to string in this way
        String inputDate = date.getText().toString().trim();
        String inputWorkflow = String.valueOf(workflow.getSelectedItem());

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

        newUser = new HashMap<>();
        newUser.put("firstName", inputFirstName);
        newUser.put("lastName", inputLastName);
        newUser.put("email", inputEmail);
        newUser.put("phone", inputPhone);
        newUser.put("startDate", inputDate);
        newUser.put("workflow", inputWorkflow);

        Log.d(TAG, "User: " + newUser.toString());

//        VolleyParser volleyParser = new VolleyParser(this.getApplicationContext());
//        volleyParser.addNewUser((User) newUser,  new VolleyUserResponseListener(){
//                    @Override
//                    public void onSuccess(User user) {
//                        startActivity(new Intent(AddNewHireActivity.this, NewHireListActivity.class));
//                    }
//        });

        }

    // Creates menu
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
                Toast.makeText(getApplicationContext(), "Logout toast. Cheers!",
                        Toast.LENGTH_SHORT).show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    }
