package com.fitnesshub.bial_flyeasy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.database.Prefs;
import com.fitnesshub.bial_flyeasy.databinding.ActivityMainBinding;
import com.fitnesshub.bial_flyeasy.utils.Constants;
import com.fitnesshub.bial_flyeasy.utils.HelperClass;
import com.fitnesshub.bial_flyeasy.viewModels.MainViewModel;
import com.google.android.material.navigation.NavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
ActivityMainBinding activityBinding;
NavigationView navigationView;
MainViewModel viewModel;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel= new ViewModelProvider(this).get(MainViewModel.class);
        activityBinding= DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);
        setSupportActionBar(activityBinding.homeToolbar);
        activityBinding.drawerOpener.setOnClickListener(view -> activityBinding.drawerLayout.openDrawer(GravityCompat.START));
        navigationView.setNavigationItemSelectedListener(this);
        //activityBinding.notifications.setOnClickListener(view -> startActivity(new Intent(this,NotificationActivity.class)));
    }

    @Override
    public void onBackPressed() {
        if (activityBinding.drawerLayout.isDrawerOpen(GravityCompat.START))
            activityBinding.drawerLayout.closeDrawer(GravityCompat.START);
        else {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("EXIT");
            alert.setMessage("Do you want to exit the application?");
            alert.setCancelable(false);
            alert.setPositiveButton("Exit", (dialog, which) -> {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            });
            alert.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = alert.create();
            dialog.show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Prefs prefs=new Prefs(this);
        activityBinding.cityWifi.setText(Constants.airportNames[prefs.getCityInt()] + " Airport Wifi");
        viewModel.getData(prefs.getUser().get_id(), Constants.airportCode[prefs.getCityInt()]);
        viewModel.getResponse().observe(this,responseResource->{
            if (responseResource == null) return;
            if (responseResource.status == Constants.IN_PROGRESS) {
                activityBinding.setLoading(true);
            }
            else if (responseResource.status == Constants.OKAY) {
                activityBinding.setLoading(true);
                activityBinding.setHomeModel(responseResource.data);
            } else {
                HelperClass.toast(this,responseResource.message);
                finish();
            }
        });
        viewModel.displayToastMsg().observe(this,messge->{HelperClass.toast(this,messge);});
    }

    private void closeDrawer() { activityBinding.drawerLayout.closeDrawer(GravityCompat.START); }

    public void guidelines(){

    }
}