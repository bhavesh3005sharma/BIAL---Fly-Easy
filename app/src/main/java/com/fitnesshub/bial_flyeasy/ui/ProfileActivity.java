package com.fitnesshub.bial_flyeasy.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.database.Prefs;
import com.fitnesshub.bial_flyeasy.databinding.ActivityAuthBinding;
import com.fitnesshub.bial_flyeasy.databinding.ActivityProfileBinding;
import com.fitnesshub.bial_flyeasy.databinding.LayoutProgressBinding;
import com.fitnesshub.bial_flyeasy.models.CheckList;
import com.fitnesshub.bial_flyeasy.models.Profile;
import com.fitnesshub.bial_flyeasy.models.ResourceResponse;
import com.fitnesshub.bial_flyeasy.models.UserModel;
import com.fitnesshub.bial_flyeasy.repositories.ProfileRepository;
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices;
import com.fitnesshub.bial_flyeasy.retrofit.RetrofitClient;
import com.fitnesshub.bial_flyeasy.utils.Constants;
import com.fitnesshub.bial_flyeasy.utils.HelperClass;
import com.fitnesshub.bial_flyeasy.viewModels.ProfileViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import kotlin.Unit;

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
        spinner();

        viewModel.displayToastMsg().observe(this, msg -> HelperClass.toast(this, msg));

        profileBinding.chooseCity.setOnClickListener(view -> {
            Intent intent=new Intent(ProfileActivity.this,ChooseCityActivity.class);
            intent.putExtra("fromProfile",true);
            startActivity(intent);
        });

        profileBinding.editProfile.setOnClickListener(view -> profileBinding.setEdit(true));
        //if(!Prefs.getUser().getProfileCompleted())profileBinding.setEdit(true);

        profileBinding.submitProfile.setOnClickListener(view -> {
            String name=""+profileBinding.firstNameEdit.getText().toString();
            UserModel userModel=Prefs.getUser();
            LiveData<ResourceResponse<Unit>> responseLiveData= viewModel.validateData(new UserModel(userModel.get_id(),userModel.getEmail(),userModel.getPassword(),true,name,profileBinding.phoneEdit.getText().toString(),profileBinding.aadharEdit.getText().toString(),genderString,profileBinding.addressEdit.getText().toString(),Integer.parseInt(profileBinding.ageEdit.getText().toString())));
            responseLiveData.observe(this,responseResource->{
                if (responseResource == null) return;
                if (alertDialog == null || responseResource.status == Constants.IN_PROGRESS) {
                    if (alertDialog != null) alertDialog.dismiss();
                    buildAD();
                } else if (responseResource.status == Constants.DISMISS_DIALOGUE)
                    alertDialog.dismiss();
                else if (responseResource.status == Constants.OKAY) {
                    sendToHomeScreen();
                    layoutAD.setStatus(Constants.REGISTRATION_SUCCESS);
                    layoutAD.setTitle("Updated Successfully");

                } else {
                    layoutAD.setStatus(responseResource.status);
                    layoutAD.setTitle(responseResource.message);
                    layoutAD.setViewModelProfile(viewModel);
                }
            });
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


    @Override
    protected void onStart() {
        super.onStart();
        UserModel userModel=Prefs.getUser();
        MediatorLiveData<ResourceResponse<UserModel>> responseLiveData= viewModel.getUserData(userModel);
        responseLiveData.observe(this,responseResource -> {
            if (responseResource == null) return;
            if (alertDialog == null || responseResource.status == Constants.IN_PROGRESS) {
                if (alertDialog != null) alertDialog.dismiss();
                buildAD();
            } else if (responseResource.status == Constants.DISMISS_DIALOGUE)
                alertDialog.dismiss();
            else if (responseResource.status == Constants.OKAY) {
                UserModel userModel1= responseResource.data;
                layoutAD.setStatus(Constants.REGISTRATION_SUCCESS);
                layoutAD.setTitle("Updated Successfully");
                loadUI(userModel1);

            } else {
                layoutAD.setStatus(responseResource.status);
                layoutAD.setTitle(responseResource.message);
                layoutAD.setViewModelProfile(viewModel);
                sendToHomeScreen();
            }
        });
    }

    private void loadUI(UserModel userModel1) {
        profileBinding.firstNameText.setText(userModel1.getName());
        profileBinding.firstNameEdit.setText(userModel1.getName());
        profileBinding.phoneText.setText(userModel1.getPhone_no());
        profileBinding.phoneEdit.setText(userModel1.getPhone_no());
        profileBinding.aadharText.setText(userModel1.getAadharcard_no());
        profileBinding.aadharEdit.setText(userModel1.getAadharcard_no());
        profileBinding.addressText.setText(userModel1.getAddress());
        profileBinding.addressEdit.setText(userModel1.getAddress());
        profileBinding.ageText.setText(""+userModel1.getAge());
        profileBinding.ageEdit.setText(""+userModel1.getAge());
        String gender= userModel1.getGender();
        if(gender.equals("Female"))profileBinding.genderSpinner.setSelection(0);
        else if(gender.equals("Male"))profileBinding.genderSpinner.setSelection(1);
        else profileBinding.genderSpinner.setSelection(2);
    }
}

