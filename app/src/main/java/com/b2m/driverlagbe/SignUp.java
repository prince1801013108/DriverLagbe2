package com.b2m.driverlagbe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SignUp extends AppCompatActivity {
    TextView headerText,signInBtn;
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpBtn=findViewById(R.id.signUpBtnId);
        signInBtn=findViewById(R.id.signInBtnId);
        headerText=findViewById(R.id.headerTextId);
        String text = "<font color=#E88574>Sign up</font> <font color=#ffffff>with\nemail and phone number</font>";
        headerText.setText(Html.fromHtml(text));
        System.out.println("Hello");
    }
}
