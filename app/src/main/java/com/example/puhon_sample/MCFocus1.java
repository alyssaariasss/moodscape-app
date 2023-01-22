package com.example.puhon_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MCFocus1 extends AppCompatActivity {

    public static final long START_TIME_IN_MILLIS = 600000;

    TextView focusTimer;
    Button focusTimerStart, focusTimerReset, focusTimerDone;
    CountDownTimer countDownTimer;
    long timeLeftInMillis = START_TIME_IN_MILLIS;
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

        CreateNotificationChannel();


        focusTimerDone.setOnClickListener(v -> {
            Intent intent = new Intent(this, Meditation.class);
            startActivity(intent);
        });

        focusTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
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
                // Set up alarm system for goals
                Intent intent = new Intent(MCFocus1.this, ReminderNotification.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MCFocus1.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

                long startTime = System.currentTimeMillis();
                long tenMinutesInMillis = 1000 * 600;

                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        startTime + tenMinutesInMillis, pendingIntent);
            }
        });
        updateTimer();


        // NavBar Buttons

        ImageButton btn_home = findViewById(R.id.nav_home);
        ImageButton btn_goals = findViewById(R.id.nav_goal);
        ImageButton btn_progress = findViewById(R.id.nav_progress);
        ImageButton btn_settings = findViewById(R.id.nav_settings);

        btn_home.setOnClickListener(v -> {
            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        });

        btn_goals.setOnClickListener(v -> {

            Intent intent = new Intent(this, goals.class);
            startActivity(intent);
        });

        btn_progress.setOnClickListener(v -> {
            Intent intent = new Intent(this, summary.class);
            startActivity(intent);
        });

        btn_settings.setOnClickListener(v -> {
            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
        });

    }
    // Initialize Countdown Timer channel
    private void CreateNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MeditationReminderChannel";
            String description = "Channel for Focus Meditation";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("ReminderNotification", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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