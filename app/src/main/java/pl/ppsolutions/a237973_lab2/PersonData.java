package pl.ppsolutions.a237973_lab2;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Kretek on 19/03/2018.
 */

public class PersonData
{
    private long id;
    private String name;
    private String surname;
    private Date birthDate;
    private Bitmap photo;

    public PersonData(long id, String name, String surname, Date birthDate, Bitmap photo)
    {
        this.id=id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.photo = photo;
    }

    public PersonData(String name, String surname, Date birthDate, Bitmap photo)
    {
        this(0, name, surname, birthDate, photo);
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public Bitmap getPhoto()
    {
        return photo;
    }

    public void setPhoto(Bitmap photo)
    {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof PersonData)
        {
            PersonData personData = (PersonData) o;
            return this.id == personData.id;
        }
        else
            return false;
    }

    @Override
    public int hashCode()
    {
        return (int) id;
    }
}
