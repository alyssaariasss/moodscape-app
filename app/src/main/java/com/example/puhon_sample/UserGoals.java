package com.example.puhon_sample;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class UserGoals implements Serializable {
    @Exclude
    int goalId, status;

    String goal, deadline, date;

    public UserGoals() {

    }

    public UserGoals(int id, int status, String goal, String deadline, String date) {
        this.goalId = id;
        this.status = status;
        this.goal = goal;
        this.deadline = deadline;
        this.date = date;
    }

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int id) {
        this.goalId = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
