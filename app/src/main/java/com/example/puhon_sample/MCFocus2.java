package com.example.puhon_sample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;

public class MCFocus2 extends AppCompatActivity {

    TextView tv_p1, tv_p2;
    ImageView focusQ1, focusQ2, focusQ3, focusQ4, focusQ5, focusQ6, focusQ7, focusQ8, focusQ9, focusQ10, focusQ11, focusQ12;

    // array for the images
    Integer [] cardsArray = { 101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206 };

    // actual images
    int image101, image102, image103, image104, image105, image106,
            image201, image202, image203, image204, image205, image206;

    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;

    int turn = 1;
    int playerPoints = 0, cpuPoints = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcfocus2);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        tv_p1 = findViewById(R.id.tv_p1);
        tv_p2 = findViewById(R.id.tv_p2);

        focusQ1 = findViewById(R.id.focusQ1);
        focusQ2 = findViewById(R.id.focusQ2);
        focusQ3 = findViewById(R.id.focusQ3);
        focusQ4 = findViewById(R.id.focusQ4);
        focusQ5 = findViewById(R.id.focusQ5);
        focusQ6 = findViewById(R.id.focusQ6);
        focusQ7 = findViewById(R.id.focusQ7);
        focusQ8 = findViewById(R.id.focusQ8);
        focusQ9 = findViewById(R.id.focusQ9);
        focusQ10 = findViewById(R.id.focusQ10);
        focusQ11 = findViewById(R.id.focusQ11);
        focusQ12 = findViewById(R.id.focusQ12);

        focusQ1.setTag("0");
        focusQ2.setTag("1");
        focusQ3.setTag("2");
        focusQ4.setTag("3");
        focusQ5.setTag("4");
        focusQ6.setTag("5");
        focusQ7.setTag("6");
        focusQ8.setTag("7");
        focusQ9.setTag("8");
        focusQ10.setTag("9");
        focusQ11.setTag("10");
        focusQ12.setTag("11");


        // load the card images
        frontOfCardsResources();

        // shuffle the images
        Collections.shuffle(Arrays.asList(cardsArray));

        // changing the color of the second player (inactive)
