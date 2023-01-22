package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

        CardView menu1 = findViewById(R.id.for_moods);
        CardView menu2 = findViewById(R.id.for_goals);
        CardView menu3 = findViewById(R.id.for_meditation);
        CardView menu4 = findViewById(R.id.for_summary);

        menu1.setOnClickListener(v -> {

            Intent intent = new Intent(this, moods.class);
            startActivity(intent);
        });

        menu2.setOnClickListener(v -> {

            Intent intent = new Intent(this, goals.class);
            startActivity(intent);
        });


        menu3.setOnClickListener(v -> {

            Intent intent = new Intent(this, Meditation.class);
            startActivity(intent);
        });


        menu4.setOnClickListener(v -> {

            Intent intent = new Intent(this, summary.class);
            startActivity(intent);
        });

        // NavBar Buttons

        ImageButton btn_goals = findViewById(R.id.nav_goal);
        ImageButton btn_progress = findViewById(R.id.nav_progress);
        ImageButton btn_settings = findViewById(R.id.nav_settings);


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
}