package com.example.puhon_sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CompletedGoalListAdapter extends RecyclerView.Adapter<CompletedGoalListAdapter.ViewHolder> {
    ArrayList<String> arrayListMember;

    public CompletedGoalListAdapter(ArrayList<String> arrayListMember) {
        this.arrayListMember = arrayListMember;
    }

    @NonNull
    @Override
    public CompletedGoalListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_goal_list, parent,false);
        return new CompletedGoalListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedGoalListAdapter.ViewHolder holder, int position) {
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
