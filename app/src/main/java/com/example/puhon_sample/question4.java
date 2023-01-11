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
import android.widget.TextView;

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
    TextView Question4Rate;
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
        Question4Rate = findViewById(R.id.question4_rate);
        BackBtn4 = findViewById(R.id.backbtn4);
        NextBtn4 = findViewById(R.id.nextbtn4);

        Question4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Question4Rate.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


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
                int question4 = Question4.getProgress();

                Intent intent8 = getIntent();
                String question1 = intent8.getStringExtra("QUESTION1");
                String question2_1 = intent8.getStringExtra("QUESTION2_1");
                String question2_2 = intent8.getStringExtra("QUESTION2_2");
                String question3 = intent8.getStringExtra("QUESTION3");
                String question5 = intent8.getStringExtra("QUESTION5");

                Intent intent7 = new Intent(question4.this, question3.class);
                intent7.putExtra("QUESTION1", question1);
                intent7.putExtra("QUESTION2_1", question2_1);
                intent7.putExtra("QUESTION2_2", question2_2);
                intent7.putExtra("QUESTION3", question3);
                intent7.putExtra("QUESTION4", question4);
                intent7.putExtra("QUESTION5", question5);

                startActivity(intent7);
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