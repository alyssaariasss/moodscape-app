package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class menu extends AppCompatActivity {

    TextView Greetings;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id;
    User userprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;
        id = user.getUid();
        reference = database.getReference().child("users").child(id);

        Greetings = findViewById(R.id.Hello_User);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userprofile = snapshot.getValue(User.class);
                assert userprofile != null;
                Greetings.setText(String.format("Hello, %s!", userprofile.getUserFirstName()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(menu.this, "Something is wrong.", Toast.LENGTH_SHORT).show();
            }
        });

        
        // Main Menu Buttons

        Button btn_mood = findViewById(R.id.mood_button);

        btn_mood.setOnClickListener(v -> {

            Intent intent = new Intent(this, moods.class);
            startActivity(intent);
        });

        Button btn_goals = findViewById(R.id.goals_button);

        btn_goals.setOnClickListener(v -> {

            Intent intent = new Intent(this, goals.class);
            startActivity(intent);
        });

        Button btn_meditate = findViewById(R.id.meditate_button);

        btn_meditate.setOnClickListener(v -> {

            Intent intent = new Intent(this, Meditation.class);
            startActivity(intent);
        });

        Button btn_summary = findViewById(R.id.progress_button);

        btn_summary.setOnClickListener(v -> {

            Intent intent = new Intent(this, summary.class);
            startActivity(intent);
        });

        // NavBar Buttons

        ImageButton btn_home = findViewById(R.id.nav_home);

        btn_home.setOnClickListener(v -> {

            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        });

        ImageButton btn_info = findViewById(R.id.nav_about_mood);

        btn_info.setOnClickListener(v -> {

            Intent intent = new Intent(this, Meditation.class);
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
}