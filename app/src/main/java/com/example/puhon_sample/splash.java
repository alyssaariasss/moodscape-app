package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        int SPLASH_SCREEN = 6000;
        new Handler().postDelayed(() -> {
            if (mAuth.getCurrentUser() != null) {
                Intent intent= new Intent(splash.this, menu.class);
                startActivity(intent);
            } else {
                Intent intent= new Intent(splash.this, Onboarding.class);
                startActivity(intent);
            }
            finish();
        }, SPLASH_SCREEN);

    }
}