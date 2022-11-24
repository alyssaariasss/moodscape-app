package com.example.puhon_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class retrieveFeelings extends AppCompatActivity {


    ListView mUserFeelingData;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    UserFeelings userFeelings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_retrieve_feelings);

        Button next_btn = findViewById(R.id.btn_next_feeling);


        userFeelings = new UserFeelings();
        mUserFeelingData = (ListView) findViewById(R.id.UserFeelingsData);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        id = user.getUid();
        reference = database.getInstance().getReference().child("users").child(id).child("UserFeelings");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.list,R.id.usermoodlist,list);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for (DataSnapshot ds : datasnapshot.getChildren()) {

                    userFeelings = ds.getValue(UserFeelings.class);
                    list.add(userFeelings.getFeeling());

                }
                mUserFeelingData.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        next_btn.setOnClickListener(v -> {

            Intent intent = new Intent(this, menu.class);
            startActivity(intent);

        });

    }
}