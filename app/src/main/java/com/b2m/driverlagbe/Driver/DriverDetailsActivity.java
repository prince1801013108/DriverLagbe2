package com.b2m.driverlagbe.Driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2m.driverlagbe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.OutputStream;

public class DriverDetailsActivity extends AppCompatActivity {
    ImageView detailsImage;
    TextView name,expert,experience,carType,drivingLiscence,mobileNumber;
    private String productID="";
    Button shareBtn;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);
        getSupportActionBar().setTitle("Driver Details");
        //shareBtn=findViewById(R.id.shareButton);
        productID=getIntent().getStringExtra("pid");
        detailsImage=findViewById(R.id.driverImageDetailsId);
        name=findViewById(R.id.driverNameIdDetails);
        expert=findViewById(R.id.driverExpertIdDetails);
        experience=findViewById(R.id.driverExperinceIdDetails);
        carType=findViewById(R.id.driverCarTypeIdDetails);
        drivingLiscence=findViewById(R.id.driverLicenceIdDetails);
        mobileNumber=findViewById(R.id.driverphoneNumberIdDetails);
        getProductDetails(productID);
//        shareBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                share();
//            }
//        });
    }

    private void getProductDetails(final String productID) {
        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Driver Info");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists()){
                    Driver_Model products=dataSnapshot.getValue(Driver_Model.class);
                    name.setText(products.getDriverName());
                    expert.setText("Expert In :"+products.getDriverExpert());
                    experience.setText("Experience :"+products.getDriverExperiense());
                    carType.setText("Car Type :"+products.getDriverCarType());
                    drivingLiscence.setText("Driving Licence :"+products.getDriverLinsence());
                    mobileNumber.setText("Mobile No :"+products.getDriverNumber());
                    Picasso.get().load(products.getDriverImage()).into(detailsImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//    public void share(){
//        Bitmap icon = bitmap;
//        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setType("image/jpeg");
//
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "title");
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                values);
//
//
//        OutputStream outstream;
//        try {
//            outstream = getContentResolver().openOutputStream(uri);
//            icon.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
//            outstream.close();
//        } catch (Exception e) {
//            System.err.println(e.toString());
//        }
//
//        share.putExtra(Intent.EXTRA_STREAM, uri);
//        startActivity(Intent.createChooser(share, "Share Image"));
//
//    }

}
