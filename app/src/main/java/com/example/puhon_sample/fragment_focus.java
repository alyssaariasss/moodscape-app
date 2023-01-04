package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class fragment_focus extends Fragment {

    CardView focus1CV, focus2CV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_focus, container, false);

        focus1CV = view.findViewById(R.id.focus1);


        focus1CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MCFocus1.class);
                startActivity(intent);
            }
        });

        return view;
    }
}