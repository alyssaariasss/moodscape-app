package com.example.puhon_sample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class login extends AppCompatActivity {
    EditText mEmail1;
    TextInputEditText mPassword1;
    Button mLogInBtn,mForgotPass;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail1 = findViewById(R.id.Email1);
        mPassword1 = findViewById(R.id.Password1);
        fAuth = FirebaseAuth.getInstance();
        mLogInBtn = findViewById(R.id.LogInBtn);
        mForgotPass = findViewById(R.id.ForgotPass);
        progressBar = findViewById(R.id.progressBar);

        mLogInBtn.setOnClickListener(v -> {
            String email = mEmail1.getText().toString().trim();
            String password = Objects.requireNonNull(mPassword1.getText()).toString().trim();

            if(TextUtils.isEmpty(email)){
                mEmail1.setError("Email is required.");
                return;
            }

            if(TextUtils.isEmpty(password)){
                mPassword1.setError("Password is required.");
                return;
            }

            if(password.length() < 10){
                mPassword1.setError("Password must be at least 10 characters.");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            // authenticate the user

            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(login.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), introduction.class));

                }
                else {
                    Toast.makeText(login.this, "Error! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            });
        });

        mForgotPass.setOnClickListener(v -> {

            EditText resetMail = new EditText(v.getContext());
            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset password?");
            passwordResetDialog.setMessage("Enter your registered email to receive reset link.");
            passwordResetDialog.setView(resetMail);

            passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                // extract the email and send reset link

                String mail = resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid -> Toast.makeText(login.this, "We have e-mailed your password reset link!", Toast.LENGTH_SHORT).show()) .addOnFailureListener(e -> Toast.makeText(login.this, "Error! reset link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show());


            });

            passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
                // close the dialog
            });

            passwordResetDialog.create().show();
        });
    }
}