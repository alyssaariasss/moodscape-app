package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class fragment_anxiety extends Fragment {

    CardView anxiety1CV, anxiety2CV;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anxiety, container, false);

        anxiety1CV = view.findViewById(R.id.anxiety1);
        anxiety2CV = view.findViewById(R.id.anxiety2);



        anxiety1CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MCAnxiety1.class);
                startActivity(intent);
            }
        });

        anxiety2CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MCAnxiety2.class);
                startActivity(intent);
            }
        });

        return view;
    }

}