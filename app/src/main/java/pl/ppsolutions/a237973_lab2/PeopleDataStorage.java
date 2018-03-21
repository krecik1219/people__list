package pl.ppsolutions.a237973_lab2;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import pl.ppsolutions.a237973_lab2.storage.PeopleDatabase;
import pl.ppsolutions.a237973_lab2.storage.Person;
import pl.ppsolutions.a237973_lab2.storage.PersonDAO;

/**
 * Created by Kretek on 20/03/2018.
 */

public class PeopleDataStorage
{
    private static final String TAG = PeopleDataStorage.class.getSimpleName();
    private static final String DIRECTORY_NAME = "people_photos";
    private static final String DB_NAME = "people_db";
    private static final String PHOTO_NAME_PATTERN = "person_photo%d.png";

    private Context context;

    public PeopleDataStorage(Context context)
    {
        this.context = context;
    }

    public void removePerson(PersonData personData)
    {
        long id = personData.getId();
        Log.d(TAG, "id of person being removed: "+id);
        PeopleDatabase db = Room.databaseBuilder(context, PeopleDatabase.class, DB_NAME).build();
        PersonDAO personDAO = db.personDAO();
        personDAO.removePerson(id);
        db.close();
        File directory = context.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE);
        String fileName = String.format(Locale.US, PHOTO_NAME_PATTERN, id);
        File photoFile = new File(directory, fileName);
        photoFile.delete();
    }

    public List<PersonData> readPeopleData()
    {
        PeopleDatabase db = Room.databaseBuilder(context, PeopleDatabase.class, DB_NAME).build();
        PersonDAO personDAO = db.personDAO();
        List<Person> personListDB = personDAO.getAllPeople();
        db.close();
        List<PersonData> peopleList = new ArrayList<PersonData>(personListDB.size());
        Person auxHolder = null;
        for(Iterator<Person> it = personListDB.iterator(); it.hasNext(); )
        {
            auxHolder = it.next();
            peopleList.add(new PersonData(
                    auxHolder.getPersonId(),
                    auxHolder.getName(),
                    auxHolder.getSurname(),
                    auxHolder.getBirthDayDate(),
                    getBitmapFromFile(auxHolder.getPhotoPath())
            ));
        }

        return peopleList;
    }

    public void storePersonData(PersonData personData)
    {
        String personName = personData.getName();
        String personSurname = personData.getSurname();
        Date personBirthDate = personData.getBirthDate();
        Bitmap personPhoto = personData.getPhoto();
        Person person = new Person();
        person.setName(personName);
        person.setSurname(personSurname);
        person.setBirthDayDate(personBirthDate);
        PeopleDatabase db = Room.databaseBuilder(context, PeopleDatabase.class, DB_NAME).build();
        PersonDAO personDAO = db.personDAO();

        long insertedId = personDAO.insertSinglePerson(person);
        String photoPath = savePersonPhoto(insertedId, personPhoto);
        personDAO.updatePhotoPath(photoPath, insertedId);
        db.close();
        personData.setId(insertedId);
        Log.d(TAG, "after insert: id: "+personData.getId()+" ; Name: "+personName+" ; Surname: "+personSurname+" ; Photo path: "+photoPath);
    }

    public void storePeopleData(List<PersonData> peopleList)
    {
        for(Iterator<PersonData> it = peopleList.iterator(); it.hasNext(); )
        {
            storePersonData(it.next());
        }
    }

    public void clearAllData()
    {
        PeopleDatabase db = Room.databaseBuilder(context, PeopleDatabase.class, DB_NAME).build();
        PersonDAO personDAO = db.personDAO();
        personDAO.clearPersonTable();
        db.close();
        removeAllPhotos();
    }

    private void removeAllPhotos()
    {
        File directory = context.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE);
        File[] photoFiles = directory.listFiles();
        for(int i=0; i<photoFiles.length; i++)
            photoFiles[i].delete();
    }

    private String savePersonPhoto(long personId, @NonNull Bitmap photo)
    {
        File directory = context.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE);
        String fileName = String.format(Locale.US, PHOTO_NAME_PATTERN, personId);
        File myFile = new File(directory, fileName);
        try(FileOutputStream output = new FileOutputStream(myFile))
        {
            photo.compress(Bitmap.CompressFormat.PNG, 100, output);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
       return  myFile.getAbsolutePath();
    }

    private Bitmap getBitmapFromFile(String path)
    {
        return BitmapFactory.decodeFile(path);
    }
}
