package com.b2m.driverlagbe.Basic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.b2m.driverlagbe.R;
import com.b2m.driverlagbe.LogInAndRes.SignUpOption;

public class LocationEnable extends AppCompatActivity implements View.OnClickListener {
    Button locationSkipBtn,useMyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_enable);
        getSupportActionBar().setTitle("Location Enable");
        locationSkipBtn =findViewById(R.id.locationSkipBtnId);
        useMyLocation=findViewById(R.id.useMyLocationBtnId);
        useMyLocation.setOnClickListener(this);
        locationSkipBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.useMyLocationBtnId){
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        if(v.getId()==R.id.locationSkipBtnId){
            Intent intent = new Intent(LocationEnable.this, SignUpOption.class);
            startActivity(intent);
        }
    }
}
