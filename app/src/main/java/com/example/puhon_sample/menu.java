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
        setContentView(R.layout.activity_menu);

        // Main Menu Buttons

        Button btn_mood = findViewById(R.id.mood_button);

        btn_mood.setOnClickListener(v -> {

            Intent intent = new Intent(this, HappyChoice.class);
            startActivity(intent);
        });

        Button btn_goals = findViewById(R.id.goals_button);

        btn_goals.setOnClickListener(v -> {

            Intent intent = new Intent(this, sample_activity.class);
            startActivity(intent);
        });

        Button btn_meditate = findViewById(R.id.meditate_button);

        btn_meditate.setOnClickListener(v -> {

            Intent intent = new Intent(this, sample_activity.class);
            startActivity(intent);
        });

        Button btn_summary = findViewById(R.id.progress_button);

        btn_summary.setOnClickListener(v -> {

            Intent intent = new Intent(this, sample_activity.class);
            startActivity(intent);
        });

        // NavBar Buttons

        ImageButton btn_home = findViewById(R.id.nav_home);

        btn_home.setOnClickListener(v -> {

            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        });

        ImageButton btn_info = findViewById(R.id.nav_about_mood);

        btn_info.setOnClickListener(v -> {

            Intent intent = new Intent(this, BreakScreen1.class);
            startActivity(intent);
        });

        ImageButton btn_progress = findViewById(R.id.nav_progress);

        btn_progress.setOnClickListener(v -> {

            Intent intent = new Intent(this, sample_activity.class);
            startActivity(intent);
        });

        ImageButton btn_settings = findViewById(R.id.nav_settings);

        btn_settings.setOnClickListener(v -> {

            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
        });

    }
}