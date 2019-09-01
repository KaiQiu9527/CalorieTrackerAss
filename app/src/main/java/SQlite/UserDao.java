/*package SQlite;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();
    @Query("SELECT * FROM user where name = :name")
    List<User> getByAName(String name);
    @Query("SELECT * FROM user where surname = :surname")
    List<User> getBySurname(String surname);
    @Query("SELECT * FROM user where email = :email")
    List<User> getByEmail(String email);
    @Query("SELECT * FROM user where address = :address")
    List<User> getByAddress(String address);
    @Query("SELECT * FROM user where postcode = :postcode")
    List<User> getByPostcode(Integer postcode);
    @Query("SELECT * FROM user where level = :level")
    List<User> getByLevel(Integer level);
    @Query("SELECT * FROM user where stepPerMile = :stepPerMile")
    List<User> getByStepPerMile(Integer stepPerMile);
    @Query("SELECT * FROM user where gender = :gender")
    List<User> getByGender(String gender);
    @Query("SELECT * FROM user where dob = :dob")
    List<User> getByDob(String dob);
    @Insert
    void insertAll(User... users);
    @Insert
    long insert(User user);
    @Delete
    void delete(User User);
    @Update(onConflict = REPLACE)
    public void updateuser(User... users);
    @Query("DELETE FROM user")
    void deleteAll();
*/