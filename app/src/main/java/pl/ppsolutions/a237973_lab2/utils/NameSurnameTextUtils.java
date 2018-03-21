package pl.ppsolutions.a237973_lab2.utils;

import android.support.annotation.NonNull;

/**
 * Created by Kretek on 21/03/2018.
 */

public class NameSurnameTextUtils
{
    public static boolean isDigitsFree(@NonNull String text)
    {
        return text.matches("\\D+");
    }

    @NonNull
    public static String capitalizeFirstLetter(@NonNull String text)
    {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    @NonNull
    public static String skipLeadingAndTrailingWhitespaces(@NonNull String text)
    {
        return text.trim();
    }
}
