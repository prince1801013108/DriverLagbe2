package com.b2m.driverlagbe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.b2m.driverlagbe.Model.Prevalent;
import com.b2m.driverlagbe.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private Button LoginButton;
    private EditText InputphoneNumber, InputPassword;
    private ProgressDialog loadingBar;
    private String ParentDbName = "Users";
    private CheckBox chkboxreminder;
    TextView signUpText,headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_login);
        headerText=findViewById(R.id.headerTextId);
        String text = "<font color=#E88574>Sign in</font> <font color=#ffffff>with\nphone number</font>";
        headerText.setText(Html.fromHtml(text));

        LoginButton = findViewById(R.id.login_button);
        Paper.init(this);
        InputphoneNumber = findViewById(R.id.login_phone_number);
        InputPassword = findViewById(R.id.login_password_number);
        signUpText=findViewById(R.id.sign_up);

        loadingBar = new ProgressDialog(this);
        chkboxreminder = findViewById(R.id.remember_me_chck);

        //login access
        String UsersPhoneKey=Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey=Paper.book().read(Prevalent.UserpawwordKey);
        if (UsersPhoneKey !=""&& UserPasswordKey!="")
        {
            if (!TextUtils.isEmpty(UsersPhoneKey)&& !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UsersPhoneKey,UserPasswordKey);

                loadingBar.setTitle("Already Logged In");
                loadingBar.setMessage("please wait");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }



        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegistationActivity.class);
                startActivity(intent);
            }
        });
    }
    public void LoginUser() {

        String phone = InputphoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        if (TextUtils.isEmpty(phone)||phone.length() < 11 || phone.length() > 13) {
            Toast.makeText(LoginActivity.this, "Enter your valid phone number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)||password.length() < 6 || password.length() > 8) {
            Toast.makeText(LoginActivity.this, "Enter your valid password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Log in");
            loadingBar.setMessage("please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            LoginAccessAccount(phone, password);
        }
    }
    private void LoginAccessAccount(final String phone, final String password) {
        if (chkboxreminder.isChecked()) {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserpawwordKey, password);
        }


        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ParentDbName).child(phone).exists()) {
                    Users userData = dataSnapshot.child(ParentDbName).child(phone).getValue(Users.class);
                    if (userData.getPhone().equals(phone)) {
                        if (userData.getPassword().equals(password)) {
                            if (ParentDbName.equals("Users")) {
                                Toast.makeText(LoginActivity.this, "Dear User,You are login successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                Prevalent.currentOnlineUssers=userData;
                                finish();
                                startActivity(intent);
                            }

                        }

                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Sorry,We are not found any account with this" + phone + "number", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "You need to created a new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void AllowAccess(final String phone, final String password) {

        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users userData=dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone))
                    {
                        if (userData.getPassword().equals(password)){
                            Toast.makeText(LoginActivity.this,"Login successfull",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            Prevalent.currentOnlineUssers=userData;
                            startActivity(intent);
                            finish();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this,"Account with this"+phone+"phone do not exists",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this,"You need to created a new account",Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
