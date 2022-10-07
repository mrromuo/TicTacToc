package com.raeed.tictactoc.sideClasses;

public class Frinds {
    private String User_name;
    private String User_Id;
    private String Last_Date;
    private String Last_Time;
    private String CurrentStates;

    void Frinds(){}

    public Frinds(String user_name, String user_Id, String last_Date, String last_Time, String currentStates) {
        User_name = user_name;
        User_Id = user_Id;
        Last_Date = last_Date;
        Last_Time = last_Time;
        CurrentStates = currentStates;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getLast_Date() {
        return Last_Date;
    }

    public void setLast_Date(String last_Date) {
        Last_Date = last_Date;
    }

    public String getLast_Time() {
        return Last_Time;
    }

    public void setLast_Time(String last_Time) {
        Last_Time = last_Time;
    }

    public String getCurrentStates() {
        return CurrentStates;
    }

    public void setCurrentStates(String currentStates) {
        CurrentStates = currentStates;
    }
}
