package com.b2m.driverlagbe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Job_request_Activity extends AppCompatActivity {
    ListView listView;
    int []DriverImages;
    String []DriverName;
    String []Money;
    String []Distence ;
    String []Pickup_point;
    String []Dropup_point ;
   Button Accept, Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_request_);
        listView=findViewById(R.id.listViewJOb_RequestId);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String player_list = DriverName[position];
                Toast.makeText(Job_request_Activity.this,"Hi", Toast.LENGTH_SHORT).show();


            }

        });
}
}
