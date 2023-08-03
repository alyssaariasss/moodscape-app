package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class Onboarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout pageLayout;
    Button skipBtn, backBtn, nextBtn;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.onboardingViewPager);
        pageLayout = findViewById(R.id.pageIndicator);

        skipBtn = findViewById(R.id.skipButton);
        backBtn = findViewById(R.id.onBackButton);
        nextBtn = findViewById(R.id.onNextButton);

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Onboarding.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getItem(0) > 0) {
                    viewPager.setCurrentItem(getItem(-1), true);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getItem(0) < 2) {
                    viewPager.setCurrentItem(getItem(1), true);
                } else {
                    Intent intent = new Intent(Onboarding.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        setIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);
    }

    public void setIndicator (int position) {
        dots = new TextView[3];
        pageLayout.removeAllViews();

        for (int i=0; i<dots.length;i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(ContextCompat.getColor(this, R.color.mid_gray));
            pageLayout.addView(dots[i]);
        }

        dots[position].setTextColor(ContextCompat.getColor(this, R.color.dark_gray));

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setIndicator(position);

            if (position>0) {
                backBtn.setVisibility(View.VISIBLE);
            } else {
                backBtn.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getItem (int i) {
        return viewPager.getCurrentItem() + i;
    }
}