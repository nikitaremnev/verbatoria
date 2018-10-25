package com.verbatoria.business.schedule.datasource;

import com.verbatoria.business.schedule.models.ScheduleItemModel;
import com.verbatoria.utils.DateUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

import static com.verbatoria.utils.LocaleHelper.LOCALE_RU;

/**
 * @author nikitaremnev
 */

public class ScheduleDataSource implements IScheduleDataSource<String, Date, Date, ScheduleItemModel> {

    private static final int ROWS_COUNT = 8;
    private static final int START_HOUR = 8;
    private static final int END_HOUR = 19;
    private static final int WEEK_COUNT = 7;
    private static final int FIRST_DAY_OF_WEEK = 1;
    private static final int COLUMN_COUNT = END_HOUR - START_HOUR + 1;

    private final Map<Integer, List<ScheduleItemModel>> mItems = new WeakHashMap<>();

    private Calendar mCalendar = Calendar.getInstance(new Locale(LOCALE_RU));

    private Calendar mOriginalCalendar;

    public ScheduleDataSource() {
        mOriginalCalendar = Calendar.getInstance(new Locale(LOCALE_RU));
    }

    public ScheduleDataSource(Calendar calendar) {
        mOriginalCalendar = calendar;
        int dayOfWeekIndex = 1;
        while (dayOfWeekIndex < ROWS_COUNT) {
            List<ScheduleItemModel> dayGenerated = new ArrayList<>();
            for (int i = START_HOUR; i < END_HOUR; i++) {
                dayGenerated.add(new ScheduleItemModel());
            }
            mItems.put(dayOfWeekIndex, dayGenerated);
            dayOfWeekIndex ++;
        }
    }

    @Override
    public int getRowsCount() {
        return ROWS_COUNT;
    }

    @Override
    public int getColumnsCount() {
        return COLUMN_COUNT;
    }

    @Override
    public String getFirstHeaderData() {
        return null;
    }

    @Override
    public Date getRowHeaderData(int index) {
        mCalendar = Calendar.getInstance(new Locale(LOCALE_RU));
        mCalendar.setTime(mOriginalCalendar.getTime());
        int firstDayOfWeek = getFirstDayOfWeek();
        mCalendar.set(Calendar.DAY_OF_YEAR, mCalendar.get(Calendar.DAY_OF_YEAR) - (getCurrentDayOfWeek() - firstDayOfWeek - (index - 1)));
        return mCalendar.getTime();
    }

    @Override
    public Date getColumnHeaderData(int index) {
        mCalendar.set(Calendar.HOUR_OF_DAY, START_HOUR + index - 1);
        mCalendar.set(Calendar.MINUTE, 0);
        return mCalendar.getTime();
    }

    @Override
    public ScheduleItemModel getItemData(int rowIndex, int columnIndex) {
        return mItems.get(rowIndex).get(columnIndex - 1);
    }

