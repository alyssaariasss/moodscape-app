package com.example.puhon_sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.GoalsViewHolder> {
    static ArrayList<UserGoals> UserGoals;
    static FragmentToday fragmentToday;

    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id;

    public GoalsAdapter(FragmentToday fragmentToday, ArrayList<UserGoals> UserGoals) {
        this.fragmentToday = fragmentToday;
        this.UserGoals = UserGoals;
    }

    @NonNull
    @Override
    public GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoalsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.today_goal_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsViewHolder holder, int position) {
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;
        id = user.getUid();

        reference = database.getReference().child("users").child(id).child("UserGoals");

        UserGoals userGoals = UserGoals.get(position);
        holder.goalsCheckbox.setText(userGoals.goal);
        holder.time.setText(userGoals.deadline);

        holder.goalsCheckbox.setChecked(toBoolean(userGoals.getStatus()));

        holder.goalsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    // Status = 1 if goal is checked
                    reference.child(String.valueOf(userGoals.getGoalId())).child("status").setValue(1);
                } else {
                    // Status = 0 if goal is unchecked
                    reference.child(String.valueOf(userGoals.getGoalId())).child("status").setValue(0);
                }
            }
        });
    }

    public FragmentToday getContext() {
        return fragmentToday;
    }

    public void setTasks(ArrayList<UserGoals> userGoals) {
        this.UserGoals = userGoals;
        notifyDataSetChanged();
    }

    public static void editItem(int position) {
        UserGoals userGoals = UserGoals.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("goalId", userGoals.getGoalId());
        bundle.putString("goal", userGoals.getGoal());
        bundle.putString("time", userGoals.getDeadline());
        AddNewGoal fragment = new AddNewGoal();
        fragment.setArguments(bundle);
        fragment.show(fragmentToday.getParentFragmentManager(), AddNewGoal.TAG);
    }

    private boolean toBoolean(int s) {
        return s != 0;
    }

    @Override
    public int getItemCount() {
        return UserGoals.size() ;
    }

    static class GoalsViewHolder extends RecyclerView.ViewHolder {

        CheckBox goalsCheckbox;
        TextView time;

        public GoalsViewHolder(@NonNull View itemView) {
            super(itemView);

            goalsCheckbox = itemView.findViewById(R.id.goalsCheckbox);
            time = itemView.findViewById(R.id.time);

        }
    }
}