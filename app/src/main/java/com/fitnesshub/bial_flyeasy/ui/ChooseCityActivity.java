package com.fitnesshub.bial_flyeasy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.database.Prefs;
import com.fitnesshub.bial_flyeasy.databinding.ActivityChooseCityBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChooseCityActivity extends AppCompatActivity {
ActivityChooseCityBinding cityBinding;
int airportInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cityBinding = DataBindingUtil.setContentView(this,R.layout.activity_choose_city);
        spinner();

        Intent intent=getIntent();
        Boolean fromProfile=intent.getBooleanExtra("fromProfile",false);

        cityBinding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.setCity(airportInt);
                if(fromProfile)finish();
                else sendToHomeScreen();
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

    void spinner(){
        String[] airport=new String[]{"Bengaluru Airport","Mumbai Airport","Delhi Airport","Chennai Airport","Kolkata Airport","Hyderabad Airport"};
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,airport);
        cityBinding.airportSpinner.setAdapter(spinnerAdapter);
        cityBinding.airportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                airportInt= i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}