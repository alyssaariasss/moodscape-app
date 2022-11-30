package com.example.puhon_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;


public class moods extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moods);

        // Mood Choices/Slider
        ImageSlider imageSlider = findViewById(R.id.mood_slider);

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.happy_mood));
        slideModels.add(new SlideModel(R.drawable.angry_mood));
        slideModels.add(new SlideModel(R.drawable.sad_mood));
        slideModels.add(new SlideModel(R.drawable.disgusted_mood));
        slideModels.add(new SlideModel(R.drawable.surprised_mood));
        slideModels.add(new SlideModel(R.drawable.fearful_mood));

        imageSlider.setImageList(slideModels,true);

        // NavBar Buttons

        ImageButton btn_home = findViewById(R.id.nav_home);

        btn_home.setOnClickListener(v -> {

            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        });

        ImageButton btn_info = findViewById(R.id.nav_about_mood);

        btn_info.setOnClickListener(v -> {

            Intent intent = new Intent(this, BreakScreen1.class);
            startActivity(intent);
        });

        ImageButton btn_progress = findViewById(R.id.nav_progress);

        btn_progress.setOnClickListener(v -> {

            Intent intent = new Intent(this, sample_activity.class);
            startActivity(intent);
        });

        ImageButton btn_settings = findViewById(R.id.nav_settings);

        btn_settings.setOnClickListener(v -> {

            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
        });

    }
}