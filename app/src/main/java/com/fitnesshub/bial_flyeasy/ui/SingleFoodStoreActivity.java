package com.fitnesshub.bial_flyeasy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.databinding.ActivitySingleFoodStoreBinding;
import com.fitnesshub.bial_flyeasy.models.FoodStoreModel;
import com.fitnesshub.bial_flyeasy.utils.HelperClass;
import com.squareup.picasso.Picasso;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SingleFoodStoreActivity extends AppCompatActivity {
ActivitySingleFoodStoreBinding activityBinding;
FoodStoreModel model;
boolean isFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding= DataBindingUtil.setContentView(this,R.layout.activity_single_food_store);
        Intent intent=getIntent();
        isFood = intent.getBooleanExtra("isFood",true);
        model=(FoodStoreModel) intent.getSerializableExtra("shopDetails");
        activityBinding.setShopModel(model);
        activityBinding.setIsFood(isFood);
        Picasso.get().load(model.getImages().get(0)).into(activityBinding.image1);
        Picasso.get().load(model.getImages().get(1)).into(activityBinding.image2);
        Picasso.get().load(model.getImages().get(2)).into(activityBinding.image3);
        Picasso.get().load(model.getImages().get(3)).into(activityBinding.image4);
        activityBinding.backButton.setOnClickListener(view -> super.onBackPressed());
    }
}