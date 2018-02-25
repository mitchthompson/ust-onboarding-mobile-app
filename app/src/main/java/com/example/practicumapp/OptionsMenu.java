package com.example.practicumapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OptionsMenu extends AppCompatActivity {

    String TAG = "MENU ACTIVITY : ";

    /**
     * Creates actionbar menu and inflates menu hierarchy from menu/main_menu.xml.
     * @param menu The options menu which items are placed
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_debug:
                startActivity(new Intent(OptionsMenu.this, DebugActivity.class));
                Log.d(TAG,"debug menu activity selected");
            case R.id.action_logout:
                Log.d(TAG,"logout pressed");
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
