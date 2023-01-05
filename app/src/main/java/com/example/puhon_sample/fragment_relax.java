package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class fragment_relax extends Fragment {

    CardView Relax1, Relax2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relax, container, false);

        Relax1 = view.findViewById(R.id.relax1);
        Relax2 = view.findViewById(R.id.relax2);

        Relax1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), relax_music.class);
                startActivity(intent);
            }
        });

        Relax2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MCAnxiety2.class);
                startActivity(intent);
            }
        });




        return view;
    }
}