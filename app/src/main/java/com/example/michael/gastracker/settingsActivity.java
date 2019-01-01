package com.example.michael.gastracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class settingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void homePage(View v) {
        Intent openHome = new Intent(settingsActivity.this, MainActivity.class);
        openHome.putExtra("Version", "1.0");
        settingsActivity.this.startActivity(openHome);
    }
}
