package com.example.puhon_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MCAnxiety2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcanxiety2);


        Button mcButtonDone = findViewById(R.id.mc_doneBtn1);

        mcButtonDone.setOnClickListener(v -> {

            Intent intent = new Intent(this, fragment_anxiety.class);
            startActivity(intent);
        });

    }
}