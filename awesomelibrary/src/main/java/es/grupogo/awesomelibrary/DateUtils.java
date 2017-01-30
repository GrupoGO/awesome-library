package es.grupogo.awesomelibrary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Carlos Olmedo on 26/1/17.
 */

public class DateUtils {

    public int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public int getMonth(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month;
    }

    public int getYear(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    public long getDifferenceInSeconds(Date d1, Date d2) {

        long diff = d2.getTime() - d1.getTime();
        return diff / 1000;
    }

    public long getDifferenceInMinutes(Date d1, Date d2) {

        return getDifferenceInSeconds(d1, d2) / 60;
    }

    public long getDifferenceInHours(Date d1, Date d2) {

        return getDifferenceInMinutes(d1, d2) / 60;
    }

    public long getDifferenceInDays(Date d1, Date d2) {

        return getDifferenceInHours(d1, d2) / 24;
    }

    public long getDifferenceInMonths(Date d1, Date d2) {

        long days = getDifferenceInDays(d1, d2);
        return days / 30;
    }

    public long getDifferenceInYears(Date d1, Date d2) {

        return getDifferenceInDays(d1, d2) / 365;
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern, Locale.US);
        return dateFormatter.format(date.getTime());
    }


}
