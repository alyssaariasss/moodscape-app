package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void onBackPressed() { }
}