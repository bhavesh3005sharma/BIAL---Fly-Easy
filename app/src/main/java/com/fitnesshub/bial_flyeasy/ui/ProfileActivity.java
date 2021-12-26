package com.fitnesshub.bial_flyeasy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fitnesshub.bial_flyeasy.R;

public class ProfileActivity extends AppCompatActivity {
    Spinner genderSpinner;
    String genderString="Female";
    TextView editProfile,submitProfile,firstNameText,lastNameText,phoneText,ageText,genderText,aadharText,addressText;
    EditText firstNameEdit,lastNameEdit,phoneEdit,ageEdit,aadharEdit,addressEdit;
    ImageView addCheckList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        genderSpinner=findViewById(R.id.genderSpinner);
        editProfile=findViewById(R.id.editProfile);
        submitProfile=findViewById(R.id.submitProfile);
        firstNameText=findViewById(R.id.firstNameText);
        firstNameEdit=findViewById(R.id.firstNameEdit);
        lastNameText=findViewById(R.id.lastNameText);
        lastNameEdit=findViewById(R.id.lastNameEdit);
        phoneText=findViewById(R.id.phoneText);
        phoneEdit=findViewById(R.id.phoneEdit);
        ageText=findViewById(R.id.ageText);
        ageEdit=findViewById(R.id.ageEdit);
        genderText=findViewById(R.id.genderText);
        aadharText=findViewById(R.id.aadharText);
        aadharEdit=findViewById(R.id.aadharEdit);
        addressText=findViewById(R.id.addressText);
        addressEdit=findViewById(R.id.addressEdit);
        addCheckList=findViewById(R.id.addCheckList);

        String[] gender=new String[]{"Female","Male","Others"};
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(ProfileActivity.this,R.layout.support_simple_spinner_dropdown_item,gender);
        genderSpinner.setAdapter(spinnerAdapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genderString= adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editing();
            }
        });

        submitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitting();
            }
        });

    }

    public void editing(){
        editProfile.setVisibility(View.GONE);
        submitProfile.setVisibility(View.VISIBLE);
        firstNameText.setVisibility(View.GONE);
        firstNameEdit.setVisibility(View.VISIBLE);
        lastNameText.setVisibility(View.GONE);
        lastNameEdit.setVisibility(View.VISIBLE);
        phoneText.setVisibility(View.GONE);
        phoneEdit.setVisibility(View.VISIBLE);
        ageText.setVisibility(View.GONE);
        ageEdit.setVisibility(View.VISIBLE);
        genderText.setVisibility(View.GONE);
        genderSpinner.setVisibility(View.VISIBLE);
        aadharText.setVisibility(View.GONE);
        aadharEdit.setVisibility(View.VISIBLE);
        addressText.setVisibility(View.GONE);
        addressEdit.setVisibility(View.VISIBLE);
        addCheckList.setVisibility(View.VISIBLE);
    }

    public void submitting(){
        editProfile.setVisibility(View.VISIBLE);
        submitProfile.setVisibility(View.GONE);
        firstNameText.setVisibility(View.VISIBLE);
        firstNameEdit.setVisibility(View.GONE);
        lastNameText.setVisibility(View.VISIBLE);
        lastNameEdit.setVisibility(View.GONE);
        phoneText.setVisibility(View.VISIBLE);
        phoneEdit.setVisibility(View.GONE);
        ageText.setVisibility(View.VISIBLE);
        ageEdit.setVisibility(View.GONE);
        genderText.setVisibility(View.VISIBLE);
        genderSpinner.setVisibility(View.GONE);
        aadharText.setVisibility(View.VISIBLE);
        aadharEdit.setVisibility(View.GONE);
        addressText.setVisibility(View.VISIBLE);
        addressEdit.setVisibility(View.GONE);
        addCheckList.setVisibility(View.GONE);
    }
}

