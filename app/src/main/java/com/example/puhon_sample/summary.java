package com.example.puhon_sample;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class summary extends AppCompatActivity {
    TextView summaryTab, overallTab;
    private int selectedTabNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        summaryTab = findViewById(R.id.summaryTab);
        overallTab = findViewById(R.id.overallTab);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentLayout, FragmentSummary.class, null)
                .commit();

        summaryTab.setOnClickListener(view -> selectTab(1));

        overallTab.setOnClickListener(view -> selectTab(2));


        // NavBar Buttons

        ImageButton btn_home = findViewById(R.id.nav_home);
        ImageButton btn_info = findViewById(R.id.nav_about_mood);
        ImageButton btn_settings = findViewById(R.id.nav_settings);


        btn_home.setOnClickListener(v -> {

            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        });

        btn_info.setOnClickListener(v -> {

            Intent intent = new Intent(this, aboutMoodscape.class);
            startActivity(intent);
        });

        btn_settings.setOnClickListener(v -> {

            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
        });

    }

    private void selectTab(int tabNumber) {
        TextView selectedTextView, nonSelectedTextView;

        if (tabNumber == 1) {
            selectedTextView = summaryTab;
            nonSelectedTextView = overallTab;

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentLayout,FragmentSummary.class, null)
                    .commit();
        }
        else {
            selectedTextView = overallTab;
            nonSelectedTextView = summaryTab;

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentLayout,FragmentOverallReport.class, null)
                    .commit();
        }

        float slideTo = (tabNumber - selectedTabNumber) * selectedTextView.getWidth();

        TranslateAnimation translateAnimation = new TranslateAnimation(0, slideTo, 0, 0);
        translateAnimation.setDuration(200);

        if (selectedTabNumber == 1) {
            summaryTab.startAnimation(translateAnimation);
        }
        else {
            overallTab.startAnimation(translateAnimation);
        }

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                selectedTextView.setBackgroundResource(R.drawable.round_selector_goals);
                selectedTextView.setTypeface(null, Typeface.BOLD);
                selectedTextView.setTextColor(Color.WHITE);

                nonSelectedTextView.setBackgroundResource(R.drawable.round_back_goals);
                selectedTextView.setTypeface(null, Typeface.NORMAL);
                nonSelectedTextView.setTextColor(ContextCompat.getColor(summary.this, R.color.dark_gray));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        selectedTabNumber = tabNumber;
    }
}