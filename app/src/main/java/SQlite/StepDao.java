package SQlite;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.Date;
import java.util.List;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
@Dao
public interface StepDao {
    @Query("SELECT * FROM step")
    List<Step> getAll();
    @Query("SELECT * FROM step where name = :name")
    List<Step> getByName(String name);
    @Query("SELECT * FROM step where date = :date")
    List<Step> getByDate(String date);
    @Query("SELECT * FROM step where step = :step")
    List<Step> getByDate(Integer step);
    @Query("SELECT SUM(step) from step where date = :date and name = :name")
    int getTotalByDate(String date, String name);
    @Query("select sum(step) from step where date = :date and name = :name")
    int getTotalStepThatDay(String date, String name);
    @Query("SELECT count(*) FROM step where name = :name")
    int checkByName(String name);

    @Insert
    void insertAll(Step... steps);
    @Insert
    long insert(Step step);
    @Delete
    void delete(Step step);
    @Query("DELETE FROM STEP WHERE name = :name ")
    void deleteByName(String  name);
    @Update(onConflict = REPLACE)
    public void updateUsers(Step... steps);
    @Query("DELETE FROM step")
    void deleteAll();
}
