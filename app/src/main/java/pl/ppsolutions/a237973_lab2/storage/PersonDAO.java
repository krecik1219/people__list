package pl.ppsolutions.a237973_lab2.storage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Kretek on 20/03/2018.
 */

@Dao
public interface PersonDAO
{
    @Insert
    Long insertSinglePerson(Person person);

    @Query("SELECT * FROM Person")
    List<Person> getAllPeople();

    @Query("UPDATE Person SET photo_path = :path WHERE id_person = :personId")
    void updatePhotoPath(String path, Long personId);

    @Query("DELETE FROM Person WHERE id_person = :personId")
    void removePerson(Long personId);

    @Query("DELETE FROM Person")
    void clearPersonTable();
}
