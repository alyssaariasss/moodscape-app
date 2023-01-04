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

public class questions extends AppCompatActivity {

    UserAnswers AnswerQuestion1;
    Button BackBtn1, NextBtn1;
    EditText Question1;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id, dateToday;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        Question1 = findViewById(R.id.question1);
        BackBtn1 = findViewById(R.id.backbtn1);
        NextBtn1 = findViewById(R.id.nextbtn1);


        //next and back buttons
        NextBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String question1 = Question1.getText().toString();

                if (TextUtils.isEmpty(question1)) {
                    Question1.setError("Input is required");
                    return;
                } else {
                    Intent intent = new Intent(questions.this, question2.class);
                    intent.putExtra("QUESTION1", question1);
                    startActivity(intent);
                    finish();
                }
            }
        });

        BackBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BreakScreen1.class));
            }
        });

    }

    // Gets current date
    private void ShowDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.US);
        dateToday = dateFormat.format(today);
    }
}