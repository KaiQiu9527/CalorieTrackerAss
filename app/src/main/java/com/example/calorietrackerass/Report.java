package com.example.calorietrackerass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import SQlite.User;

public class Report {
    double burncal;
    double calgoal;
    double consumecal;
    Date date;
    int id ;
    int steptaken;
    User userid;

    public Report(double calgoal, double calburned, double calconsumed, int nowstep, User userid)
    {
        this.calgoal = calgoal;
        this.burncal = calburned;
        this.consumecal = calconsumed;
        this.steptaken = nowstep;
        this.userid = userid;
        date = Calendar.getInstance().getTime();
    }

    public void setId(int id)
    {
        this.id = id;
    }
    public  void setUserid(User userid)
    {
        this.userid = userid;
    }

}




