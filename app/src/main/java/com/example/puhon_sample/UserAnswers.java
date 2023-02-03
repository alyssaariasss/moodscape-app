package com.example.puhon_sample;

public class UserAnswers {

    String UserQuestion1, date, UserQuestion2_1, UserQuestion2_2, UserQuestion3;
    Integer UserQuestion4, UserQuestion5;

    public UserAnswers() {

    }

    public UserAnswers(String question1, String question2_1, String question2_2, String question3,
                       Integer question4, Integer question5) {
        UserQuestion1 = question1;
        UserQuestion2_1 = question2_1;
        UserQuestion2_2 = question2_2;
        UserQuestion3 = question3;
        UserQuestion4 = question4;
        UserQuestion5 = question5;
        this.date = date;
    }


    // getters
    public String getUserQuestion1() {return UserQuestion1; }
    public String getUserQuestion2_1() {return UserQuestion2_1; }
    public String getUserQuestion2_2() {return UserQuestion2_2; }
    public String getUserQuestion3() {return UserQuestion3; }
    public Integer getUserQuestion4() {return UserQuestion4; }
    public Integer getUserQuestion5() {return UserQuestion5; }
    public String getDate() {
        return date;
    }

    // setters
    public void setUserQuestion1(String question1) {UserQuestion1 = question1; }
    public void setUserQuestion2_1(String question2_1) {UserQuestion2_1 = question2_1; }
    public void setUserQuestion2_2(String question2_2) {UserQuestion2_2 = question2_2; }
    public void setUserQuestion3(String question3) {UserQuestion3 = question3; }
    public void setUserQuestion4(Integer question4) {UserQuestion4 = question4; }
    public void setUserQuestion5(Integer question5) {UserQuestion5 = question5; }
    public void setDate(String date) {
        this.date = date;
    }


}

