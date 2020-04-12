package com.b2m.driverlagbe.LogInAndRes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.b2m.driverlagbe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegistationActivity extends AppCompatActivity {
    private Button CreatedAccountButton,logInText;
    private EditText InputName,InputMail,InputPhone,InputPassword;
    private ProgressDialog loading;
    private TextView headerText;
    String option;
    Bundle  bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);
        getSupportActionBar().setTitle("Sign Up");

        headerText=findViewById(R.id.headerTextId);
        String text = "<font color=#E88574>Sign up</font> <font color=#ffffff>with\nphone number and many more</font>";
        headerText.setText(Html.fromHtml(text));

        CreatedAccountButton=findViewById(R.id.registation_button);
        InputName=findViewById(R.id.registion_name);
        InputMail=findViewById(R.id.registion_mail);
        InputPhone=findViewById(R.id.registation_phone_number);
        InputPassword=findViewById(R.id.registion_password_number);
        logInText=findViewById(R.id.log_in);

        loading =new ProgressDialog(this);

        bundle=getIntent().getExtras();
        option=bundle.getString("option");

        CreatedAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatedAccount();
            }


        });
        logInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistationActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void CreatedAccount() {
        String name=InputName.getText().toString();
        String mail=InputMail.getText().toString();
        String phone=InputPhone.getText().toString();
        String password=InputPassword.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (TextUtils.isEmpty(name)){
            Toast.makeText(RegistationActivity.this,"Please Write your name",Toast.LENGTH_SHORT).show();
        }
        if (emailPattern.matches(mail)){
            Toast.makeText(RegistationActivity.this,"Please enter valid E mail",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phone)||phone.length() < 6 || phone.length() > 13){
            Toast.makeText(RegistationActivity.this,"Please input valid number",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password)||password.length() < 6 || password.length() > 8)
        {
            Toast.makeText(RegistationActivity.this, "Please created your password 6-8 character", Toast.LENGTH_SHORT).show();
        }
        else{

            loading.setTitle("Credted Account Successfully");
            loading.setMessage("Please wait");
            loading.setCanceledOnTouchOutside(false);
            loading.show();
            validphonenumber(name,mail,phone,password);
        }
    }
    private void validphonenumber(final String name,final String mail, final String phone, final String password) {

        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child(option).child(phone).exists()))
                {
                    HashMap<String,Object> userdataMap =new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("mail",mail);
                    userdataMap.put("password",password);
                    userdataMap.put("name",name);
                    Rootref.child(option).child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegistationActivity.this,"Congratulation,Your registration is successful",Toast.LENGTH_SHORT).show();
                                        loading.dismiss();
                                        Intent intent=new Intent(RegistationActivity.this,LoginActivity.class);
                                        intent.putExtra("option",option);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(RegistationActivity.this,"Network error,Please try again",Toast.LENGTH_SHORT).show();
                                        loading.dismiss();
                                    }
                                }
                            });
                }


                else{
                    Toast.makeText(RegistationActivity.this,"this"+phone+"allready register",Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    Toast.makeText(RegistationActivity.this,"Please try another number",Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(RegistationActivity.this, MainActivity.class);
//                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
