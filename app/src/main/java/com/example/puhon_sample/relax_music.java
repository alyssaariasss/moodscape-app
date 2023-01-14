package com.example.puhon_sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class relax_music extends AppCompatActivity {

    Button Done;
    ImageView Play, Prev, Next;
    TextView SongTitle;
    SeekBar SeekBarTime, SeekBarVol;
    private Runnable runnable;
    private AudioManager audioManager;
    int currentIndex = 0;
    static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax_music);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // initializing views
        //Play = findViewById(R.id.relaxPlayBtn);
        Play = findViewById(R.id.relaxPlay);
        Prev = findViewById(R.id.relaxPrev);
        Next = findViewById(R.id.relaxNext);
        Done = findViewById(R.id.relaxDoneBtn);
        SongTitle = findViewById(R.id.songTitle);
        SeekBarTime = findViewById(R.id.seekBarTime);
        SeekBarVol = findViewById(R.id.seekBarVol);

        // creating arraylist to store songs
        ArrayList<Integer> songs = new ArrayList<>();

        songs.add(0, R.raw.acoustic_vibe);
        songs.add(1, R.raw.freshness);
        songs.add(2, R.raw.just_relax);
        songs.add(3, R.raw.people_and_trees);
        songs.add(4, R.raw.street_food);
        songs.add(5, R.raw.the_beat_of_nature);


        // initializing media player
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));


        // seekbar volume
        int maxV = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curV = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        SeekBarVol.setMax(maxV);
        SeekBarVol.setProgress(curV);

        SeekBarVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeekBarTime.setMax(mediaPlayer.getDuration());
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    Play.setImageResource(R.drawable.play_icon);
                }else {
                    mediaPlayer.start();
                    Play.setImageResource(R.drawable.pause_icon);
                }
                SongNames();
            }
        });


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex < songs.size() - 1) {
                    currentIndex++;
                } else {
                    currentIndex = 0;
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));
                mediaPlayer.start();
                SongNames();
            }
        });

        Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex > 0) {
                    currentIndex--;
                } else {
                    currentIndex = songs.size() - 1;
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));
                mediaPlayer.start();
                SongNames();
            }
        });

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                startActivity(new Intent(getApplicationContext(), Meditation.class));
                finish();
            }
        });

    }

    // created a section for all the names
    private void SongNames() {
        if (currentIndex == 0) {
            SongTitle.setText("Acoustic Vibe");
        }
        if (currentIndex == 1) {
            SongTitle.setText("Freshness");
        }
        if (currentIndex == 2) {
            SongTitle.setText("Just Relax");
        }
        if (currentIndex == 3) {
            SongTitle.setText("People and Trees");
        }
        if (currentIndex == 4) {
            SongTitle.setText("Street Food");
        }
        if (currentIndex == 5) {
            SongTitle.setText("The Beat of Nature");
        }


        // seekbar duration
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                SeekBarTime.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
            }
        });
        SeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mediaPlayer.seekTo(progress);
                    SeekBarTime.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // NavBar Buttons

        ImageButton btn_home = findViewById(R.id.nav_home);

        btn_home.setOnClickListener(v -> {

            mediaPlayer.stop();
            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        });

        ImageButton btn_info = findViewById(R.id.nav_about_mood);

        btn_info.setOnClickListener(v -> {

            mediaPlayer.stop();
            Intent intent = new Intent(this, BreakScreen1.class);
            startActivity(intent);
        });

        ImageButton btn_progress = findViewById(R.id.nav_progress);

        btn_progress.setOnClickListener(v -> {

            mediaPlayer.stop();
            Intent intent = new Intent(this, summary.class);
            startActivity(intent);
        });

        ImageButton btn_settings = findViewById(R.id.nav_settings);

        btn_settings.setOnClickListener(v -> {

            mediaPlayer.stop();
            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    try{
                        if (mediaPlayer.isPlaying()) {
                            Message message = new Message();
                            message.what = mediaPlayer.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @SuppressLint("Handler Leak") Handler handler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            SeekBarTime.setProgress(msg.what);
        }
    };
}
