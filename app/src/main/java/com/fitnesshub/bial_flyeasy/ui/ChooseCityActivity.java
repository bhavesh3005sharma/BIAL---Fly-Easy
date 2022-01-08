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
import com.fitnesshub.bial_flyeasy.models.SearchFlightModel;
import com.fitnesshub.bial_flyeasy.utils.Constants;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChooseCityActivity extends AppCompatActivity {
    ActivityChooseCityBinding cityBinding;
    int airportInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cityBinding=DataBindingUtil.setContentView(ChooseCityActivity.this,R.layout.activity_choose_city);

        spinner();

        Intent intent=getIntent();
        String intentFrom=intent.getStringExtra("intentFrom");
        SearchFlightModel searchFlightModel = (SearchFlightModel) intent.getSerializableExtra("searchFlightModel");

        cityBinding.submitButton.setOnClickListener(view -> {
            if(intentFrom==null || intentFrom.isEmpty()) sendToHomeScreen();
            else if(intentFrom.equals("fromProfile"))sendToProfileScreen();
            else if(intentFrom.equals("FlightBookingActivity_source")){
                searchFlightModel.setSource(Constants.airportCode[airportInt]);
                sendToFlightBooking(searchFlightModel);
            } else if(intentFrom.equals("FlightBookingActivity_destination")){
                searchFlightModel.setDestination(Constants.airportCode[airportInt]);
                sendToFlightBooking(searchFlightModel);
            } else sendToHomeScreen();
        });
    }

    private void sendToFlightBooking(SearchFlightModel searchFlightModel) {
        Intent intent = new Intent(this, FlightBookingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("searchFlightModel_city_choosen",searchFlightModel);
        startActivity(intent);
        finish();
    }

    private void sendToHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void sendToProfileScreen() {
        Prefs prefs= new Prefs(ChooseCityActivity.this);
        prefs.setCity(airportInt);
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    void spinner(){
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, Constants.airportNames);
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