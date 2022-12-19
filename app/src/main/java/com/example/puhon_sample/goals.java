package com.example.puhon_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class goals extends AppCompatActivity {

    TextView goals_Today, goals_History;
    private int selectedTabNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        goals_Today = findViewById(R.id.goals_Today);
        goals_History = findViewById(R.id.goals_History);

        // selecting first fragment by default
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, FragmentToday.class, null)
                .commit();

        goals_Today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectTab(1);

            }
        });

        goals_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectTab(2);

            }
        });
    }

    private void selectTab(int tabNumber){

        TextView selectedTextView;
        TextView nonSelectedTextView;

        if(tabNumber == 1){

            // user has selected first tab so first TextView is selected
            selectedTextView = goals_Today;

            // the other is not selected
            nonSelectedTextView = goals_History;

            // setting fragment to the fragment container view
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer,FragmentToday.class, null)
                    .commit();
        }
        else {

            // user has selected second tab so second TextView is selected
            selectedTextView = goals_History;

            // the other is not selected
            nonSelectedTextView = goals_Today;

            // setting fragment to the fragment container view
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer,FragmentHistory.class, null)
                    .commit();
        }

        float slideTo = (tabNumber - selectedTabNumber) * selectedTextView.getWidth();

        // creating translate animation
        TranslateAnimation translateAnimation = new TranslateAnimation(0, slideTo, 0, 0);
        translateAnimation.setDuration(200);

        // checking for previously selected tab
        if(selectedTabNumber == 1){
            goals_Today.startAnimation(translateAnimation);
        }
        else{
            goals_History.startAnimation(translateAnimation);
        }

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // change design of selected tab's textView
                selectedTextView.setBackgroundResource(R.drawable.round_selector_goals);
                selectedTextView.setTypeface(null, Typeface.BOLD);
                selectedTextView.setTextColor(Color.WHITE);

                // change design of non selected tab's textView
                nonSelectedTextView.setBackgroundResource(R.drawable.round_back_goals);
                selectedTextView.setTypeface(null, Typeface.NORMAL);
                nonSelectedTextView.setTextColor(Color.parseColor("#4A5759"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        selectedTabNumber = tabNumber;
    }
}