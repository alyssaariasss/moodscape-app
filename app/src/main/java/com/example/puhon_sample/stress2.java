package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class stress2 extends AppCompatActivity {

    Button stress2DoneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress2);

        stress2DoneBtn = (Button) findViewById(R.id.stress2DoneBtn);

        stress2DoneBtn.setOnClickListener(view -> {
            Intent intent = new Intent(stress2.this, Meditation.class);
            startActivity(intent);
        });


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
    }
}