//        tv_p2.setTextColor(Color.MAGENTA);

        focusQ1.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) v.getTag());
            doStuff(focusQ1, theCard);
        });

        focusQ2.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) v.getTag());
            doStuff(focusQ2, theCard);
        });

        focusQ3.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) v.getTag());
            doStuff(focusQ3, theCard);
        });

        focusQ4.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) v.getTag());
            doStuff(focusQ4, theCard);
        });

        focusQ5.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) v.getTag());
            doStuff(focusQ5, theCard);
        });

        focusQ6.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) v.getTag());
            doStuff(focusQ6, theCard);
        });

        focusQ7.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) v.getTag());
            doStuff(focusQ7, theCard);
        });

        focusQ8.setOnClickListener(v -> {
            int theCard = Integer.parseInt(v.getTag().toString());
            doStuff(focusQ8, theCard);
        });

        focusQ9.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) v.getTag());
            doStuff(focusQ9, theCard);
        });

        focusQ10.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) v.getTag());
            doStuff(focusQ10, theCard);
        });

        focusQ11.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) v.getTag());
            doStuff(focusQ11, theCard);
        });

        focusQ12.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) v.getTag());
            doStuff(focusQ12, theCard);
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

    private void doStuff(ImageView img, int card) {
        //set the correct image to the imageview
        if (cardsArray[card] == 101) {
            img.setImageResource(image101);
        } else if (cardsArray[card] == 102) {
            img.setImageResource(image102);
        } else if (cardsArray[card] == 103) {
            img.setImageResource(image103);
        } else if (cardsArray[card] == 104) {
            img.setImageResource(image104);
        } else if (cardsArray[card] == 105) {
            img.setImageResource(image105);
        } else if (cardsArray[card] == 106) {
            img.setImageResource(image106);
        } else if (cardsArray[card] == 201) {
            img.setImageResource(image201);
        } else if (cardsArray[card] == 202) {
            img.setImageResource(image202);
        } else if (cardsArray[card] == 203) {
            img.setImageResource(image203);
        } else if (cardsArray[card] == 204) {
            img.setImageResource(image204);
        } else if (cardsArray[card] == 205) {
            img.setImageResource(image205);
        } else if (cardsArray[card] == 206) {
            img.setImageResource(image206);
        }

        // check which image is selected and save it to temporary variable
        if (cardNumber == 1) {
            firstCard = cardsArray[card];
            if (firstCard > 200) {
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;

            img.setEnabled(false);
        } else if (cardNumber == 2) {
            secondCard = cardsArray[card];
            if (secondCard > 200) {
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;

            focusQ1.setEnabled(false);
            focusQ2.setEnabled(false);
            focusQ3.setEnabled(false);
            focusQ4.setEnabled(false);
            focusQ5.setEnabled(false);
            focusQ6.setEnabled(false);
            focusQ7.setEnabled(false);
            focusQ8.setEnabled(false);
            focusQ9.setEnabled(false);
            focusQ10.setEnabled(false);
            focusQ11.setEnabled(false);
            focusQ12.setEnabled(false);

            Handler handler = new Handler();
            //  check if the selected images are equal
            handler.postDelayed(this::calculate, 1000);
        }
    }

    @SuppressLint("SetTextI18n")
    private void calculate() {
        if (firstCard == secondCard) {
            if (clickedFirst == 0) {
                focusQ1.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 1) {
                focusQ2.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 2) {
                focusQ3.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 3) {
                focusQ4.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 4) {
                focusQ5.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 5) {
                focusQ6.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 6) {
                focusQ7.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 7) {
                focusQ8.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 8) {
                focusQ9.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 9) {
                focusQ10.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 10) {
                focusQ11.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 11) {
                focusQ12.setVisibility(View.INVISIBLE);
            }

            if (clickedSecond == 0) {
                focusQ1.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 1) {
                focusQ2.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 2) {
                focusQ3.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 3) {
                focusQ4.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 4) {
                focusQ5.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 5) {
                focusQ6.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 6) {
                focusQ7.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 7) {
                focusQ8.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 8) {
                focusQ9.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 9) {
                focusQ10.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 10) {
                focusQ11.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 11) {
                focusQ12.setVisibility(View.INVISIBLE);
            }

            // add points to the correct player
            if (turn == 1) {
                playerPoints++;
                tv_p1.setText("P1: " + playerPoints);
            } else if (turn == 2) {
                cpuPoints++;
                tv_p2.setText("P2: " + cpuPoints);
            }
        } else {
            focusQ1.setImageResource(R.drawable.a_question);
            focusQ2.setImageResource(R.drawable.a_question);
            focusQ3.setImageResource(R.drawable.a_question);
            focusQ4.setImageResource(R.drawable.a_question);
            focusQ5.setImageResource(R.drawable.a_question);
            focusQ6.setImageResource(R.drawable.a_question);
            focusQ7.setImageResource(R.drawable.a_question);
            focusQ8.setImageResource(R.drawable.a_question);
            focusQ9.setImageResource(R.drawable.a_question);
            focusQ10.setImageResource(R.drawable.a_question);
            focusQ11.setImageResource(R.drawable.a_question);
            focusQ12.setImageResource(R.drawable.a_question);

            // change the player turn
            if (turn == 1) {
                turn = 2;
                tv_p1.setTextColor(Color.MAGENTA);
                tv_p2.setTextColor(Color.BLACK);

            }
        }

        focusQ1.setEnabled(true);
        focusQ2.setEnabled(true);
        focusQ3.setEnabled(true);
        focusQ4.setEnabled(true);
        focusQ5.setEnabled(true);
        focusQ6.setEnabled(true);
        focusQ7.setEnabled(true);
        focusQ8.setEnabled(true);
        focusQ9.setEnabled(true);
        focusQ10.setEnabled(true);
        focusQ11.setEnabled(true);
        focusQ12.setEnabled(true);

        // checked if the game is over
        checkEnd();
    }

    private void checkEnd() {
        if (focusQ1.getVisibility() == View.INVISIBLE &&
                focusQ2.getVisibility() == View.INVISIBLE &&
                focusQ3.getVisibility() == View.INVISIBLE &&
                focusQ4.getVisibility() == View.INVISIBLE &&
                focusQ5.getVisibility() == View.INVISIBLE &&
                focusQ6.getVisibility() == View.INVISIBLE &&
                focusQ7.getVisibility() == View.INVISIBLE &&
                focusQ8.getVisibility() == View.INVISIBLE &&
                focusQ9.getVisibility() == View.INVISIBLE &&
                focusQ10.getVisibility() == View.INVISIBLE &&
                focusQ11.getVisibility() == View.INVISIBLE &&
                focusQ12.getVisibility() == View.INVISIBLE) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MCFocus2.this);
            alertDialogBuilder
                    .setMessage("Congratulations!")
                    .setCancelable(false)
                    .setPositiveButton("NEW", (dialog, which) -> {
                        Intent intent = new Intent(getApplicationContext(), MCFocus2.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("EXIT", (dialog, which) -> {
                        Intent intent = new Intent(getApplicationContext(), Meditation.class);
                        startActivity(intent);
                        finish();
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void frontOfCardsResources() {
        image101 = R.drawable.a_apple;
        image102 = R.drawable.a_bananas;
        image103 = R.drawable.a_grapes;
        image104 = R.drawable.a_kiwi;
        image105 = R.drawable.a_mango;
        image106 = R.drawable.a_orange;
        image201 = R.drawable.a_apple2;
        image202 = R.drawable.a_bananas2;
        image203 = R.drawable.a_grapes2;
        image204 = R.drawable.a_kiwi2;
        image205 = R.drawable.a_mango2;
        image206 = R.drawable.a_orange2;

    }
}