package com.b2m.driverlagbe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2m.driverlagbe.Basic.EmptyNotification;
import com.b2m.driverlagbe.Driver.DriverData;
import com.b2m.driverlagbe.Driver.DriverDetailsActivity;
import com.b2m.driverlagbe.Driver.DriverViewHolder;
import com.b2m.driverlagbe.Driver.Driver_Model;
import com.b2m.driverlagbe.LogInAndRes.LoginActivity;
import com.b2m.driverlagbe.Model.Prevalent;
import com.b2m.driverlagbe.User.SettinsActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;
    private DatabaseReference driverData;
    RecyclerView recyclerView;

    private DatabaseReference productRef;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Paper.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Driver Account");
        productRef= FirebaseDatabase.getInstance().getReference().child("Driver Info");
        Paper.init(this);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout2);
        NavigationView navigationView = findViewById(R.id.nav_view2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //navigationView.setItemIconTintList(null);
        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.toolbar_n, getTheme());
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

        try {
            View headerView=navigationView.getHeaderView(0);
            TextView usernameTextView=headerView.findViewById(R.id.user_name);
            CircleImageView profileImageView=headerView.findViewById(R.id.user_profile_image);
            usernameTextView.setText(Prevalent.currentOnlineUssers.getName());
            Picasso.get().load(Prevalent.currentOnlineUssers.getImage()).placeholder(R.drawable.profile).into(profileImageView);
        }catch (Exception e){
            e.printStackTrace();
        }

        recyclerView=findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Driver_Model> options=new FirebaseRecyclerOptions.Builder<Driver_Model>()
                .setQuery(productRef,Driver_Model.class).build();

        FirebaseRecyclerAdapter<Driver_Model, DriverViewHolder> adapter=new FirebaseRecyclerAdapter<Driver_Model, DriverViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DriverViewHolder drivertViewHolder, int i, @NonNull final Driver_Model driver_model) {

                drivertViewHolder.name.setText(driver_model.getDriverName());
                drivertViewHolder.exprence.setText(driver_model.getDriverExperiense());
                drivertViewHolder.phoneNumber.setText(driver_model.getDriverNumber());
                //drivertViewHolder.lisence.setText(driver_model.getDriverLinsence());

                Picasso.get().load(driver_model.getDriverImage()).into(drivertViewHolder.driverImage);
                drivertViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity2.this, DriverDetailsActivity.class);
                        intent.putExtra("pid",driver_model.getPid());
                        startActivity(intent);


                    }
                });
            }



            @NonNull
            @Override
            public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.driver_view_child,viewGroup,false);
                DriverViewHolder holder=new DriverViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.driverEditProfileId) {
            Intent intent=new Intent(MainActivity2.this, DriverData.class);
            startActivity(intent);

        }
        else if (id == R.id.driverNotificationId) {
            Intent intent =new Intent(MainActivity2.this, EmptyNotification.class);
            startActivity(intent);

        }
        else if(id==R.id.driverlogoutId){
            Paper.book().destroy();
            Intent intent=new Intent(MainActivity2.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if(id==R.id.driverexitId){
            finish();
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
    //long back_pressed;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity2.super.onBackPressed();
                    }
                }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.logoutId){
            Paper.book().destroy();
            Intent intent=new Intent(MainActivity2.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
        if (item.getItemId()==R.id.exitId){
            finish();
            System.exit(0);

        }

        return false;
    }
}


