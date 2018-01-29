package com.example.practicumapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practicumapp.Interfaces.UserResponseCallback;
import com.example.practicumapp.models.User;

/**
 *
 * very basic login activity -- Joseph Sayler
 *
 * at this time, this activity accepts a username and password, compares them to hard coded
 * username/password and if correct redirects user to the tasks list activity.
 *
 * TODO: implement methods from a login library to authenticate with API
 * TODO: remove hardcoded username/password
 * TODO: implement better error handling / alerting user if username/password incorrect
 * TODO: re-write these comments to conform with our JavaDoc standards
 *
 **/
public class LoginActivity extends AppCompatActivity {

    EditText unameField, pswdField;
    Button submitLogin;
    String userID, userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login();
    }

    void Login(){
        unameField = (EditText)findViewById(R.id.username);
        pswdField = (EditText)findViewById(R.id.password);
        submitLogin = (Button)findViewById(R.id.loginButton);

        submitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID = unameField.getText().toString();
                userPass = pswdField.getText().toString();

                UserLoginTask login = new UserLoginTask(userID, userPass);
                login.doInBackground();

                if(userID.equals("admin") && userPass.equals("admin")){
                    Toast.makeText(LoginActivity.this, "Username and Password is correct", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, TaskListActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                UserParser getJson = new UserParser(getApplicationContext());
                getJson.getUser(mEmail, new UserResponseCallback() {
                    @Override
                    public void onSuccess(User user) {
                        String temp = "Email: " + user.getEmail() + "\nFirst: " + user.getFirstName() + "\nLast: " + user.getLastName()
                                + "\nUsername: " + user.getUserName() + "\nType: " + user.getType() + "\nID: " + user.getId()
                                + "\nPhone: " + user.getPhone();
                        Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();



                    }
                });

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}