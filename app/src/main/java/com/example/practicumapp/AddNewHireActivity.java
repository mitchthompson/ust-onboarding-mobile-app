package com.example.practicumapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class AddNewHireActivity extends MainActivity {

    private Spinner spinner1;
    private Button btnCancel, btnDone;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private EditText firstName, lastName, email, phone, date;


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
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Views
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        date = (EditText) findViewById(R.id.date);



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
        spinner1 = (Spinner) findViewById(R.id.spinner1);
    }

    /**
     * Add listeners for spinner and buttons
     */
    public void addListenerOnButton() {

        // Add listeners to all page buttons
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnCancel = (Button) findViewById(R.id.btnCancel);


        // Perform activity when "Done" is selected -- for now just displays toast confirming drop
        // down selection
        btnDone.setOnClickListener(new OnClickListener() {

            //add toast to display selection
            @Override
            public void onClick(View v) {

                Toast.makeText(AddNewHireActivity.this,
                        "OnClickListener : " +
                                "\nSpinner : "+ String.valueOf(spinner1.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
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
        //TODO: add variable for workflow once API workflow is determined (ie. number to indicate which workflow)

        // Form validation for all fields
        if(TextUtils.isEmpty(inputFirstName)){
            // Show message
            Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();

            // Stops function from executing
            return;
        }

        if(TextUtils.isEmpty(inputLastName)){
            // Show message
            Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT).show();

            // Stops function from executing
            return;
        }

        if(TextUtils.isEmpty(inputEmail)){
            // Show message
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();

            // Stops function from executing
            return;
        }

        if(TextUtils.isEmpty(inputPhone)){
            // Show message
            Toast.makeText(this, "Please enter phone", Toast.LENGTH_SHORT).show();

            // Stops function from executing
            return;
        }

        if(TextUtils.isEmpty(inputDate)){
            // Show message
            Toast.makeText(this, "Please enter hire date", Toast.LENGTH_SHORT).show();

            // Stops function from executing
            return;
        }

        // If validations are OK, show progress bar
        //TODO: Add progress bar

        //TODO: Add API POST method to add employee

        }

    }
