package SQlite;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Step {
    @PrimaryKey(autoGenerate = true)
    Integer id;
    @ColumnInfo(name = "name")
    private String name ;
    @ColumnInfo(name = "step")
    private int step = 0;
    @ColumnInfo(name = "date")
    private String date;
//    @ColumnInfo(name = "calorie")
//    private double calorie;

//    public String convertTime(Date date)
//    {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String stringDate = sdf.format(date);
//        return  stringDate;
//    }

    public Step(){}

    public Step(String name, int step, Date date)
    {
        this.name = name;
        this.step = step;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.date = sdf.format(date);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setStep(Integer step)
    {
        this.step = step;
    }

    public Integer getStep()
    {
        return step;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getDate()
    {
        return date;
    }

//    public Double getCalorie(){return calorie;}
//
//    public void setCalorie( Double calorie ){ this.calorie = calorie;}


}
