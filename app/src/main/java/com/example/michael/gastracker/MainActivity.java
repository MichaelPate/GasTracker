package com.example.michael.gastracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    sqliteHelperClass database = null;
    List<logEntryClass> entryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Access the database to gather statistics
        database = new sqliteHelperClass(this);
        entryList = database.getAllEntries();
        //TODO: Iterate through the log and get all the statistics needed for the home screen.
    }

    public void aboutPage(View v) {
        Intent openAbout = new Intent(MainActivity.this, aboutActivity.class);
        openAbout.putExtra("Version", "1.0");
        MainActivity.this.startActivity(openAbout);
    }

    public void settingsPage(View v) {
        Intent openSettings = new Intent(MainActivity.this, settingsActivity.class);
        MainActivity.this.startActivity(openSettings);
    }

    public void listEntries(View v) {
        Intent listEntries = new Intent(MainActivity.this, listActivity.class);
        MainActivity.this.startActivity(listEntries);
    }

    public void newEntry(View v) {
        int highestId = 0;
        for (logEntryClass entry : entryList) {
            if (entry.getId() > highestId) {
                highestId = entry.getId();
            }
        }

        // Add one so that the id wont conflict with other entries
        highestId++;

        Intent newEntry = new Intent(MainActivity.this, entryEditorActivity.class);
        newEntry.putExtra("id", Integer.toString(highestId));
        MainActivity.this.startActivity(newEntry);
    }
}
