package com.example.michael.gastracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.List;

public class entryEditorActivity extends AppCompatActivity {
    // Information about the current entry
    private int entryId;
    private boolean entryAlreadyExists;

    // We need something to edit
    logEntryClass entryToEdit;

    // Database information
    private sqliteHelperClass db = null;
    private List<logEntryClass> entryList;

    // UI Members
    private TextView date_entry;
    private TextView distance_entry;
    private TextView price_entry;
    private TextView volume_entry;
    private TextView total_entry;
    private TextView title_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_editor);

        // Assume the entry does not already exist
        entryAlreadyExists = false;

        // Init. the database and get all entries
        db = new sqliteHelperClass(this);
        entryList = db.getAllEntries();

        // Init. the UI members
        date_entry = findViewById(R.id.dateEntry);
        distance_entry = findViewById(R.id.tripDistanceEntry);
        price_entry = findViewById(R.id.priceEntry);
        volume_entry = findViewById(R.id.volumeEntry);
        total_entry = findViewById(R.id.totalEntry);
        title_bar = findViewById(R.id.edit_title);

        // Parse the id of the entry to edit from the Intent
        entryId = Integer.parseInt(getIntent().getStringExtra("id"));

        // Set the title on the top of the screen
        title_bar.setText("Editing Entry #" + entryId);

        // Search through all entries to see if the selected entryId exists
        entryToEdit = new logEntryClass(entryId);
        for (logEntryClass e : entryList) {
            if (e.getId() == entryId) {
                entryAlreadyExists = true;
                entryToEdit = db.getEntry(entryId);
            }
        }

        // Populate the UI members with entryToEdit attribs
        date_entry.setText(entryToEdit.getDate());
        distance_entry.setText(entryToEdit.getDistance());
        price_entry.setText(entryToEdit.getPrice());
        volume_entry.setText(entryToEdit.getVolume());
        total_entry.setText(entryToEdit.getCost());
    }

    public void saveChanges(View v) {
        // Write changes to the entry copy
        entryToEdit.setId(entryId);
        entryToEdit.setDate(date_entry.getText().toString());
        entryToEdit.setDistance(distance_entry.getText().toString());
        entryToEdit.setPrice(price_entry.getText().toString());
        entryToEdit.setVolume(volume_entry.getText().toString());
        entryToEdit.setCost(total_entry.getText().toString());

        // Call the database and update the entry there
        if (entryAlreadyExists) {
            db.updateEntry(entryToEdit, false);
        } else {
            db.addEntry(entryToEdit);
        }

        // Go back after saving
        goBack(v);
    }

    public void goBack(View v) {
        // If the entry exists, go back to entry viewer, if not, go back to home
        if (entryAlreadyExists) {
            Intent goBack =
                    new Intent(entryEditorActivity.this, entryViewActivity.class);
            goBack.putExtra("id", Integer.toString(entryId));
            entryEditorActivity.this.startActivity(goBack);
        } else {
            Intent goBack = new Intent(entryEditorActivity.this, MainActivity.class);
            entryEditorActivity.this.startActivity(goBack);
        }

    }
}
