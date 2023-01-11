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

public class question3 extends AppCompatActivity {

    UserAnswers Answers;
    Button BackBtn3, NextBtn3;
    EditText Question3;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id, dateToday;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        Question3 = findViewById(R.id.question3);
        BackBtn3 = findViewById(R.id.backbtn3);
        NextBtn3 = findViewById(R.id.nextbtn3);


        //next and back buttons
        NextBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String question3 = Question3.getText().toString();

                if(TextUtils.isEmpty(question3)){
                    Question3.setError("Input is required");
                    return;
                }
                else{
                    Intent intent1 = getIntent();
                    String question1 = intent1.getStringExtra("QUESTION1");
                    String question2_1 = intent1.getStringExtra("QUESTION2_1");
                    String question2_2 = intent1.getStringExtra("QUESTION2_2");
                    Intent intent2 = new Intent(question3.this, question4.class);
                    intent2.putExtra("QUESTION1", question1);
                    intent2.putExtra("QUESTION2_1", question2_1);
                    intent2.putExtra("QUESTION2_2", question2_2);
                    intent2.putExtra("QUESTION3", question3);
                    startActivity(intent2);
                    finish();
                }
            }
        });

        BackBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question3 = Question3.getText().toString();

                Intent intent7 = getIntent();
                String question1 = intent7.getStringExtra("QUESTION1");
                String question2_1 = intent7.getStringExtra("QUESTION2_1");
                String question2_2 = intent7.getStringExtra("QUESTION2_2");
                String question4 = intent7.getStringExtra("QUESTION4");
                String question5 = intent7.getStringExtra("QUESTION5");

                Intent intent6 = new Intent(question3.this, question2.class);
                intent7.putExtra("QUESTION1", question1);
                intent7.putExtra("QUESTION2_1", question2_1);
                intent7.putExtra("QUESTION2_2", question2_2);
                intent7.putExtra("QUESTION3", question3);
                intent7.putExtra("QUESTION4", question4);
                intent7.putExtra("QUESTION5", question5);

                startActivity(intent6);
                finish();
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