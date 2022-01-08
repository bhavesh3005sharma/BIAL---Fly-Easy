package com.fitnesshub.bial_flyeasy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.databinding.ActivityFoodStoresBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FoodStoresActivity extends AppCompatActivity {
ActivityFoodStoresBinding activityBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        boolean isFood=intent.getBooleanExtra("isFood",true);

        activityBinding= DataBindingUtil.setContentView(this, R.layout.activity_food_stores);
        activityBinding.setIsFood(isFood);
        activityBinding.text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FoodStoresActivity.this,SingleFoodStoreActivity.class);
                intent.putExtra("isFood",isFood);
                startActivity(intent);
            }
        });
    }
}