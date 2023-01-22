package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class BreakScreen1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_screen1);

        // NavBar Buttons

        ImageButton btn_home = findViewById(R.id.nav_home);
        ImageButton btn_goals = findViewById(R.id.nav_goal);
        ImageButton btn_progress = findViewById(R.id.nav_progress);
        ImageButton btn_settings = findViewById(R.id.nav_settings);


        btn_home.setOnClickListener(v -> {

            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        });

        btn_goals.setOnClickListener(v -> {

            Intent intent = new Intent(this, goals.class);
            startActivity(intent);
        });

        btn_progress.setOnClickListener(v -> {

            Intent intent = new Intent(this, summary.class);
            startActivity(intent);
        });

        btn_settings.setOnClickListener(v -> {

            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
        });

        // Back & Continue Button

        Button btn_back = findViewById(R.id.backButton);

        btn_back.setOnClickListener(v -> {

            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        });

        Button btn_cont = findViewById(R.id.continueButton);

        btn_cont.setOnClickListener(v -> {

            Intent intent = new Intent(this, questions.class);
            startActivity(intent);
        });
    }
}