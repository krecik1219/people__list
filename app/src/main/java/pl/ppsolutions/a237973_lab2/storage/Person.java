package pl.ppsolutions.a237973_lab2.storage;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ColumnInfo.NOCASE;

/**
 * Created by Kretek on 20/03/2018.
 */

@Entity
public class Person
{
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_person", collate = NOCASE)
    private Long personId;

    @NonNull
    @ColumnInfo(name = "name", collate = NOCASE)
    private String name;

    @NonNull
    @ColumnInfo(name = "surname", collate = NOCASE)
    private String surname;

    @NonNull
    @ColumnInfo(name = "birth_date")
    private Date birthDayDate;

    @ColumnInfo(name = "photo_path")
    private String photoPath;

    @NonNull
    public Long getPersonId()
    {
        return personId;
    }

    public void setPersonId(@NonNull Long personId)
    {
        this.personId = personId;
    }

    @NonNull
    public String getName()
    {
        return name;
    }

    public void setName(@NonNull String name)
    {
        this.name = name;
    }

    @NonNull
    public String getSurname()
    {
        return surname;
    }

    public void setSurname(@NonNull String surname)
    {
        this.surname = surname;
    }

    @NonNull
    public Date getBirthDayDate()
    {
        return birthDayDate;
    }

    public void setBirthDayDate(@NonNull Date birthDayDate)
    {
        this.birthDayDate = birthDayDate;
    }

    public String getPhotoPath()
    {
        return photoPath;
    }

    public void setPhotoPath(@NonNull String photoPath)
    {
        this.photoPath = photoPath;
    }
}
