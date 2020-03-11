package com.b2m.driverlagbe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    TextView headerText,signUpBtn;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signUpBtn=findViewById(R.id.signUpBtnId);
        signUpBtn.setOnClickListener(this);

        headerText=findViewById(R.id.headerTextId);
        String text = "<font color=#E88574>Sign in</font> <font color=#ffffff>with\nphone number</font>";
        headerText.setText(Html.fromHtml(text));
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signUpBtnId){
            intent=new Intent(SignIn.this,Verification.class);
            startActivity(intent);
        }

    }
}
