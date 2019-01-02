package com.example.michael.gastracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class listActivity extends AppCompatActivity {
    // Declare adapter attributes
    ArrayList<String> entries = null;
    ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Define UI Members
        ListView entryList_ui = findViewById(R.id.entryList);

        // Define adapter variables, there is a list for entries to display and for database data
        entries = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, entries);
        entryList_ui.setAdapter(adapter);

        // Declare the database interface
        sqliteHelperClass db = new sqliteHelperClass(this);
        List<logEntryClass> logList = db.getAllEntries();

        // Only the ID, date, and cost is displayed, so get those for each log entry on record
        for (logEntryClass entry : logList) {
            String header = Integer.toString(entry.getId());
            header += " - " + entry.getDate() + " - $";
            header += entry.getCost();
            addItem(header);
        }

        // When the user taps a list entry, we need to send that entry's id to the viewer
        entryList_ui.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedValue = (String) parent.getItemAtPosition(position);

                // Create an intent to open the entry viewer activity, passing the located index.
                Intent readEntry =
                        new Intent(listActivity.this, entryViewActivity.class);
                clickedValue = clickedValue.split(" ")[0];
                readEntry.putExtra("id", clickedValue);
                listActivity.this.startActivity(readEntry);
            }
        });
    }

    public void addItem(String entry) {
        entries.add(entry);
        adapter.notifyDataSetChanged();
    }

    public void homePage(View v) {
        Intent openHome = new Intent(listActivity.this, MainActivity.class);
        listActivity.this.startActivity(openHome);
    }
}
