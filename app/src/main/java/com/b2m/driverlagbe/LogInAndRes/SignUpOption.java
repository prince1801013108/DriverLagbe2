package com.b2m.driverlagbe.LogInAndRes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.b2m.driverlagbe.MainActivity2;
import com.b2m.driverlagbe.R;

public class SignUpOption extends AppCompatActivity implements View.OnClickListener {
    TextView headerText;
    Button driverBtn,userBtn,signInBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_option);
        getSupportActionBar().setTitle("Sign Up Option");
        driverBtn=findViewById(R.id.asDriverBtnId);
        userBtn=findViewById(R.id.asUserBtnId);
        signInBtn=findViewById(R.id.signInBtnId);
        headerText=findViewById(R.id.headerTextId);
        String text = "<font color=#E88574>Choose</font> <font color=#ffffff>your\nAccount type</font>";
        headerText.setText(Html.fromHtml(text));

        userBtn.setOnClickListener(this);
        driverBtn.setOnClickListener(this);
        signInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.asDriverBtnId){
            intent=new Intent(SignUpOption.this, RegistationActivity.class);
            intent.putExtra("option","Drivers");
            startActivity(intent);
        }
        if(v.getId()==R.id.asUserBtnId){
            intent=new Intent(SignUpOption.this, RegistationActivity.class);
            intent.putExtra("option","Users");
            startActivity(intent);
        }
        if (v.getId()==R.id.signInBtnId){
            intent=new Intent(SignUpOption.this, LoginActivity.class);
            startActivity(intent);
        }

    }
}
