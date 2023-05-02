package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MCAnxiety1 extends AppCompatActivity {
    TextView title2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcanxiety1);

        Button mcButtonDone = findViewById(R.id.mc_doneBtn);
        title2 = findViewById(R.id.title2);

        String[] quoteList = {
                "I am allowed to make a big deal out of things that feel really big to me.",
                "Some days will be harder to get up to, some days will be unusual. But you are free to keep going anyway. Let genuine days remind you, you are more than what is behind you.",
                "When you have a dream, you've got to grab it and never let go.",
                "Life has got all those twists and turns. You've got to hold on tight and off you go.",
                "Keep your face always toward the sunshine, and shadows will fall behind you.",
                "There is always light. If only we're brave enough to see it. If only we're brave enough to be it.",
                "When you give joy to other people, you get more joy in return. You should give a good thought to happiness that you can give out.",
                "It is only when we take chances, when our lives improve. The initial and the most difficult risk that we need to take is to become honest.",
                "There are three ways to ultimate success: The first way is to be kind. The second way is to be kind. The third way is to be kind.",
                "If you are working on something that you really care about, you don’t have to be pushed. The vision pulls you.",
                "You don't always need a plan. Sometimes you just need to breathe, trust, let go and see what happens.",
                "People often say that beauty is in the eye of the beholder, and I say that the most liberating thing about beauty is realizing you are the beholder.",
                "The best and most beautiful things in the world cannot be seen or even touched — they must be felt with the heart.",
                "Don’t limit yourself. Many people limit themselves to what they think they can do. You can go as far as your mind lets you. What you believe, remember, you can achieve.",
                "We need to accept that we won’t always make the right decisions, that we’ll screw up royally sometimes―understanding that failure is not the opposite of success, it’s part of success.",
                "Happiness is not something ready made. It comes from your own actions.",
                "Magic is believing in yourself. If you can make that happen, you can make anything happen.",
                "If you believe it’ll work out, you’ll see opportunities. If you don’t believe it’ll work out, you’ll see obstacles.",
                "Don’t be pushed around by the fears in your mind. Be led by the dreams in your heart.",
                "Sometimes when you’re in a dark place you think you’ve been buried but you’ve actually been planted.",
                "Twenty years from now you’ll be more disappointed by the things you did not do than the ones you did.",
        };

        Random randomizeQuote = new Random();
        String quote = quoteList[randomizeQuote.nextInt(quoteList.length)];
        title2.setText(String.format("\"%s\"", quote));

        mcButtonDone.setOnClickListener(v -> {
            Intent intent = new Intent(this, Meditation.class);
            startActivity(intent);
        });

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
}