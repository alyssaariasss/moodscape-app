package com.example.puhon_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class questions extends AppCompatActivity {

    UserAnswers UserAnswer;
    Button mSaveQuestionsBtn;
    EditText mWhereTxt, mWhoTxt, mDoTxt, mReasonTxt, mWhatTxt;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_questions);

        mWhereTxt = findViewById(R.id.question1);
        mWhoTxt = findViewById(R.id.question2);
        mDoTxt = findViewById(R.id.question3);
        mReasonTxt = findViewById(R.id.question4);
        mWhatTxt = findViewById(R.id.question5);
        mSaveQuestionsBtn = findViewById(R.id.confirm_q_btn);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        id = user.getUid();

        reference = database.getInstance().getReference().child("users").child(id).child("Answers");

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

        mSaveQuestionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String WhereTxt = mWhereTxt.getText().toString();
                String WhoTxt = mWhoTxt.getText().toString();
                String DoTxt = mDoTxt.getText().toString();
                String ReasonTxt = mReasonTxt.getText().toString();
                String WhatTxt = mWhatTxt.getText().toString();

                if(TextUtils.isEmpty(WhereTxt)){
                    mWhereTxt.setError("Input is required");
                    return;
                }
                if(TextUtils.isEmpty(WhoTxt)){
                    mWhoTxt.setError("Input is required");
                    return;
                }
                if(TextUtils.isEmpty(DoTxt)){
                    mDoTxt.setError("Input is required");
                    return;
                }
                if(TextUtils.isEmpty(ReasonTxt)){
                    mReasonTxt.setError("Input is required");
                    return;
                }
                if(TextUtils.isEmpty(WhatTxt)){
                    mWhoTxt.setError("Input is required");
                    return;
                }
                else{
                    UserAnswer = new UserAnswers(WhereTxt, WhoTxt, DoTxt, ReasonTxt, WhatTxt);
                    reference.child(String.valueOf(i + 1)).setValue(UserAnswer);
                    startActivity(new Intent(getApplicationContext(), menu.class));
                    finish();
                }

            }
        });

    }
}