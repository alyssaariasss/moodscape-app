package com.example.puhon_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class question4 extends AppCompatActivity {

    UserAnswers Answers;
    Button BackBtn4, NextBtn4;
    SeekBar Question4;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id, dateToday;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);

        Question4 = findViewById(R.id.question4_seekBar);
        BackBtn4 = findViewById(R.id.backbtn4);
        NextBtn4 = findViewById(R.id.nextbtn4);


        //next and back buttons
        NextBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int question4 = Question4.getProgress();

                Intent intent2 = getIntent();
                String question1 = intent2.getStringExtra("QUESTION1");
                String question2_1 = intent2.getStringExtra("QUESTION2_1");
                String question2_2 = intent2.getStringExtra("QUESTION2_2");
                String question3 = intent2.getStringExtra("QUESTION3");
                Intent intent3 = new Intent(question4.this, question5.class);
                intent3.putExtra("QUESTION1", question1);
                intent3.putExtra("QUESTION2_1", question2_1);
                intent3.putExtra("QUESTION2_2", question2_2);
                intent3.putExtra("QUESTION3", question3);
                intent3.putExtra("QUESTION4", question4);
                startActivity(intent3);
                finish();
            }
        });

        BackBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), menu.class));
            }
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

            Intent intent = new Intent(this, summary.class);
            startActivity(intent);
        });

        ImageButton btn_settings = findViewById(R.id.nav_settings);

        btn_settings.setOnClickListener(v -> {

            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
        });

    }

    // Gets current date
    private void ShowDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.US);
        dateToday = dateFormat.format(today);
    }
}