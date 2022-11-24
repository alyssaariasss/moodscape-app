package com.example.puhon_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class moods extends AppCompatActivity {

    private RadioGroup radioGroup;
    private ImageView mood_image;
    private Integer []photos = { R.drawable.happy, R.drawable.sad, R.drawable.angry, R.drawable.fearful, R.drawable.disgusted, R.drawable.surprised };

    RadioButton mhappy, msad, mangry, mfearful, mdisgusted, msurprised;
    UserMoods UserMood;
    Button msavemoods;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_moods);
        this.mood_image = findViewById(R.id.mood_image);
        this.radioGroup = findViewById(R.id.radioGroup);
        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                mood_image.setImageResource(photos[index]);
            }
        });

        mhappy = findViewById(R.id.rad_btn_happy);
        msad = findViewById(R.id.rad_btn_sad);
        mangry = findViewById(R.id.rad_btn_angry);
        mfearful = findViewById(R.id.rad_btn_fearful);
        mdisgusted = findViewById(R.id.rad_btn_disgusted);
        msurprised = findViewById(R.id.rad_btn_surprised);
        msavemoods = findViewById(R.id.btn_next);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        id = user.getUid();

        reference = database.getInstance().getReference().child("users").child(id).child("UserMoods");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    i = (int) dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        msavemoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Happy = mhappy.getText().toString();
                String Sad = msad.getText().toString();
                String Angry = mangry.getText().toString();
                String Fearful = mfearful.getText().toString();
                String Disgusted = mdisgusted.getText().toString();
                String Surprised = msurprised.getText().toString();

                if (mhappy.isChecked()) {
                    UserMood = new UserMoods();
                    UserMood.setMood(Happy);
                    reference.child(String.valueOf(i + 1)).setValue(UserMood);
                    finish();
                    startActivity(new Intent(getApplicationContext(), feelings.class));
                }
                if (msad.isChecked()) {
                    UserMood = new UserMoods();
                    UserMood.setMood(Sad);
                    reference.child(String.valueOf(i + 1)).setValue(UserMood);
                    finish();
                    startActivity(new Intent(getApplicationContext(), feelings.class));
                }
                if (mangry.isChecked()) {
                    UserMood = new UserMoods();
                    UserMood.setMood(Angry);
                    reference.child(String.valueOf(i + 1)).setValue(UserMood);
                    finish();
                    startActivity(new Intent(getApplicationContext(), feelings.class));
                }
                if (mfearful.isChecked()) {
                    UserMood = new UserMoods();
                    UserMood.setMood(Fearful);
                    reference.child(String.valueOf(i + 1)).setValue(UserMood);
                    finish();
                    startActivity(new Intent(getApplicationContext(), feelings.class));
                }
                if (mdisgusted.isChecked()) {
                    UserMood = new UserMoods();
                    UserMood.setMood(Disgusted);
                    reference.child(String.valueOf(i + 1)).setValue(UserMood);
                    finish();
                    startActivity(new Intent(getApplicationContext(), feelings.class));
                }
                if (msurprised.isChecked()) {
                    UserMood = new UserMoods();
                    UserMood.setMood(Surprised);
                    reference.child(String.valueOf(i + 1)).setValue(UserMood);
                    finish();
                    startActivity(new Intent(getApplicationContext(), feelings.class));
                }
            }
        });


    }
}