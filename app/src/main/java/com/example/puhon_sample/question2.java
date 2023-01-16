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

public class question2 extends AppCompatActivity {
    Button BackBtn2, NextBtn2;
    EditText Question2_1,Question2_2;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions2);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        Question2_1 = findViewById(R.id.question2_1);
        Question2_2 = findViewById(R.id.question2_2);
        BackBtn2 = findViewById(R.id.backbtn2);
        NextBtn2 = findViewById(R.id.nextbtn2);

        sharedPreferences = getApplicationContext().getSharedPreferences("UserAnswers", Context.MODE_PRIVATE);
        DisplayText();

        //next and back buttons
        NextBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String question2_1 = Question2_1.getText().toString();
                String question2_2 = Question2_2.getText().toString();


                if(TextUtils.isEmpty(question2_1)){
                    Question2_1.setError("Input is required");
                    return;
                }
                if(TextUtils.isEmpty(question2_2)) {
                    Question2_2.setError("Input is required");
                    return;
                }
                else{
                    Intent intent = new Intent(question2.this, question3.class);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Answer2_1", question2_1);
                    editor.putString("Answer2_2", question2_2);
                    editor.apply();

                    startActivity(intent);
                    finish();
                }
            }
        });

        BackBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(question2.this, questions.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void DisplayText() {
        String answer2_1 = sharedPreferences.getString("Answer2_1", "");
        String answer2_2 = sharedPreferences.getString("Answer2_2", "");

        Question2_1.setText(answer2_1);
        Question2_2.setText(answer2_2);
    }
}