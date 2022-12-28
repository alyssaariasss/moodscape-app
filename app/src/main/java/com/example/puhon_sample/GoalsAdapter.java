package com.example.puhon_sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.GoalsViewHolder> {

    List<String> UserGoals;
    goals activity;
    FirebaseDatabase database;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    String id;
    int i = 0;

    public GoalsAdapter(List<String> userGoals) {
        UserGoals = userGoals;
    }

    @NonNull
    @Override
    public GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoalsViewHolder
                (LayoutInflater.from(parent.getContext()).inflate(R.layout.today_goal_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsViewHolder holder, int position) {

        holder.goals_checkBox.setText(UserGoals.get(position));

    }

    @Override
    public int getItemCount() {
        return UserGoals.size() ;
    }

    class GoalsViewHolder extends RecyclerView.ViewHolder {

        CheckBox goals_checkBox;

        public GoalsViewHolder(@NonNull View itemView) {
            super(itemView);

            goals_checkBox = itemView.findViewById(R.id.goalsCheckbox);
        }
    }
}