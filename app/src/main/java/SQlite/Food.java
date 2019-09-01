package SQlite;

public class Food {
    int id = 0;
    String category;
    String name;
    Double calamount;
    Double fat;
    String servingunit = "cup";
    double servingamount = 1;

    public Food(String category,String name, Double calamount, Double fat)
    {
        this.category = category;
        this.name = name;
        this.calamount = calamount;
        this.fat = fat;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
