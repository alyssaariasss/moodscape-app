package com.example.puhon_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class register extends AppCompatActivity {

    EditText mFirstName, mLastName, mEmail, mAge;
    TextInputEditText mPassword;
    Button mSignUpBtn;
    FirebaseAuth fAuth;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirstName = findViewById(R.id.FirstName);
        mLastName = findViewById(R.id.LastName);
        mEmail = findViewById(R.id.Email1);
        mPassword = findViewById(R.id.Password1);
        mAge = findViewById(R.id.Age);
        mSignUpBtn = findViewById(R.id.SignUpBtn);

        fAuth = FirebaseAuth.getInstance();

        mSignUpBtn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = Objects.requireNonNull(mPassword.getText()).toString();
            String fname = mFirstName.getText().toString().trim();
            String lname = mLastName.getText().toString().trim();
            String age = mAge.getText().toString().trim();

            if(TextUtils.isEmpty(fname)) {
                mEmail.setError("First name is required.");
                return;
            }
            if(TextUtils.isEmpty(lname)) {
                mEmail.setError("Last name is required.");
                return;
            }
            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email is required.");
                return;
            }
            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password is required.");
                return;
            }
            if(password.length() < 10){
                mPassword.setError("Password must be at least 10 characters.");
                return;
            }

            // register the user in firebase
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){

                    FirebaseUser user = fAuth.getCurrentUser();
                    assert user != null;
                    id = user.getUid();
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(id);

                    User userdata = new User(fname,lname,age,email,password);
                    databaseRef.setValue(userdata).addOnCompleteListener(task1 -> {

                        Toast.makeText(this, "User created.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),login.class));
                        finish();

                    });
                }
                else {
                    Toast.makeText(register.this, "Error!" + Objects.requireNonNull(task.getException()). getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}