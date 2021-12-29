package com.fitnesshub.bial_flyeasy.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.database.Prefs;
import com.fitnesshub.bial_flyeasy.databinding.ActivityAuthBinding;
import com.fitnesshub.bial_flyeasy.databinding.ActivityProfileBinding;
import com.fitnesshub.bial_flyeasy.databinding.LayoutProgressBinding;
import com.fitnesshub.bial_flyeasy.models.CheckList;
import com.fitnesshub.bial_flyeasy.models.Profile;
import com.fitnesshub.bial_flyeasy.models.UserModel;
import com.fitnesshub.bial_flyeasy.repositories.ProfileRepository;
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices;
import com.fitnesshub.bial_flyeasy.retrofit.RetrofitClient;
import com.fitnesshub.bial_flyeasy.utils.Constants;
import com.fitnesshub.bial_flyeasy.utils.HelperClass;
import com.fitnesshub.bial_flyeasy.viewModels.ProfileViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity {
    String genderString,airportString;

    ProfileViewModel viewModel;
    ActivityProfileBinding profileBinding;
    AlertDialog alertDialog;
    LayoutProgressBinding layoutAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel=new ViewModelProvider(this).get(ProfileViewModel.class);

        profileBinding = DataBindingUtil.setContentView(this,R.layout.activity_profile);

        viewModel.displayToastMsg().observe(this, msg ->
                HelperClass.toast(this, msg)
        );

        viewModel.getResponse().observe(this,userModelResourceResponse -> {
            if (userModelResourceResponse == null) return;
            if (alertDialog == null || userModelResourceResponse.status == Constants.IN_PROGRESS) {
                if (alertDialog != null) alertDialog.dismiss();
                buildAD();
            }
            else if (userModelResourceResponse.status == Constants.DISMISS_DIALOGUE)
                alertDialog.dismiss();
            else if (userModelResourceResponse.status == Constants.OKAY) {
                sendToHomeScreen();
                // TODO : Shift this saving data to Prefs in Repo
                Prefs.setUserLoggedIn(this, true);
                Prefs.setToken(this, userModelResourceResponse.token);
                Prefs.SetUserData(this, userModelResourceResponse.data);
            }
            else {
                layoutAD.setStatus(userModelResourceResponse.status);
                layoutAD.setTitle(userModelResourceResponse.message);
                layoutAD.setViewModelProfile(viewModel);
            }
        });

        spinner();

        profileBinding.chooseCity.setOnClickListener(view -> startActivity(new Intent(this,ChooseCityActivity.class)));

        profileBinding.editProfile.setOnClickListener(view -> profileBinding.setEdit(true));

        profileBinding.submitProfile.setOnClickListener(view -> {
            String name=""+profileBinding.firstNameEdit.getText().toString()+ profileBinding.lastNameEdit.getText().toString();
            viewModel.validateData(new UserModel("","","",true,name,profileBinding.phoneEdit.getText().toString(),profileBinding.aadharEdit.getText().toString(),genderString,profileBinding.addressEdit.getText().toString(),Integer.parseInt(profileBinding.ageEdit.getText().toString())));
            profileBinding.setEdit(false);
        });

    }

    void spinner(){
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
    }
    private void sendToHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    private void buildAD() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        layoutAD = LayoutProgressBinding.inflate(LayoutInflater.from(this));
        builder.setView(layoutAD.getRoot());
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        layoutAD.dismissButton.setOnClickListener((v) -> alertDialog.dismiss());
        layoutAD.setStatus(Constants.IN_PROGRESS);
        layoutAD.setViewModelProfile(viewModel);
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        alertDialog.getWindow().setBackgroundDrawable(null);
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
    }

}

