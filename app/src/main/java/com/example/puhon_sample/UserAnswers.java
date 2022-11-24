package com.example.puhon_sample;

public class UserAnswers {

    String UserWhere;
    String UserWho;
    String UserDo;
    String UserReason;
    String UserWhat;

    public UserAnswers(){

    }

    public UserAnswers(String userWhere, String userWho, String userDo, String userReason, String userWhat){
        UserWhere = userWhere;
        UserWho = userWho;
        UserDo = userDo;
        UserReason = userReason;
        UserWhat = userWhat;
    }

    // getters

    public String getUserWhere() { return UserWhere; }
    public String getUserWho() { return UserWho; }
    public String getUserDo() { return UserDo; }
    public String getUserReason() { return UserReason; }
    public String getUserWhat() { return UserWhat; }

    // setters

    public void setUserWhere(String userWhere) { UserWhere = userWhere; }
    public void setUserWho(String userWho) { UserWhere = userWho; }
    public void setUserDo(String userDo) { UserWhere = userDo; }
    public void setUserReason(String userReason) { UserWhere = userReason; }
    public void setUserWhat(String userWhat) { UserWhere = userWhat; }
}
