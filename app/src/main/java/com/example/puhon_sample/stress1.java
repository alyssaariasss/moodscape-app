package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class stress1 extends AppCompatActivity {
    Button stress1DoneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress1);

        final ViewPager2 viewPager2 = findViewById(R.id.viewPager2);
        stress1DoneBtn = findViewById(R.id.stress1DoneBtn);

        List<VideoItem> videoItemList = new ArrayList<>();

        VideoItem fallingDog = new VideoItem();
        fallingDog.videoURL = "android.resource://" + getPackageName() + "/" + R.raw.stress_a;
        fallingDog.videoTitle = "Puppy riding a scooter";
        fallingDog.videoDescription = "Video credits: @everywherejovan";
        videoItemList.add(fallingDog);

        VideoItem laughingParamedics = new VideoItem();
        laughingParamedics.videoURL = "android.resource://" + getPackageName() + "/" + R.raw.stress_b;
        laughingParamedics.videoTitle = "Two laughing paramedics";
        laughingParamedics.videoDescription = "Video credits: @dt2073";
        videoItemList.add(laughingParamedics);

        VideoItem trumpetDog = new VideoItem();
        trumpetDog.videoURL = "android.resource://" + getPackageName() + "/" + R.raw.stress_c;
        trumpetDog.videoTitle = "Dog playing a trumpet";
        trumpetDog.videoDescription = "Video credits: @ly12206688";
        videoItemList.add(trumpetDog);

        viewPager2.setAdapter(new VideoAdapter(videoItemList));

        stress1DoneBtn.setOnClickListener(view -> {
            Intent intent = new Intent(stress1.this, Meditation.class);
            startActivity(intent);
        });

        // NavBar Buttons

        ImageButton btn_home = findViewById(R.id.nav_home);
        ImageButton btn_info = findViewById(R.id.nav_about_mood);
        ImageButton btn_progress = findViewById(R.id.nav_progress);
        ImageButton btn_settings = findViewById(R.id.nav_settings);


        btn_home.setOnClickListener(v -> {

            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        });

        btn_info.setOnClickListener(v -> {

            Intent intent = new Intent(this, aboutMoodscape.class);
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
}