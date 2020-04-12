package com.b2m.driverlagbe.Basic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.b2m.driverlagbe.R;

public class Tracking extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    TextView skipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        getSupportActionBar().setTitle("Tracking");
        skipBtn=findViewById(R.id.skipBtnId);
        skipBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.skipBtnId){
            intent=new Intent(Tracking.this,Earn_money.class);
            startActivity(intent);
        }

    }
}