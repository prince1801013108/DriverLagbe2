package com.b2m.driverlagbe.Basic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.b2m.driverlagbe.R;

public class Earn_money extends AppCompatActivity implements View.OnClickListener {
    Button getStartBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_money);
        getSupportActionBar().setTitle("Earn Money");
        getStartBtn=findViewById(R.id.getStartBtnId);
        getStartBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.getStartBtnId){
            intent=new Intent(Earn_money.this,LocationEnable.class);
            startActivity(intent);
        }
    }
}
