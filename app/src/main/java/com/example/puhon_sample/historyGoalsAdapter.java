package com.example.puhon_sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class historyGoalsAdapter extends RecyclerView.Adapter<historyGoalsAdapter.MyViewHolder> {

    FragmentHistory goalsContext;
    ArrayList<UserGoals> goalsList;


    public historyGoalsAdapter(FragmentHistory goalsContext, ArrayList<UserGoals> goalsList) {
        this.goalsContext = goalsContext;
        this.goalsList = goalsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(goalsContext.getActivity()).inflate(R.layout.history_goals_group,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        UserGoals userGoals = goalsList.get(position);
        holder.GoalTxtHistory.setText(userGoals.getGoal());
        holder.TimeTxtHistory.setText(userGoals.getDeadline());

        //UserMoods userMoods = list2.get(position);
        //holder.MoodList.setText(String.valueOf(userMoods.getMood()));
        //holder.MoodList.setText(userMoods.getMood());

    }

    @Override
    public int getItemCount() {
        return goalsList.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView GoalTxtHistory, TimeTxtHistory;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            GoalTxtHistory = itemView.findViewById(R.id.goalTxtHistory);
            TimeTxtHistory = itemView.findViewById(R.id.timeTxtHistory);
        }
    }

}