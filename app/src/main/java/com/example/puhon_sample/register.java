package com.example.puhon_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    EditText mName, mEmail, mPassword, mAge, mPronouns;
    Button mSignUpBtn;
    FirebaseAuth fAuth;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        mName = findViewById(R.id.Name);
        mEmail = findViewById(R.id.Email1);
        mPassword = findViewById(R.id.Password1);
        mAge = findViewById(R.id.Age);
        mPronouns = findViewById(R.id.Pronouns);
        mSignUpBtn = findViewById(R.id.SignUpBtn);

        fAuth = FirebaseAuth.getInstance();


        mSignUpBtn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String name = mName.getText().toString().trim();
            String age = mAge.getText().toString().trim();
            String pronouns = mPronouns.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email is Required");
                return;
            }
            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password is Required.");
                return;
            }
            if(TextUtils.isEmpty(name)) {
                mEmail.setError("Name is Required");
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
                    id = user.getUid();
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(id);

                    User userdata = new User(name,age, pronouns,email,password);
                    databaseRef.setValue(userdata).addOnCompleteListener(task1 -> {

                        Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),login.class));
                        finish();

                    });


                }
                else {
                    Toast.makeText(register.this, "Error!" + task.getException(). getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        });
    }
}