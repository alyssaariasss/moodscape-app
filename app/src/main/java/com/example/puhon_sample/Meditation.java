package com.example.puhon_sample;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Meditation extends AppCompatActivity {
    ImageView mc_relax, mc_focus, mc_anxiety, mc_stress;
    private int selectedTabNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        mc_relax = findViewById(R.id.mc_relax);
        mc_focus = findViewById(R.id.mc_focus);
        mc_anxiety = findViewById(R.id.mc_anxiety);
        mc_stress = findViewById(R.id.mc_stress);


        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentLayout, fragment_relax.class, null)
                .commit();

        mc_relax.setOnClickListener(view -> selectTab(1));
        mc_focus.setOnClickListener(view -> selectTab(2));
        mc_anxiety.setOnClickListener(view -> selectTab(3));
        mc_stress.setOnClickListener(view -> selectTab(4));


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

            Intent intent = new Intent(this, summary.class);
            startActivity(intent);
        });

        ImageButton btn_settings = findViewById(R.id.nav_settings);

        btn_settings.setOnClickListener(v -> {

            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
        });

    }

    private void selectTab(int tabNumber) {
        ImageView selectedImageView;

        ImageView nonSelectedImageView1;
        ImageView nonSelectedImageView2;
        ImageView nonSelectedImageView3;


        if (tabNumber == 1) {
            selectedImageView = mc_relax;
            nonSelectedImageView1 = mc_focus;
            nonSelectedImageView2 = mc_anxiety;
            nonSelectedImageView3 = mc_stress;

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentLayout,fragment_relax.class, null)
                    .commit();
        }
        else if (tabNumber == 2) {
            selectedImageView = mc_focus;
            nonSelectedImageView1 = mc_relax;
            nonSelectedImageView2 = mc_anxiety;
            nonSelectedImageView3 = mc_stress;

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentLayout,fragment_focus.class, null)
                    .commit();
        }

        else if (tabNumber == 3) {
            selectedImageView = mc_anxiety;
            nonSelectedImageView1 = mc_relax;
            nonSelectedImageView2 = mc_focus;
            nonSelectedImageView3 = mc_stress;

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentLayout,fragment_anxiety.class, null)
                    .commit();
        }

        else {
            selectedImageView = mc_stress;
            nonSelectedImageView1 = mc_relax;
            nonSelectedImageView2 = mc_focus;
            nonSelectedImageView3 = mc_anxiety;

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentLayout,fragment_stress.class, null)
                    .commit();
        }

        float slideTo = (tabNumber - selectedTabNumber) * selectedImageView.getWidth();

        TranslateAnimation translateAnimation = new TranslateAnimation(0, slideTo, 0, 0);
        translateAnimation.setDuration(200);

        if (selectedTabNumber == 1) {
            mc_relax.startAnimation(translateAnimation);
        }
        else if (selectedTabNumber == 2) {
            mc_focus.startAnimation(translateAnimation);
        }
        else if (selectedTabNumber == 3) {
            mc_anxiety.startAnimation(translateAnimation);
        }
        else {
            mc_stress.startAnimation(translateAnimation);
        }

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        selectedTabNumber = tabNumber;
    }
}
