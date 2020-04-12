package com.b2m.driverlagbe.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.b2m.driverlagbe.MainActivity;
import com.b2m.driverlagbe.Model.Prevalent;
import com.b2m.driverlagbe.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettinsActivity extends AppCompatActivity {

    private CircleImageView profileimageview;
    private EditText fullname,userPhone,address;
    private TextView profilechangeTextview,cancleTxtbtn,saveTextbtn;
    private Uri imageuri;
    private String myUrl="";
    private UploadTask uploadTask;
    private StorageReference storgeProfilePictureRef;
    //private DatabaseReference driverRef;

    private String checker="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settins);

        //getSupportActionBar().setTitle("User Profile");
        //driverRef= FirebaseDatabase.getInstance().getReference().child("Profile picture");

        storgeProfilePictureRef= FirebaseStorage.getInstance().getReference().child("Profile picture");

        profileimageview=findViewById(R.id.setting_profile_image);
        fullname=findViewById(R.id.setting_full_name);
        userPhone=findViewById(R.id.setting_phone_number);
        address=findViewById(R.id.setting_address);
        profilechangeTextview=findViewById(R.id.profile_image_change_btn);
        cancleTxtbtn=findViewById(R.id.close_setting);
        saveTextbtn=findViewById(R.id.update_setting);

        userInfoDisplay(profileimageview,fullname,userPhone,address);
        cancleTxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveTextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked"))
                {
                    userInfoSaved();
                }else {
                    updateOnlyuserInfo();
                }
            }
        });
        profilechangeTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker="clicked";
                CropImage.activity(imageuri)
                        .setAspectRatio(1,1)
                        .start(SettinsActivity.this);
            }
        });
    }

    private void updateOnlyuserInfo() {

        DatabaseReference Ref= FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String,Object> usermap=new HashMap<>();
        usermap.put("name",fullname.getText().toString());
        usermap.put("address",address.getText().toString());
        usermap.put("phoneOrder",userPhone.getText().toString());
        Ref.child(Prevalent.currentOnlineUssers.getPhone()).updateChildren(usermap);


        startActivity(new Intent(SettinsActivity.this, MainActivity.class));
        Toast.makeText(SettinsActivity.this,"Profile update successfully",Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data !=null)
        {
            CropImage.ActivityResult result= CropImage.getActivityResult(data);


            imageuri=result.getUri();
            profileimageview.setImageURI(imageuri);
        }else {
            Toast.makeText(this,"Error,Try Again",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettinsActivity.this,SettinsActivity.class));
            finish();
        }

    }

    private void userInfoSaved() {

//        if (TextUtils.isEmpty(fullname.getText().toString()))
//        {
//            Toast.makeText(this,"name is empty",Toast.LENGTH_SHORT).show();
//        }else if (TextUtils.isEmpty(userPhone.getText().toString())){
//            Toast.makeText(this,"phone number is empty",Toast.LENGTH_SHORT).show();
//        }else if (TextUtils.isEmpty(address.getText().toString())){
//            Toast.makeText(this,"address is empty",Toast.LENGTH_SHORT).show();
//        }
       /* else*/ if (checker.equals("clicked")){
            uploadImage();
        }

    }

    private void uploadImage() {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Upload Profile");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait ");
        progressDialog.show();
        if (imageuri !=null)
        {
            final StorageReference fileRef=storgeProfilePictureRef
                    .child(Prevalent.currentOnlineUssers.getPhone()+".jpg");
            uploadTask=fileRef.putFile(imageuri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Task<? extends Object> then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri=task.getResult();
                        myUrl= downloadUri != null ? downloadUri.toString() : null;
                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");
                        HashMap<String,Object> usermap=new HashMap<>();
                        usermap.put("name",fullname.getText().toString());
                        usermap.put("address",address.getText().toString());
                        usermap.put("phoneOrder",userPhone.getText().toString());
                        usermap.put("image",myUrl);
                        progressDialog.dismiss();

                        ref.child(Prevalent.currentOnlineUssers.getPhone()).updateChildren(usermap);

                        startActivity(new Intent(SettinsActivity.this,MainActivity.class));
                        Toast.makeText(SettinsActivity.this,"Profile update successfully",Toast.LENGTH_SHORT).show();
                        finish();

                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(SettinsActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }
        else {
            Toast.makeText(this,"profile picture not selected",Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDisplay(final CircleImageView profileimageview, final EditText fullname, final EditText userPhone, final EditText address) {
        DatabaseReference Userref= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUssers.getPhone());
        Userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("image").exists())
                    {
                        String image=dataSnapshot.child("image").getValue().toString();
                        String name=dataSnapshot.child("name").getValue().toString();
                        String phone=dataSnapshot.child("phone").getValue().toString();
                        String addres=dataSnapshot.child("address").getValue().toString();


                        Picasso.get().load(image).into(profileimageview);
                        fullname.setText(name);
                        userPhone.setText(phone);
                        address.setText(addres);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
