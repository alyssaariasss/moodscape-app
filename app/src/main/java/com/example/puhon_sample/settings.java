package com.example.puhon_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class settings extends AppCompatActivity {

    EditText mprofileName, mprofileAge, mprofilePronouns, mprofileEmail, mprofilePassword;
    Button mSaveChanges, Logout;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id;
    User userprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);

            ImageButton btn_back = findViewById(R.id.btn_back);
            mprofileName = findViewById(R.id.profileName);
            mprofileAge = findViewById(R.id.profileAge);
            mprofilePronouns = findViewById(R.id.profilePronouns);
            mprofileEmail = findViewById(R.id.profileEmail);
            mprofilePassword = findViewById(R.id.profilePassword);
            mSaveChanges = findViewById(R.id.btn_save);
            Logout = findViewById(R.id.btn_logout);


            fAuth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            FirebaseUser user = fAuth.getCurrentUser();
            id = user.getUid();
            reference = database.getReference().child("users").child(id);


            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    userprofile = snapshot.getValue(User.class);

                    mprofileName.setText(userprofile.getUserName());
                    mprofileAge.setText(userprofile.getUserAge());
                    mprofilePronouns.setText(userprofile.getUserPronouns());
                    mprofileEmail.setText(userprofile.getUserEmail());
                    mprofilePassword.setText(userprofile.getUserPassword());

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            mSaveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mprofileName.getText().toString().isEmpty() || mprofileAge.getText().toString().isEmpty() ||
                            mprofilePronouns.getText().toString().isEmpty() || mprofileEmail.getText().toString().isEmpty() || mprofilePassword.getText().toString().isEmpty()){
                        Toast.makeText(settings.this, "One or Many fields are empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String email = mprofileEmail.getText().toString();
                    user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Map<String,Object> edited = new HashMap<>();
                            edited.put("userEmail",email);
                            edited.put("userName",mprofileName.getText().toString());
                            edited.put("userAge",mprofileAge.getText().toString());
                            edited.put("userPronouns",mprofilePronouns.getText().toString());
                            reference.updateChildren(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(settings.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),menu.class));
                                    finish();
                                }
                            });
                            Toast.makeText(settings.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(settings.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    String password = mprofilePassword.getText().toString();
                    user.updatePassword(password).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Map<String,Object> edited = new HashMap<>();
                            edited.put("userPassword",password);
                            reference.updateChildren(edited);

                        }
                    });


                }
            });
            Logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fAuth.signOut();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });

            btn_back.setOnClickListener(v -> {

                Intent intent = new Intent(this, menu.class);
                startActivity(intent);

        });

        }
    }



