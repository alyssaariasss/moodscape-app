package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;
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


public class moods extends AppCompatActivity {
    String mood, id, dateToday;
    Slider moodSlider;
    ImageView moodView;
    Button choiceButton;

    UserMoods userMoods;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moods);

        moodSlider = findViewById(R.id.slider);
        moodView = findViewById(R.id.moodView);
        choiceButton = findViewById(R.id.ChoiceButton);

        SetDisplayImage();

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;
        id = user.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("users").child(id).child("UserMoods");

        // Gets children count of UserMoods
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    i = (int) snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(moods.this, "An error has occurred while processing your request.", Toast.LENGTH_SHORT).show();
            }
        });

        choiceButton.setOnClickListener(view -> {
            userMoods = new UserMoods();
            ShowDate();

            // Stores mood and date to db
            if (mood != null) {
                userMoods.setMood(mood);
            } else {
                userMoods.setMood("Angry");
            }
            userMoods.setDate(dateToday);
            reference.child(String.valueOf(i+1)).setValue(userMoods);

            Intent intent = new Intent(this, BreakScreen1.class);
            startActivity(intent);
        });

        // NavBar Buttons

        ImageButton btn_home = findViewById(R.id.nav_home);
        ImageButton btn_goals = findViewById(R.id.nav_goal);
        ImageButton btn_progress = findViewById(R.id.nav_progress);
        ImageButton btn_settings = findViewById(R.id.nav_settings);


        btn_home.setOnClickListener(v -> {

            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        });

        btn_goals.setOnClickListener(v -> {

            Intent intent = new Intent(this, goals.class);
            startActivity(intent);
        });

        btn_progress.setOnClickListener(v -> {

            Intent intent = new Intent(this, summary.class);
            startActivity(intent);
        });

        btn_settings.setOnClickListener(v -> {

            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
        });

    }

    // Changes display image and sets mood
    private void SetDisplayImage() {
        moodSlider.addOnChangeListener((slider, value, fromUser) -> {
            if (value == 0) {
                moodView.setImageResource(R.drawable.angry_icon);
                mood = "Angry";
            } else if (value == 1) {
                moodView.setImageResource(R.drawable.sad_icon);
                mood = "Sad";
            } else if (value == 2) {
                moodView.setImageResource(R.drawable.disgusted_icon);
                mood = "Disgusted";
            } else if (value == 3) {
                moodView.setImageResource(R.drawable.fearful_icon);
                mood = "Fearful";
            } else if (value == 4) {
                moodView.setImageResource(R.drawable.surprised_icon);
                mood = "Surprised";
            } else if (value == 5) {
                moodView.setImageResource(R.drawable.happy_icon);
                mood = "Happy";
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