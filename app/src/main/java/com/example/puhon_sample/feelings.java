package com.example.puhon_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class feelings extends AppCompatActivity {

    UserFeelings UserFeeling;
    Button mSaveFeelingsBtn;
    EditText mFeelingsTxt;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_feelings);

        mFeelingsTxt = findViewById(R.id.feelings_txt);
        mSaveFeelingsBtn = findViewById(R.id.btn_confirm);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        id = user.getUid();

        reference = database.getInstance().getReference().child("users").child(id).child("UserFeelings");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    i = (int) dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                /// try

            }
        });

        mSaveFeelingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FeelingsTxt = mFeelingsTxt.getText().toString();

                if(!TextUtils.isEmpty(FeelingsTxt)){
                    UserFeeling = new UserFeelings(FeelingsTxt);
                    reference.child(String.valueOf(i + 1)).setValue(UserFeeling);
                    finish();
                    startActivity(new Intent(getApplicationContext(), questions.class));
                }
                else{
                    mFeelingsTxt.setError("Input is Required");
                }
            }
        });

    }
}