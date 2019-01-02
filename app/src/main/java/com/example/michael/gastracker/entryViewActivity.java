package com.example.michael.gastracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.List;

public class entryViewActivity extends AppCompatActivity {

    private int entryId = 0;
    private logEntryClass entryToView;

    // Database information
    private sqliteHelperClass db = null;
    private List<logEntryClass> entryList;

    // UI Members
    private TextView date_view;
    private TextView distance_view;
    private TextView price_view;
    private TextView volume_view;
    private TextView cost_view;
    private TextView title_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_view);

        // Init. the database and get all entries
        db = new sqliteHelperClass(this);
        entryList = db.getAllEntries();

        // Init. the UI members
        date_view = findViewById(R.id.dateBox);
        distance_view = findViewById(R.id.distanceBox);
        price_view = findViewById(R.id.priceBox);
        volume_view = findViewById(R.id.volumeBox);
        cost_view = findViewById(R.id.totalBox);
        title_bar = findViewById(R.id.testView);

        // Get the entry ID from the intent
        entryId = Integer.parseInt(getIntent().getStringExtra("id"));

        // Set the title to make it looks nice
        title_bar.setText("Entry #" + entryId);

        // Get the entry and update the UI elements
        entryToView = db.getEntry(entryId);
        date_view.setText(entryToView.getDate());
        distance_view.setText(entryToView.getDistance());
        price_view.setText(entryToView.getPrice());
        volume_view.setText(entryToView.getVolume());
        cost_view.setText(entryToView.getCost());
    }

    public void delete(View v) {
        int deletedId = entryToView.getId();

        for (logEntryClass e : db.getAllEntries()) {
            if(e.getId() > deletedId) {
                db.updateEntry(e, true);
            }
        }

        // Finally, delete the entry and go back
        db.deleteEntry(entryToView);
        goBack(v);
    }

    public void goBack(View v) {
        Intent returnBack = new Intent(entryViewActivity.this, listActivity.class);
        entryViewActivity.this.startActivity(returnBack);
    }

    public void editEntry(View v) {
        Intent editEntry = new Intent(entryViewActivity.this, entryEditorActivity.class);
        editEntry.putExtra("id", Integer.toString(entryId));
        entryViewActivity.this.startActivity(editEntry);
    }
}
