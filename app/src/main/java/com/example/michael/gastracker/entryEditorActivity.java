package com.example.michael.gastracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.List;

public class entryEditorActivity extends AppCompatActivity {

    private int index = 0;
    private logEntryClass currentEntry;

    private boolean alreadyExists;

    private sqliteHelperClass database = null;
    private List<logEntryClass> entryList;

    private TextView date_entry;
    private TextView distance_entry;
    private TextView price_entry;
    private TextView volume_entry;
    private TextView total_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_editor);
        alreadyExists = false;

        database = new sqliteHelperClass(this);
        entryList = database.getAllEntries();

        Log.w("MIKE:", "Database has been defined.");

        // Define ui members
        date_entry = (TextView)findViewById(R.id.dateEntry);
        distance_entry = (TextView)findViewById(R.id.tripDistanceEntry);
        price_entry = (TextView)findViewById(R.id.priceEntry);
        volume_entry = (TextView)findViewById(R.id.volumeEntry);
        total_entry = (TextView)findViewById(R.id.totalEntry);

        // Get the index
        String rawIndex = getIntent().getStringExtra("id");
        Log.w("MIKE:", "Received extra data: " + getIntent().getStringExtra("id"));
        index = Integer.parseInt(rawIndex);

        Log.w("MIKE:", " Editing new entry with id " + index);

        // Makes it look better to load the number in large letters
        TextView titleBar = (TextView) findViewById(R.id.edit_title);
        titleBar.setText("Editing Entry #" + index);

        currentEntry = new logEntryClass(index);
        for (logEntryClass entry : entryList) {
            if (entry.getId() == index) {
                // Set the UI members to display the information
                Log.w("MIKE:", "Entry already exists, will be updating current entry");
                alreadyExists = true;
                date_entry.setText(entry.getDate());
                distance_entry.setText(entry.getDistance());
                price_entry.setText(entry.getPrice());
                volume_entry.setText(entry.getVolume());
                total_entry.setText(entry.getCost());
                currentEntry = entry;
            }
        }
    }

    public void saveChanges(View v) {
        Log.w("MIKE:", "Entered save method.");
        currentEntry.setDate(date_entry.getText().toString());
        Log.w("MIKE:", date_entry.getText().toString());
        currentEntry.setDistance(distance_entry.getText().toString());
        currentEntry.setPrice(price_entry.getText().toString());
        currentEntry.setVolume(volume_entry.getText().toString());
        currentEntry.setCost(total_entry.getText().toString());
        Log.w("MIKE:", "Data filled out");
        writeEntry(currentEntry);

        // Go back after saving
        goBack(v);
    }

    public void goBack(View v) {
        // If the entry exists, go back to entry viewer, if not, go back to home
        if (alreadyExists) {
            Intent goBack = new Intent(entryEditorActivity.this, entryViewActivity.class);
            goBack.putExtra("id", Integer.toString(index) + " - ");
            entryEditorActivity.this.startActivity(goBack);
        } else {
            Intent goBack = new Intent(entryEditorActivity.this, MainActivity.class);
            entryEditorActivity.this.startActivity(goBack);
        }

    }

    public void writeEntry(logEntryClass entry) {
        Log.w("MIKE:", "Attempting to save.");
        if (alreadyExists) {
            database.updateEntry(entry);
        } else {
            Log.w("MIKE:", "Attempting to create new entry");
            database.addEntry(entry);
        }
    }
}
