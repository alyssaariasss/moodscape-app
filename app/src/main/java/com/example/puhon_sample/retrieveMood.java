package com.example.puhon_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class retrieveMood extends AppCompatActivity {

    ListView mUserMoodData;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    UserMoods userMoods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_retrieve_mood);

        Button next_btn = findViewById(R.id.btn_next_mood);


        userMoods = new UserMoods();
        mUserMoodData = (ListView) findViewById(R.id.UserMoodData);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        id = user.getUid();
        reference = database.getInstance().getReference().child("users").child(id).child("UserMoods");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.list,R.id.usermoodlist,list);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for (DataSnapshot ds : datasnapshot.getChildren()) {

                    userMoods = ds.getValue(UserMoods.class);
                    list.add(userMoods.getMood());

                }
                mUserMoodData.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        next_btn.setOnClickListener(v -> {

            Intent intent = new Intent(this, retrieveFeelings.class);
            startActivity(intent);

        });



    }
}