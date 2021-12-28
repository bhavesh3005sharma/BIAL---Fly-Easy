package com.fitnesshub.bial_flyeasy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.database.Prefs;
import com.fitnesshub.bial_flyeasy.databinding.ActivityProfileBinding;
import com.fitnesshub.bial_flyeasy.models.CheckList;
import com.fitnesshub.bial_flyeasy.models.Profile;
import com.fitnesshub.bial_flyeasy.repositories.ProfileRepository;
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices;
import com.fitnesshub.bial_flyeasy.retrofit.RetrofitClient;
import com.fitnesshub.bial_flyeasy.utils.HelperClass;
import com.fitnesshub.bial_flyeasy.viewModelFactories.ProfileViewModelFactory;
import com.fitnesshub.bial_flyeasy.viewModels.ProfileViewModel;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    String genderString,airportString;

    ProfileViewModel viewModel;
    ActivityProfileBinding profileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ApiServices apiServices= RetrofitClient.getInstance().create(ApiServices.class);
        ProfileRepository repository=new ProfileRepository(apiServices);
        viewModel=new ViewModelProvider(this,new ProfileViewModelFactory(repository)).get(ProfileViewModel.class);

        profileBinding = DataBindingUtil.setContentView(this,R.layout.activity_profile);

        viewModel.displayToastMsg().observe(this, msg ->
                HelperClass.toast(this, msg)
        );


        String[] gender=new String[]{"Female","Male","Others"};
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(ProfileActivity.this,R.layout.support_simple_spinner_dropdown_item,gender);
        profileBinding.genderSpinner.setAdapter(spinnerAdapter);
        profileBinding.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genderString= adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        profileBinding.chooseCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(this,ActivityChooseCity.class));
            }
        });

        profileBinding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileBinding.setEdit(true);
            }
        });

        profileBinding.submitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=""+profileBinding.firstNameEdit.getText().toString()+ profileBinding.lastNameEdit.getText().toString();
                airportString= Prefs.getCity(ProfileActivity.this);
                ArrayList<CheckList> list=new ArrayList<>();
                viewModel.validateData(new Profile(name,null,profileBinding.ageEdit.getText().toString(),genderString,profileBinding.addressEdit.getText().toString(),airportString,profileBinding.phoneEdit.getText().toString(),profileBinding.aadharEdit.getText().toString(),list));
                profileBinding.setEdit(false);
            }
        });

    }
}

