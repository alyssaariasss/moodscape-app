package com.example.puhon_sample;

public class User {

    String userFirstName;
    String userLastName;
    String userAge;
    String userEmail;
    String userPassword;

    public User() {

    }

    public User(String userFirstName, String userLastName, String userAge, String userEmail, String userPassword) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userAge = userAge;
        this.userEmail = userEmail;
        this.userPassword = userPassword;

    }

    public String getUserFirstName() {
        return userFirstName;
    }
    public String getUserLastName(){
        return userLastName;
    }
    public String getUserAge() {
        return userAge;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getUserPassword() { return userPassword;

    }
}
