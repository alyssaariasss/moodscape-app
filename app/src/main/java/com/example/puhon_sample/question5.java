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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        Question5 = findViewById(R.id.question5_seekBar);
        Question5Rate = findViewById(R.id.question5_rate);
        BackBtn5 = findViewById(R.id.backbtn5);
        NextBtn5 = findViewById(R.id.nextbtn5);

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

                Intent intent3 = getIntent();
                String question1 = intent3.getStringExtra("QUESTION1");
                String question2_1 = intent3.getStringExtra("QUESTION2_1");
                String question2_2 = intent3.getStringExtra("QUESTION2_2");
                String question3 = intent3.getStringExtra("QUESTION3");
                Integer question4 = intent3.getIntExtra("QUESTION4", 1);
                Answers = new UserAnswers(question1, question2_1, question2_2, question3, question4, question5);
                ShowDate();
                Answers.setDate(dateToday);
                reference.child(String.valueOf(i + 1)).setValue(Answers);
                startActivity(new Intent(getApplicationContext(), menu.class));
                finish();
            }
        });

        BackBtn5.setOnClickListener(new View.OnClickListener() {
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