package com.example.puhon_sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class fragment_stress extends Fragment {
    CardView stress1, stress2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stress, container, false);

        stress1 = view.findViewById(R.id.stress1);
        stress2 = view.findViewById(R.id.stress2);

        stress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), stress1.class);
                startActivity(intent);
            }
        });

        stress2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), stress2.class);
                startActivity(intent);
            }
        });

        return view;
    }
}