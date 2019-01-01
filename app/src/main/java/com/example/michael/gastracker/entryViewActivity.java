package com.example.michael.gastracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.List;

public class entryViewActivity extends AppCompatActivity {

    private int entryIndex = 0;

    private sqliteHelperClass database = null;
    private List<logEntryClass> entryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_view);

        database = new sqliteHelperClass(this);
        entryList = database.getAllEntries();

        // Define ui members
        TextView tv = (TextView) findViewById(R.id.testView);
        TextView edate = (TextView) findViewById(R.id.dateBox);
        TextView edistance = (TextView) findViewById(R.id.distanceBox);
        TextView eprice = (TextView) findViewById(R.id.priceBox);
        TextView evolume = (TextView) findViewById(R.id.volumeBox);
        TextView ecost = (TextView) findViewById(R.id.totalBox);

        // Get and parse the index
        String index = getIntent().getStringExtra("id");
        index = index.split(" ")[0];
        entryIndex = Integer.parseInt(index);

        // Set the title to make it looks nice
        tv.setText("Entry #" + index);

        // index now has the index of the entry, so find that and display the information
        logEntryClass currentEntry = database.getEntry(entryIndex);
        edate.setText(currentEntry.getDate());
        edistance.setText(currentEntry.getDistance());
        eprice.setText(currentEntry.getPrice());
        evolume.setText(currentEntry.getVolume());
        ecost.setText(currentEntry.getCost());

    }

    public void delete(View v) {
        // delete entry at entryIndex
        database.deleteEntry(database.getEntry(entryIndex));

        // for all entries after the one that is deleted, update their id
        for (logEntryClass entry : database.getAllEntries()) {
            if (entry.getId() > entryIndex) {
                entry.setId( entry.getId() - 1);
                database.updateEntry(entry);
            }
        }
        goBack(v);
    }

    public void goBack(View v) {
        Intent openHome = new Intent(entryViewActivity.this, listActivity.class);
        openHome.putExtra("Version", "1.0");
        entryViewActivity.this.startActivity(openHome);
    }

    public void editEntry(View v) {
        // Pass along the index, so that the editor can open that and autofill the forms, and
        // so it knows where to save the changes to.
        Intent editEntry = new Intent(entryViewActivity.this, entryEditorActivity.class);
        editEntry.putExtra("id", Integer.toString(entryIndex));
        entryViewActivity.this.startActivity(editEntry);
    }
}
