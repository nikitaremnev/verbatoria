package com.verbatoria.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Утилиты для работы с датами
 *
 * @author nikitaremnev
 */
public class DateUtils {

    public static final long MILLIS_PER_SECOND = 1000;
    private static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    private static final long MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    private static final long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

    private static final SimpleDateFormat SERVER_FILENAME_FORMAT = new SimpleDateFormat("dd.MM_HH:mm", Locale.ROOT);
    private static final SimpleDateFormat SERVER_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ROOT);
    private static final SimpleDateFormat SERVER_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
    private static final SimpleDateFormat SERVER_DATE_WITHOUT_YEAR_FORMAT = new SimpleDateFormat("dd.MM", Locale.ROOT);
    private static final SimpleDateFormat SERVER_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ROOT);
    private static final SimpleDateFormat TIMER_TIME_FORMAT = new SimpleDateFormat("mm:ss", Locale.ROOT);
    private static final SimpleDateFormat UI_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy", Locale.ROOT);

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

    public static Date parseDateTime(String dateString) throws ParseException {
        return SERVER_DATETIME_FORMAT.parse(dateString);
    }

    public static String toServerDateTimeString(long timestamp) throws ParseException {
        return SERVER_DATETIME_FORMAT.format(timestamp * MILLIS_PER_SECOND);
    }

    public static Date parseDate(String dateString) throws ParseException {
        return SERVER_DATE_FORMAT.parse(dateString);
    }

    public static String toString(Date date) {
        return SERVER_DATE_FORMAT.format(date);
    }

    public static String toUIDateString(Date date) {
        return UI_DATE_FORMAT.format(date);
    }

    public static String fileNameFromDate(Date date) {
        return SERVER_FILENAME_FORMAT.format(date);
    }

    public static String timeToString(Date date) {
        return TIMER_TIME_FORMAT.format(date);
    }

    public static String periodToString(Date startDate, Date endDate) {
        return SERVER_DATE_WITHOUT_YEAR_FORMAT.format(startDate) +
                " " +
                SERVER_TIME_FORMAT.format(startDate) +
                " - " +
                SERVER_TIME_FORMAT.format(endDate);
    }

    public static int getYearsBetweenDates(Date startDate, Date endDate) {
        Calendar firstCalendar = GregorianCalendar.getInstance();
        Calendar secondCalendar = GregorianCalendar.getInstance();
        firstCalendar.setTime(startDate);
        secondCalendar.setTime(endDate);
        secondCalendar.add(Calendar.DAY_OF_YEAR, - firstCalendar.get(Calendar.DAY_OF_YEAR));
        return secondCalendar.get(Calendar.YEAR) - firstCalendar.get(Calendar.YEAR);
    }

    public static int getYearsFromDate(Date date) {
        Calendar firstCalendar = GregorianCalendar.getInstance();
        Calendar secondCalendar = GregorianCalendar.getInstance();
        firstCalendar.setTime(date);
        secondCalendar.add(Calendar.DAY_OF_YEAR, - firstCalendar.get(Calendar.DAY_OF_YEAR));
        return secondCalendar.get(Calendar.YEAR) - firstCalendar.get(Calendar.YEAR);
    }
}
