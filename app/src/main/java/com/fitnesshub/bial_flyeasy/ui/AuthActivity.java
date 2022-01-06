package com.fitnesshub.bial_flyeasy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.database.Prefs;
import com.fitnesshub.bial_flyeasy.databinding.ActivityAuthBinding;
import com.fitnesshub.bial_flyeasy.databinding.LayoutProgressBinding;
import com.fitnesshub.bial_flyeasy.utils.Constants;
import com.fitnesshub.bial_flyeasy.utils.HelperClass;
import com.fitnesshub.bial_flyeasy.viewModels.AuthViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AuthActivity extends AppCompatActivity {
    AuthViewModel viewModel;
    AlertDialog alertDialog;
    ActivityAuthBinding activityAuthBinding;
    LayoutProgressBinding layoutAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = (new ViewModelProvider(this)).get(AuthViewModel.class);
        activityAuthBinding = DataBindingUtil.setContentView(this, R.layout.activity_auth);

        viewModel.displayToastMsg().observe(this, msg ->
                HelperClass.toast(this, msg)
        );
        viewModel.getResponse().observe(this, responseResource -> {
            if (responseResource == null) return;
            if (alertDialog == null || responseResource.status == Constants.IN_PROGRESS) {
                if (alertDialog != null) alertDialog.dismiss();
                buildAD();
            } else if (responseResource.status == Constants.DISMISS_DIALOGUE)
                alertDialog.dismiss();
            else if (responseResource.status == Constants.OKAY) {
                if (responseResource.data != null) {
                    if (responseResource.data.getProfileCompleted()) sendToHomeScreen();
                    else sendToProfileScreen();
                } else {
                    layoutAD.setStatus(Constants.REGISTRATION_SUCCESS);
                    layoutAD.setTitle(null);
                }
            } else {
                layoutAD.setStatus(responseResource.status);
                layoutAD.setTitle(responseResource.message);
                layoutAD.setViewmodel(viewModel);
            }
        });

        activityAuthBinding.submitButton.setOnClickListener((v) -> {
            viewModel.validateData(
                    activityAuthBinding.editTextEmail.getText().toString(), activityAuthBinding.editTextPassword.getText().toString());
        });
    }

    private void sendToProfileScreen() {
        HelperClass.toast(this, "Incomplete Profile");
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void sendToHomeScreen() {
        HelperClass.toast(this, "Complete Profile");
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void buildAD() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.this);
        layoutAD = LayoutProgressBinding.inflate(LayoutInflater.from(this));
        builder.setView(layoutAD.getRoot());
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        layoutAD.setStatus(Constants.IN_PROGRESS);
        layoutAD.setViewmodel(viewModel);
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        alertDialog.getWindow().setBackgroundDrawable(null);
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    protected void onStart() {
        if (viewModel.isUserLoggedIn()) {
            sendToHomeScreen();
            //sendToProfileScreen();
        }
        super.onStart();
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