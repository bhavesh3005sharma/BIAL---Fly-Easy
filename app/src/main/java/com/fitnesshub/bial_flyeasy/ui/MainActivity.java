package com.fitnesshub.bial_flyeasy.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fitnesshub.bial_flyeasy.R;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding= DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);

        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, FlightBookingActivity.class));
    }
}