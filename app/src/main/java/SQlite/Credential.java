package SQlite;

import java.util.Date;

public class Credential {
    Integer id = 0;
    User userid = null;
    String name;
    String hash;
    Date signupdate;



    public Credential(String name, Date signUpDate,String hash)
    {
        this.name = name;
        this.signupdate = signUpDate;
        this.hash = hash;
    }

    public void setID(int id)
    {
        this.id = id;
    }

    public  void setUserid(User userid)
    {
        this.userid = userid;
    }
}