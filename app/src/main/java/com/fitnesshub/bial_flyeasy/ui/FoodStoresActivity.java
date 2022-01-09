package com.fitnesshub.bial_flyeasy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.adapters.ArrivalDepartureAdapter;
import com.fitnesshub.bial_flyeasy.adapters.FoodStoreAdapter;
import com.fitnesshub.bial_flyeasy.databinding.ActivityFoodStoresBinding;
import com.fitnesshub.bial_flyeasy.models.FoodStoreModel;
import com.fitnesshub.bial_flyeasy.utils.Constants;
import com.fitnesshub.bial_flyeasy.utils.HelperClass;
import com.fitnesshub.bial_flyeasy.viewModels.FoodStoreViewModel;
import com.fitnesshub.bial_flyeasy.viewModels.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FoodStoresActivity extends AppCompatActivity implements FoodStoreAdapter.ShopListener{
ActivityFoodStoresBinding activityBinding;
FoodStoreViewModel viewModel;
boolean isFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        isFood=intent.getBooleanExtra("isFood",true);

        viewModel= new ViewModelProvider(this).get(FoodStoreViewModel.class);
        activityBinding=DataBindingUtil.setContentView(this, R.layout.activity_food_stores);
        activityBinding.setIsFood(isFood);
        activityBinding.backButton.setOnClickListener(view -> super.onBackPressed());

    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getShopsList((isFood==true)?"FOOD_ITEMS_SHOP":"AIRPORT_SHOP");
        viewModel.getResponse().observe(this,responseResource->{
            if (responseResource == null) {
                HelperClass.toast(this,"No response");}
            if (responseResource.status == Constants.IN_PROGRESS) {
                activityBinding.setStatus(Constants.IN_PROGRESS);
            }
            else if (responseResource.status == Constants.OKAY) {
                activityBinding.setStatus(Constants.OKAY);
                activityBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                FoodStoreAdapter adapter=new FoodStoreAdapter(this,responseResource.data);
                activityBinding.recyclerView.setAdapter(adapter);
                adapter.setUpOnCardListener(this);
            } else HelperClass.toast(this, " "+responseResource.status);
        });
    }

    @Override
    public void cardClicked(FoodStoreModel foodStoreModel, int position) {
        Intent intent=new Intent(this,SingleFoodStoreActivity.class);
        intent.putExtra("isFood",isFood);
        intent.putExtra("shopDetails",foodStoreModel);
        startActivity(intent);
    }
}