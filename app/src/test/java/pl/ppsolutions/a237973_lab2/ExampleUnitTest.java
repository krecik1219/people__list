package pl.ppsolutions.a237973_lab2;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import pl.ppsolutions.a237973_lab2.utils.DateUtils;
import pl.ppsolutions.a237973_lab2.utils.NameSurnameTextUtils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    @Test
    public void date_not_after_today_for_date_prior_today_returns_true()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1985, 5, 27);
        Date date = calendar.getTime();
        assertEquals(true, DateUtils.notAfterToday(date));
    }

    @Test
    public void date_not_after_today_for_today_date_returns_true()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, 0, 0, 0);
        Date date = calendar.getTime();
        assertEquals(true, DateUtils.notAfterToday(date));
    }

    @Test
    public void date_not_after_today_for_date_after_today_returns_false()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        year++;
        calendar.set(year, 5, 27);
        Date date = calendar.getTime();
        assertEquals(false, DateUtils.notAfterToday(date));
    }

    @Test
    public void first_letter_is_uppercase_after_capitalize_for_text_with_lower_first_char()
    {
        String text = "hello world";
        String capitalized = NameSurnameTextUtils.capitalizeFirstLetter(text);
        assertEquals(true, Character.isUpperCase(capitalized.charAt(0)));
    }

    @Test
    public void first_letter_is_uppercase_after_capitalize_for_text_with_upper_first_char()
    {
        String text = "Hello world";
        String capitalized = NameSurnameTextUtils.capitalizeFirstLetter(text);
        assertEquals(true, Character.isUpperCase(capitalized.charAt(0)));
    }

    @Test
    public void skipped_whitespaces_text_should_be_shorter()
    {
        String text = "  \thello world \n";
        int textLen = text.length();
        String skippedWhiteSpaces = NameSurnameTextUtils.skipLeadingAndTrailingWhitespaces(text);
        assertEquals(16-5, skippedWhiteSpaces.length());
    }

    @Test
    public void for_text_with_digits_should_return_false()
    {
        String text = " And3rzej  2";
        assertEquals(false, NameSurnameTextUtils.isDigitsFree(text));
    }

    @Test
    public void for_text_without_digits_should_return_true()
    {
        String text = " O'Rilley - Mąka";
        assertEquals(true, NameSurnameTextUtils.isDigitsFree(text));
    }
}