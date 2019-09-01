/*
package SQlite;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "username")
    private  String username;

    @ColumnInfo(name = "name")
    private String name = "";
    @ColumnInfo(name = "surname")
    private String surname = "";
    @ColumnInfo(name = "email")
    private String email = "";
    @ColumnInfo(name = "height")
    private double height = 0;
    @ColumnInfo(name = "weight")
    private double weight = 0;
    @ColumnInfo(name = "address")
    private String address = "";
    @ColumnInfo(name = "postcode")
    private Integer postcode = 0;
    @ColumnInfo(name = "level")
    private Integer level = 0;
    @ColumnInfo(name = "stepPerMile")
    private Integer stepPerMile = 0;
    @ColumnInfo(name = "gender")
    private String gender = "";
    @ColumnInfo(name = "dob")
    private String dob = "0000-00-00";

    public Date setDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date realDate = sdf.parse(date);
            return realDate;
        } catch (ParseException e) {
            e.printStackTrace();

        }

        return null;
    }

    public void setId(Integer a)
    {
        this.id = a;
    }

    public Integer getId()
    {
        return id;
    }

    public void setName(String a)
    {
        this.name = a;
    }

    public String getName()
    {
        return name;
    }

    public void setSurname(String a)
    {
        this.surname = a;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setEmail(String a)
    {
        this.email = a;
    }

    public String getEmail()
    {
        return email;
    }

    public void setHeight(Double a)
    {
        this.height = a;
    }

    public Double getHeight()
    {
        return height;
    }

    public void setWeight(Double a)
    {
        this.weight = a;
    }

    public Double getWeight()
    {
        return weight;
    }

    public void setAddress(String a)
    {
        this.address = a;
    }

    public String getAddress()
    {
        return address;
    }

    public void setPostcode(Integer a)
    {
        this.postcode = a;
    }

    public Integer getPostcode()
    {
        return postcode;
    }

    public void setLevel(Integer a)
    {
        this.level = a;
    }

    public Integer getLevel()
    {
        return level;
    }

    public void setStepPerMile(Integer a)
    {
        this.stepPerMile = a;
    }

    public Integer getStepPerMile()
    {
        return stepPerMile;
    }

    public void setGender(String a)
    {
        this.gender = a.substring(0,1);
    }

    public String getGender()
    {
        return gender;
    }

    public void setDob(String a)
    {
        this.dob = a;
    }

    public String getDob()
    {
        return dob;
    }



}
*/


