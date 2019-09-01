package SQlite;

import android.icu.lang.UScript;

import java.util.Date;

public class User {
    Integer id = 0;
    String name = "";
    String surname = "";
    String email = "";
    Double height = 0.0;
    Double weight = 0.0;
    String address = "";
    Integer postcode = 0;
    Integer level = 0;
    Integer stepPerMile;
    String gender;
    Date dob;


    public User(String name,String surname,String email,Double height,Double weight,String address,Integer postcode,Integer level,Integer stepPerMile,String gender,Date dob)
    {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.height = height;
        this.weight = weight;
        this.postcode = postcode;
        this.dob = dob;
        this.gender = gender;
        this.level = level;
        this.stepPerMile = stepPerMile;
    }

    public User(int id)
    {
        this.id = id;
    }

    public void setID(int id)
    {
        this.id = id;
    }
}
