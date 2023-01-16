package com.example.puhon_sample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class question4 extends AppCompatActivity {
    Button BackBtn4, NextBtn4;
    TextView Question4Rate;
    SeekBar Question4;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        Question4 = findViewById(R.id.question4_seekBar);
        Question4Rate = findViewById(R.id.question4_rate);
        BackBtn4 = findViewById(R.id.backbtn4);
        NextBtn4 = findViewById(R.id.nextbtn4);

        sharedPreferences = getApplicationContext().getSharedPreferences("UserAnswers", Context.MODE_PRIVATE);
        DisplayText();

        Question4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Question4Rate.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //next and back buttons
        NextBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int question4 = Question4.getProgress();

                Intent intent = new Intent(question4.this, question5.class);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("Answer4", question4);
                editor.apply();

                startActivity(intent);
                finish();
            }
        });

        BackBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(question4.this, question3.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void DisplayText() {
        int answer4 = sharedPreferences.getInt("Answer4",0);

        Question4Rate.setText("" + answer4);
        Question4.setProgress(answer4);
    }
}