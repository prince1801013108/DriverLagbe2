package com.b2m.driverlagbe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LocationEnable extends AppCompatActivity implements View.OnClickListener {
    Button locationSkipBtn,useMyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_enable);
        locationSkipBtn =findViewById(R.id.locationSkipBtnId);
        useMyLocation=findViewById(R.id.useMyLocationBtnId);
        useMyLocation.setOnClickListener(this);
        locationSkipBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.locationSkipBtnId||v.getId()==R.id.useMyLocationBtnId){
            Intent intent = new Intent(LocationEnable.this, SignUpOption.class);
            startActivity(intent);
        }
    }
}
