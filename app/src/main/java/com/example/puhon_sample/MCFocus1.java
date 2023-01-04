package com.example.puhon_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MCFocus1 extends AppCompatActivity {

    TextView focusTimer;
    Button focusTimerStart;
    CountDownTimer countDownTimer;
    long timeLeftInMilliseconds = 600000; // 10 minutes
    boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcfocus1);


        focusTimer = findViewById(R.id.focus1_timer);
        focusTimerStart = findViewById(R.id.focus_timer_start);

        focusTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        updateTimer();
    }

    public void startStop() {
        if(timerRunning) {
            stopTimer();
        }
        else {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        focusTimerStart.setText("PAUSE");
        timerRunning = true;

    }

    public void stopTimer() {
        countDownTimer.cancel();
        focusTimerStart.setText("START");
        timerRunning = false;
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        focusTimer.setText(timeLeftText);


    }
}