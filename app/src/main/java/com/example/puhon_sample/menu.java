package com.example.puhon_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);

        Button btn_mood = findViewById(R.id.btn_mood);

        btn_mood.setOnClickListener(v -> {

            Intent intent = new Intent(this, moods.class);
            startActivity(intent);
        });

        ImageButton btn_home = findViewById(R.id.image_btn_home);

        btn_home.setOnClickListener(v -> {

            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        });

        ImageButton btn_settings = findViewById(R.id.image_btn_settings);

        btn_settings.setOnClickListener(v -> {

            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
        });

        Button btn_summary = findViewById(R.id.btn_view_summary);

        btn_summary.setOnClickListener(v -> {

            Intent intent = new Intent(this, retrieveMood.class);
            startActivity(intent);
        });


    }
}