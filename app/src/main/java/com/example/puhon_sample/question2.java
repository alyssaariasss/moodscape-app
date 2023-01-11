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

public class question2 extends AppCompatActivity {

    UserAnswers AnswerQuestion2;
    Button BackBtn2, NextBtn2;
    EditText Question2_1,Question2_2;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id, dateToday;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions2);

        Question2_1 = findViewById(R.id.question2_1);
        Question2_2 = findViewById(R.id.question2_2);
        BackBtn2 = findViewById(R.id.backbtn2);
        NextBtn2 = findViewById(R.id.nextbtn2);


        //next and back buttons
        NextBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String question2_1 = Question2_1.getText().toString();
                String question2_2 = Question2_2.getText().toString();


                if(TextUtils.isEmpty(question2_1)){
                    Question2_1.setError("Input is required");
                    return;
                }
                if(TextUtils.isEmpty(question2_2)) {
                    Question2_2.setError("Input is required");
                    return;
                }
                else{
                    Intent intent = getIntent();
                    String question1 = intent.getStringExtra("QUESTION1");
                    Intent intent1 = new Intent(question2.this, question3.class);
                    intent1.putExtra("QUESTION1", question1);
                    intent1.putExtra("QUESTION2_1", question2_1);
                    intent1.putExtra("QUESTION2_2", question2_2);
                    startActivity(intent1);
                    finish();
                }
            }
        });

        BackBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question2_1 = Question2_1.getText().toString();
                String question2_2 = Question2_2.getText().toString();

                Intent intent6 = getIntent();
                String question1 = intent6.getStringExtra("QUESTION1");
                String question3 = intent6.getStringExtra("QUESTION3");
                String question4 = intent6.getStringExtra("QUESTION4");
                String question5 = intent6.getStringExtra("QUESTION5");

                Intent intent5 = new Intent(question2.this, questions.class);
                intent5.putExtra("QUESTION1", question1);
                intent5.putExtra("QUESTION2_1", question2_1);
                intent5.putExtra("QUESTION2_2", question2_2);
                intent5.putExtra("QUESTION3", question3);
                intent5.putExtra("QUESTION4", question4);
                intent5.putExtra("QUESTION5", question5);

                startActivity(intent5);
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