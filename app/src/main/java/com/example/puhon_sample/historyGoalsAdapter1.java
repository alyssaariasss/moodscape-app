package com.example.puhon_sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class historyGoalsAdapter1 extends RecyclerView.Adapter<historyGoalsAdapter1.MyViewHolder> {

    FragmentHistory goalsContext;
    ArrayList<UserGoals> goalsList2;


    public historyGoalsAdapter1(FragmentHistory goalsContext,
                               ArrayList<UserGoals> goalsList2) {
        this.goalsContext = goalsContext;
        this.goalsList2 = goalsList2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(goalsContext.getActivity()).inflate(R.layout.history_goals_group1,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        UserGoals userGoals = goalsList2.get(position);
        holder.GoalTxtHistory1.setText(userGoals.getGoal());
        holder.TimeTxtHistory1.setText(userGoals.getDeadline());

        //UserMoods userMoods = list2.get(position);
        //holder.MoodList.setText(String.valueOf(userMoods.getMood()));
        //holder.MoodList.setText(userMoods.getMood());

    }

    @Override
    public int getItemCount() {
        return goalsList2.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView GoalTxtHistory1, TimeTxtHistory1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            GoalTxtHistory1 = itemView.findViewById(R.id.goalTxtHistory1);
            TimeTxtHistory1 = itemView.findViewById(R.id.timeTxtHistory1);
        }
    }

}