package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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
    CheckBox checkTOS;
    ProgressBar progressBar;

    MaterialAlertDialogBuilder materialAlertDialogBuilder;

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
        checkTOS = findViewById(R.id.checkTOS);
        mSignUpBtn = findViewById(R.id.SignUpBtn);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);

        fAuth = FirebaseAuth.getInstance();

        checkTOS.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                materialAlertDialogBuilder.setTitle("Terms and Service");
                materialAlertDialogBuilder.setMessage(HtmlCompat.fromHtml(getString(R.string.terms_of_service), HtmlCompat.FROM_HTML_MODE_LEGACY));
                materialAlertDialogBuilder.setPositiveButton("Accept", (dialogInterface, i) -> {
                    checkTOS.setChecked(true);
                    dialogInterface.dismiss();
                });

                materialAlertDialogBuilder.setNegativeButton("Decline", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    checkTOS.setChecked(false);
                });

                materialAlertDialogBuilder.show();
            }
        });

        mSignUpBtn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = Objects.requireNonNull(mPassword.getText()).toString();
            String fname = mFirstName.getText().toString().trim();
            String lname = mLastName.getText().toString().trim();
            String age = mAge.getText().toString().trim();

            if (TextUtils.isEmpty(fname)) {
                mFirstName.setError("First name is required.");
                return;
            }
            if (TextUtils.isEmpty(lname)) {
                mLastName.setError("Last name is required.");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is required.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is required.");
                return;
            }
            if (password.length() < 10) {
                mPassword.setError("Password must be at least 10 characters.");
                return;
            }
            if (!checkTOS.isChecked()) {
                checkTOS.setError("You must accept the terms of service to register an account.");
            }

            progressBar.setVisibility(View.VISIBLE);

            // register the user in firebase
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {

                if (task.isSuccessful()){

                    FirebaseUser user = fAuth.getCurrentUser();
                    assert user != null;
                    id = user.getUid();
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(id);

                    User userdata = new User(fname,lname,age,email,password);
                    databaseRef.setValue(userdata).addOnCompleteListener(task1 -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "User created.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),login.class));
                        finish();

                    });
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(register.this, "Error! " + Objects.requireNonNull(task.getException()). getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}