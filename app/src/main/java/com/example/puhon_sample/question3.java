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

public class question3 extends AppCompatActivity {
    Button BackBtn3, NextBtn3;
    EditText Question3;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        Question3 = findViewById(R.id.question3);
        BackBtn3 = findViewById(R.id.backbtn3);
        NextBtn3 = findViewById(R.id.nextbtn3);

        sharedPreferences = getApplicationContext().getSharedPreferences("UserAnswers", Context.MODE_PRIVATE);
        DisplayText();

        //next and back buttons
        NextBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String question3 = Question3.getText().toString();

                if(TextUtils.isEmpty(question3)){
                    Question3.setError("Input is required");
                    return;
                }
                else{
                    Intent intent = new Intent(question3.this, question4.class);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Answer3", question3);
                    editor.apply();

                    startActivity(intent);
                    finish();
                }
            }
        });

        BackBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(question3.this, question2.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void DisplayText() {
        String answer3 = sharedPreferences.getString("Answer3", "");

        Question3.setText(answer3);
    }
}