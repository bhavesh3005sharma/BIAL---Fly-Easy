package com.fitnesshub.bial_flyeasy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.databinding.ActivityWeatherBinding;
import com.fitnesshub.bial_flyeasy.utils.Constants;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherActivity extends AppCompatActivity {
    ActivityWeatherBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String city= Constants.airportNames[intent.getIntExtra("cityInt",0)];
        activityBinding= DataBindingUtil.setContentView(this,R.layout.activity_weather);
        activityBinding.setCity(city);
        activityBinding.backButton.setOnClickListener(view -> super.onBackPressed());
    }
}