package com.example.puhon_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MCFocus1 extends AppCompatActivity {

    public static final long START_TIME_IN_MILLIS = 6000;

    TextView focusTimer;
    Button focusTimerStart, focusTimerReset, focusTimerDone;
    CountDownTimer countDownTimer;
    long timeLeftInMillis;
    long endTime;
    boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcfocus1);


        focusTimer = findViewById(R.id.focus1_timer);
        focusTimerStart = findViewById(R.id.focus_timer_start);
        focusTimerReset = findViewById(R.id.focus_timer_reset);
        focusTimerDone = findViewById(R.id.focus_timer_done);

        focusTimerDone.setVisibility(View.INVISIBLE);
        focusTimerDone.postDelayed(new Runnable() {
            public void run() {
                focusTimerDone.setVisibility(View.VISIBLE);
            }
        }, 60000);

        focusTimerDone.setOnClickListener(v -> {

            Intent intent = new Intent(this, Meditation.class);
            startActivity(intent);
        });

        focusTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning) {
                    pauseTimer();
                }
                else {
                    startTimer();
                }
            }
        });

        focusTimerReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

    }

    public void startTimer() {
        endTime = System.currentTimeMillis() + timeLeftInMillis;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                focusTimerDone.setVisibility(View.VISIBLE);
                updateButtons();
            }
        }.start();

        timerRunning = true;
        updateButtons();
    }

    public void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        updateButtons();
    }

    public void resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateTimer();
        updateButtons();
    }

    public void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        focusTimer.setText(timeLeftFormatted);
    }

    public void updateButtons() {
        if (timerRunning) {
            focusTimerReset.setVisibility(View.INVISIBLE);
            focusTimerStart.setText("PAUSE");
        }
        else {
            focusTimerStart.setText("START");

            if (timeLeftInMillis < 1000) {
                focusTimerStart.setVisibility(View.INVISIBLE);
            }
            else {
                focusTimerStart.setVisibility(View.VISIBLE);
            }

            if (timeLeftInMillis < START_TIME_IN_MILLIS) {
                focusTimerReset.setVisibility(View.VISIBLE);
            }
            else {
                focusTimerReset.setVisibility(View.INVISIBLE);
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong("millisLeft", timeLeftInMillis);
        editor.putBoolean("timerRunning", timerRunning);
        editor.putLong("endTime", endTime);

        editor.apply();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);

        timeLeftInMillis = preferences.getLong("millisLeft", START_TIME_IN_MILLIS);
        timerRunning = preferences.getBoolean("timerRunning", false);

        updateTimer();
        updateButtons();

        if (timerRunning) {
            endTime = preferences.getLong("endTime", 0);
            timeLeftInMillis = endTime - System.currentTimeMillis();

            if (timeLeftInMillis < 0){
                timeLeftInMillis = 0;
                timerRunning = false;
                updateTimer();
                updateButtons();
            }
            else {
                startTimer();
            }
        }
    }
}