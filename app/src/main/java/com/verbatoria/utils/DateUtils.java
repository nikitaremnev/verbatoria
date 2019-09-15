package com.verbatoria.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Утилиты для работы с датами
 *
 * @author nikitaremnev
 */
public class DateUtils {

    public static final long MILLIS_PER_SECOND = 1000;

    private static final SimpleDateFormat SERVER_FILENAME_FORMAT = new SimpleDateFormat("dd.MM_HH:mm", Locale.ROOT);
    private static final SimpleDateFormat SERVER_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ", Locale.ROOT);
    private static final SimpleDateFormat SERVER_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
    private static final SimpleDateFormat SERVER_DATE_WITHOUT_YEAR_FORMAT = new SimpleDateFormat("dd.MM", Locale.ROOT);
    private static final SimpleDateFormat TIMER_TIME_FORMAT = new SimpleDateFormat("mm:ss", Locale.ROOT);

    public static Date parseDateTime(String dateString) throws ParseException {
        return SERVER_DATETIME_FORMAT.parse(dateString);
    }

    public static String toServerDateTimeString(long timestamp) throws ParseException {
        return SERVER_DATETIME_FORMAT.format(timestamp * MILLIS_PER_SECOND);
    }

    public static String toServerDateTimeWithoutConvertingString(long timestamp) throws ParseException {
        return SERVER_DATETIME_FORMAT.format(timestamp);
    }

    public static String toString(Date date) {
        return SERVER_DATE_FORMAT.format(date);
    }

    public static String fileNameFromDate(Date date) {
        return SERVER_FILENAME_FORMAT.format(date);
    }

    public static String timeToString(Date date) {
        return TIMER_TIME_FORMAT.format(date);
    }

    public static String datePeriodToString(Date startDate, Date endDate) {
        return SERVER_DATE_WITHOUT_YEAR_FORMAT.format(startDate) +
                " - " +
                SERVER_DATE_WITHOUT_YEAR_FORMAT.format(endDate);
    }

}
