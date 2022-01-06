package com.fitnesshub.bial_flyeasy.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.database.Prefs;
import com.fitnesshub.bial_flyeasy.databinding.ActivityProfileBinding;
import com.fitnesshub.bial_flyeasy.databinding.LayoutProgressBinding;
import com.fitnesshub.bial_flyeasy.models.UserModel;
import com.fitnesshub.bial_flyeasy.utils.Constants;
import com.fitnesshub.bial_flyeasy.utils.HelperClass;
import com.fitnesshub.bial_flyeasy.viewModels.ProfileViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity {
    String genderString,airportString;

    ProfileViewModel viewModel;
    ActivityProfileBinding profileBinding;
    AlertDialog alertDialog;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel=new ViewModelProvider(this).get(ProfileViewModel.class);
        profileBinding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        spinner();

        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.title_activity_profile));
        viewModel.displayToastMsg().observe(this, msg -> HelperClass.toast(this, msg));

        profileBinding.chooseCity.setOnClickListener(view -> {
            Intent intent=new Intent(ProfileActivity.this,ChooseCityActivity.class);
            intent.putExtra("fromProfile",true);
            startActivity(intent);
        });

        profileBinding.editProfile.setOnClickListener(view -> profileBinding.setEdit(true));

        airportString=Constants.airportNames[viewModel.getCityInt()];

        profileBinding.submitProfile.setOnClickListener(view -> {
            String name=""+profileBinding.firstNameEdit.getText().toString();
            if(userModel==null || userModel.get_id()==null){
                HelperClass.toast(this,"User Data is Null");
                return;
            }
            UserModel newUserModel=new UserModel(userModel.get_id(),userModel.getEmail(),userModel.getPassword(),true,name,profileBinding.phoneEdit.getText().toString(),profileBinding.aadharEdit.getText().toString(),genderString,profileBinding.addressEdit.getText().toString(),Integer.parseInt(profileBinding.ageEdit.getText().toString()),userModel.getDefaultCheckList());
            String res= viewModel.validateData(newUserModel);
            if(res!=null && res.equals("Correct")) {
                viewModel.updateProfile(newUserModel).observe(this, responseResource -> {
                    if (responseResource!=null && responseResource.status == Constants.OKAY) {
                        sendToHomeScreen();
                        HelperClass.toast(this, "Updated Successfully");
                    } else {
                        buildAD();
                    }
                });
                profileBinding.setEdit(false);
            }
            else HelperClass.toast(this,res);
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
        builder.setTitle("Error").setMessage("Not able to process");
        builder.setCancelable(false).setPositiveButton("Dismiss", (dialogInterface, i) -> {
            alertDialog.dismiss();
            finish();
        });
        alertDialog=builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        alertDialog.getWindow().setBackgroundDrawable(null);
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
    }


    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getUserData().observe(this,responseResource -> {
            if(responseResource==null || responseResource.data==null) return;
            userModel = responseResource.data;
            profileBinding.setEdit(!responseResource.data.getProfileCompleted());
            setGender(responseResource.data.getGender());
            profileBinding.setAirport(airportString);
            profileBinding.setUserModel(responseResource.data);

            if(responseResource.status==Constants.OKAY)
                HelperClass.showSnackBar("Synced with the Server Successfully!",this,Color.GREEN);
            else if(responseResource.status==Constants.ERROR)
                HelperClass.showSnackBar("Synced with the Server Failed!",this,Color.RED);
        });
    }

    private void setGender(String gender) {
        if(gender==null) {
            profileBinding.genderSpinner.setSelection(2);
            return;
        }
        if(gender.equals("Female"))profileBinding.genderSpinner.setSelection(0);
        else if(gender.equals("Male"))profileBinding.genderSpinner.setSelection(1);
        else profileBinding.genderSpinner.setSelection(2);
    }

    @Override
    protected void onPause() {
        if (alertDialog != null) alertDialog.dismiss();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (alertDialog != null) alertDialog.dismiss();
        super.onDestroy();
    }
}

