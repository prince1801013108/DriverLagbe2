package com.b2m.driverlagbe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AcceptJob extends AppCompatActivity implements View.OnClickListener{
    Intent intent;
    Button skipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_job);
        skipBtn=findViewById(R.id.skipBtnId);
        skipBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.skipBtnId){
            intent=new Intent(AcceptJob.this,Tracking.class);
            startActivity(intent);
        }

    }
}
