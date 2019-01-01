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

    ArrayList<String> entries = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private sqliteHelperClass database = null;
    private List<logEntryClass> logList;

    ListView entryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        entryList = (ListView) findViewById(R.id.entryList);

        database = new sqliteHelperClass(this);
        logList = database.getAllEntries();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, entries);
        entryList.setAdapter(adapter);

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Iterate through the sql database, dispalying the items in it
        // as follows:
        // id - date - cost
        //....
        for (logEntryClass entry : logList) {
            String header = Integer.toString(entry.getId());
            header += " - " + entry.getDate() + " - $";
            header += entry.getCost();
            addItem(header);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        entryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedvalue = (String) parent.getItemAtPosition(position);

                // Create an intent to open the entry viewer activity, passing the located index.
                Intent readEntry = new Intent(listActivity.this, entryViewActivity.class);
                readEntry.putExtra("id", clickedvalue);
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
        openHome.putExtra("Version", "1.0");
        listActivity.this.startActivity(openHome);
    }

}
