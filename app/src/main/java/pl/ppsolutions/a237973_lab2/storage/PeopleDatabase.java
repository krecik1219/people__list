package pl.ppsolutions.a237973_lab2.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by Kretek on 20/03/2018.
 */

@Database(entities = {Person.class}, version = 1, exportSchema = false)
@TypeConverters(value = {Converters.class})
public abstract class PeopleDatabase extends RoomDatabase
{
    public abstract PersonDAO personDAO();
}
