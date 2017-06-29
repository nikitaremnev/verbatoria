package com.verbatoria.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Утилиты для работы с датами
 *
 * @author nikitaremnev
 */
public class DateUtils {

    private static final long MILLIS_PER_SECOND = 1000;
    private static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    private static final long MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    private static final long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

    private static final SimpleDateFormat SERVER_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ROOT);

    public static boolean isCurrentYear(long millis) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar tempCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(System.currentTimeMillis());
        tempCalendar.setTimeInMillis(millis);
        return currentCalendar.get(Calendar.YEAR) == tempCalendar.get(Calendar.YEAR);
    }

    public static boolean isToday(long millis) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar tempCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(System.currentTimeMillis());
        tempCalendar.setTimeInMillis(millis);
        return currentCalendar.get(Calendar.YEAR) == tempCalendar.get(Calendar.YEAR)
                && currentCalendar.get(Calendar.DAY_OF_YEAR) == tempCalendar.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isYesterday(long millis) {
        Calendar yesterdayCalendar = Calendar.getInstance();
        Calendar tempCalendar = Calendar.getInstance();
        yesterdayCalendar.setTimeInMillis(System.currentTimeMillis() - android.text.format.DateUtils.DAY_IN_MILLIS);
        tempCalendar.setTimeInMillis(millis);
        return yesterdayCalendar.get(Calendar.YEAR) == tempCalendar.get(Calendar.YEAR)
                && yesterdayCalendar.get(Calendar.DAY_OF_YEAR) == tempCalendar.get(Calendar.DAY_OF_YEAR);
    }

    public static Date parseDate(String dateString) throws ParseException {
        return SERVER_DATE_FORMAT.parse(dateString);
    }

    public static String toString(Date date) throws ParseException {
        return SERVER_DATE_FORMAT.format(date);
    }
}
