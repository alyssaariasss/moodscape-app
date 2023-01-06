package com.example.puhon_sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentHistory extends Fragment {

    RecyclerView goalsRecyclerView;
    ArrayList<String> arrayListGroup;
    ArrayList<String> arrayListMissed;
    LinearLayoutManager linearLayoutManager;
    GroupAdapter groupAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        goalsRecyclerView = (RecyclerView) view.findViewById(R.id.goalsRecyclerGroup);

        arrayListGroup = new ArrayList<>();

        // Date
        for (int i=1; i<=10; i++) {
            arrayListGroup.add("04/Jan/2023");
        }

        arrayListMissed = new ArrayList<>();
        for (int i=1; i <= 4; i++) {
            arrayListMissed.add("Missed Goals " + i);
        }

        groupAdapter = new GroupAdapter(FragmentHistory.this, arrayListGroup, arrayListMissed);
        linearLayoutManager = new LinearLayoutManager(getContext());
        goalsRecyclerView.setLayoutManager(linearLayoutManager);
        goalsRecyclerView.setAdapter(groupAdapter);

        return view;
    }
}