package com.example.puhon_sample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class question5 extends AppCompatActivity {

    UserAnswers Answers;
    TextView Question5Rate;
    Button BackBtn5, NextBtn5;
    SeekBar Question5;
    FirebaseAuth fAuth;
    FirebaseDatabase database;  
    DatabaseReference reference;
    String id, dateToday;
    int i = 0;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        Question5 = findViewById(R.id.question5_seekBar);
        Question5Rate = findViewById(R.id.question5_rate);
        BackBtn5 = findViewById(R.id.backbtn5);
        NextBtn5 = findViewById(R.id.nextbtn5);

        sharedPreferences = getApplicationContext().getSharedPreferences("UserAnswers", Context.MODE_PRIVATE);
        DisplayText();

        Question5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Question5Rate.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        id = user.getUid();

        reference = database.getInstance().getReference().child("users").child(id).child("UserQuestions");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    i = (int) dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ///

            }
        });


        //next and back buttons
        NextBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int question5 = Question5.getProgress();

                String question1 = sharedPreferences.getString("Answer1", "");
                String question2_1 = sharedPreferences.getString("Answer2_1", "");
                String question2_2 = sharedPreferences.getString("Answer2_2", "");
                String question3 = sharedPreferences.getString("Answer3", "");
                Integer question4 = sharedPreferences.getInt("Answer4", 0);

                Answers = new UserAnswers(question1, question2_1, question2_2, question3, question4, question5);
                ShowDate();
                Answers.setDate(dateToday);
                reference.child(String.valueOf(i + 1)).setValue(Answers);

                // Removes all values for next input
                sharedPreferences.edit().clear().apply();

                startActivity(new Intent(getApplicationContext(), BreakScreen2.class));
                finish();
            }
        });

        BackBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(question5.this, question4.class);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("Answer5", Question5.getProgress());
                editor.apply();

                startActivity(intent);
                finish();
            }
        });
    }

    private void DisplayText() {
        int answer5 = sharedPreferences.getInt("Answer5",0);

        Question5Rate.setText("" + answer5);
        Question5.setProgress(answer5);
    }

    // Gets current date
    private void ShowDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.US);
        dateToday = dateFormat.format(today);
    }
}