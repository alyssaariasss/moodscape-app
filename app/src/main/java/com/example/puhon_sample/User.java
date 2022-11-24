package com.example.puhon_sample;

public class User {

    String userName;
    String userAge;
    String userEmail;
    String userPassword;
    String userPronouns;

    public User(){

    }

    public User(String userName, String userAge, String userPronouns, String userEmail, String userPassword) {
        this.userName = userName;
        this.userAge = userAge;
        this.userPronouns = userPronouns;
        this.userEmail = userEmail;
        this.userPassword = userPassword;

    }

    public String getUserName() {
        return userName;
    }
    public String getUserAge() {
        return userAge;
    }
    public String getUserPronouns(){
        return userPronouns;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getUserPassword() { return userPassword;

    }
}
