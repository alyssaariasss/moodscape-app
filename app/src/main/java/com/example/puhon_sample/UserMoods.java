package com.example.puhon_sample;

public class UserMoods {

    private String mood, date;

    public UserMoods(){

    }

    public UserMoods(String mood, String date) {
        this.mood = mood;
        this.date = date;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
