package com.b2m.driverlagbe.Basic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.b2m.driverlagbe.MainActivity;
import com.b2m.driverlagbe.MainActivity2;
import com.b2m.driverlagbe.Model.Prevalent;
import com.b2m.driverlagbe.Model.Users;
import com.b2m.driverlagbe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class AcceptJob extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    Button skipBtn;
    private Button LoginButton;
    private EditText InputphoneNumber, InputPassword;
    private ProgressDialog loadingBar;
    private String ParentDbName = "Users";
    private String ParentDbName1 = "Drivers";
    private CheckBox chkboxreminder;
    TextView signUpText,headerText;
    //private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_job);
        getSupportActionBar().setTitle("Accept Job");
        skipBtn = findViewById(R.id.skipBtnId);
        skipBtn.setOnClickListener(this);

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
                            Toast.makeText(AcceptJob.this, "Dear User,You are login successfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(AcceptJob.this, MainActivity.class);
                            Prevalent.currentOnlineUssers=userData;
                            finish();
                            startActivity(intent);
                        }

                    }

                }

            }
            if (dataSnapshot.child(ParentDbName1).child(phone).exists()) {
                Users userData = dataSnapshot.child(ParentDbName1).child(phone).getValue(Users.class);
                if (userData.getPhone().equals(phone)) {
                    if (userData.getPassword().equals(password)) {
                        if (ParentDbName1.equals("Drivers")) {
                            Toast.makeText(AcceptJob.this, "Dear driver,You are login successfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(AcceptJob.this, MainActivity2.class);
                            Prevalent.currentOnlineUssers=userData;
                            finish();
                            startActivity(intent);
                        }

                    }

                }

            }
            else {
                Toast.makeText(AcceptJob.this, "Sorry,We are not found any account with this" + phone + "number", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                Toast.makeText(AcceptJob.this, "You need to created a new account", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AcceptJob.this,"Login successfull",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent=new Intent(AcceptJob.this, MainActivity2.class);
                            Prevalent.currentOnlineUssers=userData;
                            startActivity(intent);
                            finish();
                        }
                    }else {
                        Toast.makeText(AcceptJob.this,"Account with this"+phone+"phone do not exists",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Toast.makeText(AcceptJob.this,"You need to created a new account",Toast.LENGTH_SHORT).show();
                    }

                }
                if (dataSnapshot.child("Drivers").child(phone).exists())
                {
                    Users userData=dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone))
                    {
                        if (userData.getPassword().equals(password)){
                            Toast.makeText(AcceptJob.this,"Login successfull",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent=new Intent(AcceptJob.this, MainActivity2.class);
                            Prevalent.currentOnlineUssers=userData;
                            startActivity(intent);
                            finish();
                        }
                    }else {
                        Toast.makeText(AcceptJob.this,"Account with this"+phone+"phone do not exists",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Toast.makeText(AcceptJob.this,"You need to created a new account",Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.skipBtnId) {
            intent = new Intent(AcceptJob.this, Tracking.class);
            startActivity(intent);
        }


    }
}
