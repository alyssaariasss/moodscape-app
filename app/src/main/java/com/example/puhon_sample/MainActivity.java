package com.example.puhon_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button1);

        button.setOnClickListener(v -> {

            Intent intent = new Intent(this, register.class);
            startActivity(intent);
        });

        Button button2 = findViewById(R.id.button2);

        button2.setOnClickListener(v -> {

            Intent intent = new Intent(this, login.class);
            startActivity(intent);
        });
    }
}