package com.b2m.driverlagbe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUpOption extends AppCompatActivity implements View.OnClickListener {
    TextView headerText;
    Button driverBtn,userBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_option);
        driverBtn=findViewById(R.id.asDriverBtnId);
        userBtn=findViewById(R.id.asUserBtnId);
        headerText=findViewById(R.id.headerTextId);
        String text = "<font color=#E88574>Sign up</font> <font color=#ffffff>with\nemail and phone number</font>";
        headerText.setText(Html.fromHtml(text));
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.asDriverBtnId){
            intent=new Intent(SignUpOption.this,SignUp.class);
            startActivity(intent);
        }
        if (v.getId()==R.id.asUserBtnId){
            intent=new Intent(SignUpOption.this,SignUp.class);
            startActivity(intent);
        }

    }
}
