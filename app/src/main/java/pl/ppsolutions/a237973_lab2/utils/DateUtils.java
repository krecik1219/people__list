package pl.ppsolutions.a237973_lab2.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kretek on 19/03/2018.
 */

public class DateUtils
{
    private static Calendar getCalendarWithDate(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static int getDiffInYearsTwoDates(Date first, Date later)
    {
        Calendar calFirst = getCalendarWithDate(first);
        Calendar calLater = getCalendarWithDate(later);
        return getDiffInYears(calFirst, calLater);
    }

    public static int getDiffInYearsByToday(Date older)
    {
        Calendar calFirst = getCalendarWithDate(older);
        Calendar today  = Calendar.getInstance();
        return getDiffInYears(calFirst, today);
    }

    public static boolean notAfterToday(Date date)
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(year, month, day,0,0,0);
        Date today = cal.getTime();
        return !date.after(today);
    }

    private static int getDiffInYears(Calendar calFirst, Calendar calLater)
    {
        int yearsDiff = calLater.get(Calendar.YEAR) - calFirst.get(Calendar.YEAR);
        int firstMonth = calFirst.get(Calendar.MONTH);
        int laterMonth = calLater.get(Calendar.MONTH);
        if(firstMonth > laterMonth || firstMonth == laterMonth && calFirst.get(Calendar.DAY_OF_MONTH) > calLater.get(Calendar.DAY_OF_MONTH))
            yearsDiff--;
        return yearsDiff;
    }
}