    @Override
    public Date getNextWeekStart() {
        mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) + WEEK_COUNT);
        Date result = getWeekStart();
        mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) - WEEK_COUNT);
        return result;
    }

    @Override
    public Date getNextWeekFinish() {
        mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) + WEEK_COUNT);
        Date result = getWeekEnd();
        mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) - WEEK_COUNT);
        return result;
    }

    @Override
    public Date getPreviousWeekStart() {
        mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) - WEEK_COUNT);
        Date result = getWeekStart();
        mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) + WEEK_COUNT);
        return result;
    }

    @Override
    public Date getPreviousWeekFinish() {
        mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) - WEEK_COUNT);
        Date result = getWeekEnd();
        mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) + WEEK_COUNT);
        return result;
    }

    @Override
    public String getWeekTitle() {
        return DateUtils.datePeriodToString(getRowHeaderData(FIRST_DAY_OF_WEEK), getRowHeaderData(ROWS_COUNT - 1));
    }

    @Override
    public void clearSchedule() {
        int dayOfWeekIndex = FIRST_DAY_OF_WEEK;
        while (dayOfWeekIndex < ROWS_COUNT) {
            List<ScheduleItemModel> scheduleItemModelList = mItems.get(dayOfWeekIndex);
            for (int i = 0; i < scheduleItemModelList.size(); i++) {
                scheduleItemModelList.get(i).setSelected(false);
            }
            dayOfWeekIndex ++;
        }
    }

    @Override
    public Date getWeekStart() {
        mCalendar = Calendar.getInstance(new Locale(LOCALE_RU));
        mCalendar.setTime(mOriginalCalendar.getTime());
        int firstDayOfWeek = getFirstDayOfWeek();
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.DAY_OF_YEAR, mCalendar.get(Calendar.DAY_OF_YEAR) - (getCurrentDayOfWeek() - firstDayOfWeek));
        return mCalendar.getTime();
    }

    @Override
    public Date getWeekEnd() {
        mCalendar = Calendar.getInstance(new Locale(LOCALE_RU));
        mCalendar.setTime(mOriginalCalendar.getTime());
        int firstDayOfWeek = getFirstDayOfWeek();
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.DAY_OF_YEAR, mCalendar.get(Calendar.DAY_OF_YEAR) - (getCurrentDayOfWeek() - firstDayOfWeek - WEEK_COUNT));
        return mCalendar.getTime();
    }

    @Override
    public Date getDeleteBorder(int weeksForwardCount) {
        mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) + WEEK_COUNT * weeksForwardCount);
        Date result = getWeekEnd();
        mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) - WEEK_COUNT * weeksForwardCount);
        return result;
    }

    @Override
    public Calendar getOriginalCalendar() {
        return mOriginalCalendar;
    }

    @Override
    public void setWorkingInterval(Date date) {
        mCalendar = Calendar.getInstance(new Locale(LOCALE_RU));
        mCalendar.setTime(date);
        int currentHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        if (isHourInBorders(currentHour)) {
            mItems.get(getCurrentDayOfWeek()).get(currentHour - START_HOUR).setSelected(true);
        }
    }

    @Override
    public Map<Date, List<Date>> getItems(boolean selected) {
        int dayOfWeekIndex = FIRST_DAY_OF_WEEK;
        Map<Date, List<Date>> resultItems = new HashMap<>();
        while (dayOfWeekIndex < ROWS_COUNT) {
            List<ScheduleItemModel> scheduleItemModelList = mItems.get(dayOfWeekIndex);
            List<Date> subItems = new ArrayList<>();
            for (int i = 0; i < scheduleItemModelList.size(); i++) {
                if (scheduleItemModelList.get(i).isSelected() == selected) {
                    subItems.add(getColumnHeaderData(i + 1));
                }
            }
            resultItems.put(getRowHeaderData(dayOfWeekIndex), subItems);
            dayOfWeekIndex ++;
        }
        return resultItems;
    }

    @Override
    public Map<Date, List<Date>> getItems(boolean selected, int nextWeeksCount) {
        Map<Date, List<Date>> resultItems = new HashMap<>();
        for (int weekForward = 0; weekForward < nextWeeksCount + 1; weekForward ++) {
            mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) + WEEK_COUNT * weekForward);
            int dayOfWeekIndex = FIRST_DAY_OF_WEEK;
            while (dayOfWeekIndex < ROWS_COUNT) {
                List<ScheduleItemModel> scheduleItemModelList = mItems.get(dayOfWeekIndex);
                List<Date> subItems = new ArrayList<>();
                for (int i = 0; i < scheduleItemModelList.size(); i++) {
                    if (scheduleItemModelList.get(i).isSelected() == selected) {
                        subItems.add(getColumnHeaderData(i + 1));
                    }
                }
                resultItems.put(getRowHeaderData(dayOfWeekIndex), subItems);
                dayOfWeekIndex ++;
            }
            mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) - WEEK_COUNT * weekForward);
        }
        return resultItems;
    }

    private int getFirstDayOfWeek() {
        return mCalendar.getFirstDayOfWeek() - 1;
    }

    private int getCurrentDayOfWeek() {
        return mCalendar.get(Calendar.DAY_OF_WEEK) == FIRST_DAY_OF_WEEK ? WEEK_COUNT : mCalendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    private boolean isHourInBorders(int hour) {
        return hour >= START_HOUR && hour < END_HOUR;
    }

}
