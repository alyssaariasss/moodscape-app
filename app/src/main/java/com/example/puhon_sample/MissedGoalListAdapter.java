package com.example.puhon_sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MissedGoalListAdapter extends RecyclerView.Adapter<MissedGoalListAdapter.ViewHolder> {
    ArrayList<String> arrayListMember;

    public MissedGoalListAdapter(ArrayList<String> arrayListMember) {
        this.arrayListMember = arrayListMember;
    }

    @NonNull
    @Override
    public MissedGoalListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_goal_list, parent,false);
        return new MissedGoalListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MissedGoalListAdapter.ViewHolder holder, int position) {
        holder.completedGoalsList.setText(arrayListMember.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayListMember.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView completedGoalsList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            completedGoalsList = itemView.findViewById(R.id.completedGoalsList);
        }
    }
}
