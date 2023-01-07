package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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
        fallingDog.videoURL = "https://v16-webapp.tiktok.com/ee5314933a04e76ae36525bc1ae55df2/63b9af30/video/tos/useast2a/tos-useast2a-pve-0037-aiso/oM4BECzbDv7jeDAEQg26VeUnQD7CPYgsmBd6oY/?a=1988&ch=0&cr=0&dr=0&lr=tiktok&cd=0%7C0%7C1%7C0&cv=1&br=6126&bt=3063&cs=0&ds=3&ft=4b~OyMei8Zmo0fsHo64jVVL6PpWrKsdm&mime_type=video_mp4&qs=0&rc=ZmlpZWVpOzU2OjlpMzo2NUBpamQ1bzU6ZmZyZzMzZjgzM0AzNDMtMGEzNS4xM2M1Xl4yYSNiL2Q2cjRvZG9gLS1kL2Nzcw%3D%3D&l=20230107114306D2CEC2B40D2D44251244&btag=80000";
        fallingDog.videoTitle = "Puppy riding a scooter";
        fallingDog.videoDescription = "Video credits: @everywherejovan";
        videoItemList.add(fallingDog);

        VideoItem laughingParamedics = new VideoItem();
        laughingParamedics.videoURL = "https://v16-webapp.tiktok.com/6dd4ee836314d7f126b940316854ee7f/63b9af89/video/tos/useast2a/tos-useast2a-pve-0068/a833bc0b4b5540b3bd9c4cd47e5dca90/?a=1988&ch=0&cr=0&dr=0&lr=tiktok_m&cd=0%7C0%7C0%7C0&br=498&bt=249&cs=0&ds=1&ft=4b~OyMei8Zmo0EsHo64jVfO6PpWrKsdm&mime_type=video_mp4&qs=0&rc=Omc4aTQ1ZDs0Omg4OGk3PEBpajV4bzw6ZnBqZTMzNzczM0AtYzIzMTMyXzYxNjQ2XjVfYSMuL19jcjQwXjBgLS1kMTZzcw%3D%3D&l=20230107114306DF47122312756E25BF0E&btag=80000";
        laughingParamedics.videoTitle = "Two laughing paramedics";
        laughingParamedics.videoDescription = "Video credits: @dt2073";
        videoItemList.add(laughingParamedics);

        VideoItem trumpetDog = new VideoItem();
        trumpetDog.videoURL = "https://v16-webapp.tiktok.com/b1e46376f71536f2d3cd182a94f31bd9/63b9afc1/video/tos/maliva/tos-maliva-ve-0068c799-us/fd7cc0bee6ca41b0a0acb76728d57630/?a=1988&ch=0&cr=0&dr=0&lr=tiktok_m&cd=0%7C0%7C1%7C0&cv=1&br=1858&bt=929&cs=0&ds=3&ft=4b~OyMei8Zmo0LxHo64jVOOTPpWrKsdm&mime_type=video_mp4&qs=0&rc=NTdlaDg2NjtlMzo8Mzg4aEBpanU7NDU6Znd4ZzMzZzczNEBiMTY2LmE2NjYxM15hYmEvYSNsNV8ycjRnYnBgLS1kMS9zcw%3D%3D&l=202301071145246729AF86C24EA225AB8C&btag=80000";
        trumpetDog.videoTitle = "Dog playing a trumpet";
        trumpetDog.videoDescription = "Video credits: @ly12206688";
        videoItemList.add(trumpetDog);

        viewPager2.setAdapter(new VideoAdapter(videoItemList));

        stress1DoneBtn.setOnClickListener(view -> {
            Intent intent = new Intent(stress1.this, Meditation.class);
            startActivity(intent);
        });
    }
}