package com.example.michael.gastracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class aboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void homePage(View v) {
        Intent openHome = new Intent(aboutActivity.this, MainActivity.class);
        openHome.putExtra("Version", "1.0");
        aboutActivity.this.startActivity(openHome);
    }
}
