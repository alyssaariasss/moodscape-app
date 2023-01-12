package com.example.puhon_sample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class questions extends AppCompatActivity {
    Button BackBtn1, NextBtn1;
    EditText Question1;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        Question1 = findViewById(R.id.question1);
        BackBtn1 = findViewById(R.id.backbtn1);
        NextBtn1 = findViewById(R.id.nextbtn1);

        sharedPreferences = getApplicationContext().getSharedPreferences("UserAnswers", Context.MODE_PRIVATE);
        DisplayText();

        //next and back buttons
        NextBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String question1 = Question1.getText().toString();

                if (TextUtils.isEmpty(question1)) {
                    Question1.setError("Input is required");
                    return;
                } else {
                    Intent intent = new Intent(questions.this, question2.class);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Answer1", question1);
                    editor.apply();

                    startActivity(intent);
                    finish();
                }
            }
        });

        BackBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(questions.this, BreakScreen1.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void DisplayText() {
        String answer1 = sharedPreferences.getString("Answer1", "");

        Question1.setText(answer1);
    }
}