package com.b2m.driverlagbe.Basic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.b2m.driverlagbe.R;

public class SplashScreen extends AppCompatActivity {
    LinearLayout logo;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo=findViewById(R.id.splashScreenLogoId);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.crossfading_animation);
        logo.startAnimation(animation);
        final Handler handler = new Handler();
        // for full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // action bar hide
        getSupportActionBar().hide();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                Intent intent = new Intent(SplashScreen.this, AcceptJob.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}
