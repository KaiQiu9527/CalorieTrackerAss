package com.example.calorietrackerass;

import java.util.Date;

import SQlite.Food;
import SQlite.User;

class Consumption {
    int id;
    User userid;
    Food foodid;
    double amount;
    Date date;

    public Consumption(User userid, Food foodid, double amount, Date date){
        this.userid = userid;
        this.foodid = foodid;
        this.amount = amount;
        this.date = date;
    }

    public void setId(int id)
    {
        this.id = id;
    }

}
