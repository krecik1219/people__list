package pl.ppsolutions.a237973_lab2.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import pl.ppsolutions.a237973_lab2.PersonData;
import pl.ppsolutions.a237973_lab2.R;

/**
 * Created by Kretek on 19/03/2018.
 */

public class SamplePeopleDataProvider
{
    private static SamplePeopleDataProvider instance = null;
    private ArrayList<PersonData> peopleList;
    private static Context context;
    private SamplePeopleDataProvider(){initializePeopleList();}
    private static final int SAMPLE_DATA_SIZE = 100;

    public static SamplePeopleDataProvider getInstance(Context context)
    {
        SamplePeopleDataProvider.context = context;
        if(instance == null)
            instance = new SamplePeopleDataProvider();
        return instance;
    }

    public ArrayList<PersonData> getPeopleSampleData()
    {
        return peopleList;
    }

    private void initializePeopleList()
    {
        Bitmap photoInCommon = BitmapFactory.decodeResource(context.getResources(), R.drawable.user_default_portrait);
        peopleList = new ArrayList<PersonData>(SAMPLE_DATA_SIZE);
        PersonData auxPersonData = null;
        String [] names = context.getResources().getStringArray(R.array.names);
        String [] surnames = context.getResources().getStringArray(R.array.surnames);
        for(int i=0; i<SAMPLE_DATA_SIZE; i++)
        {
            auxPersonData = new PersonData(names[i], surnames[i], getRandomDate(), photoInCommon);
            peopleList.add(auxPersonData);
        }
    }

    private Date getDate(int year, int month, int day)
    {
        Calendar cal = Calendar.getInstance();
        month--;
        cal.set(year, month, day, 0, 0, 0);
        return cal.getTime();
    }

    private Date getRandomDate()
    {
        Random generator = new Random();
        int year = generator.nextInt(55)+1950;
        int month = generator.nextInt(12)+1;
        int day = generator.nextInt(28)+1;
        return getDate(year, month, day);
    }
}
