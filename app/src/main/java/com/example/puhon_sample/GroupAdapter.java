package com.example.puhon_sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    FragmentHistory fragmentHistory;
    ArrayList<String> completedList;
    ArrayList<String> missedList;

    GroupAdapter(FragmentHistory fragmentHistory, ArrayList<String> completedList, ArrayList<String> missedList) {
        this.fragmentHistory = fragmentHistory;
        this.completedList = completedList;
        this.missedList = missedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragmentHistory.getContext()).inflate(R.layout.history_goals_group, parent, false);
        return new GroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.goalsDateHeader.setText(completedList.get(position));

        ArrayList<String> arrayListComplete = new ArrayList<>();
        for (int i=1; i <= 4; i++) {
            arrayListComplete.add("Completed Goals " + i);
        }

        ArrayList<String> arrayListMissed = new ArrayList<>();
        for (int i=1; i <= 4; i++) {
            arrayListMissed.add("Missed Goals " + i);
        }

        CompletedGoalListAdapter completedGoalListAdapter = new CompletedGoalListAdapter(arrayListComplete);
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentHistory.getContext());
        holder.goalsRecyclerView.setLayoutManager(layoutManager);
        holder.goalsRecyclerView.setAdapter(completedGoalListAdapter);

        MissedGoalListAdapter missedGoalListAdapter = new MissedGoalListAdapter(arrayListMissed);
        LinearLayoutManager mlayoutManager = new LinearLayoutManager(fragmentHistory.getContext());
        holder.mgoalsRecyclerView.setLayoutManager(mlayoutManager);
        holder.mgoalsRecyclerView.setAdapter(missedGoalListAdapter);
    }

    @Override
    public int getItemCount() {
        return completedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView goalsDateHeader;
        RecyclerView goalsRecyclerView, mgoalsRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //goalsDateHeader = itemView.findViewById(R.id.goalsDateHeader);
            goalsRecyclerView = itemView.findViewById(R.id.goalsRecyclerView);
            //mgoalsRecyclerView = itemView.findViewById(R.id.mgoalsRecyclerView);
        }
    }
}
