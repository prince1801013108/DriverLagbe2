package com.b2m.driverlagbe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class SignIn extends AppCompatActivity {
    TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        headerText=findViewById(R.id.headerTextId);
        String text = "<font color=#E88574>Sign in</font> <font color=#ffffff>with\nphone number</font>";
        headerText.setText(Html.fromHtml(text));
    }
}
