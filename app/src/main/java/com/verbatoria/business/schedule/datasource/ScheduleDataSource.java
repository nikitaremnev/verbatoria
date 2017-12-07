package com.verbatoria.business.schedule.datasource;

import android.util.Log;

import com.verbatoria.business.schedule.models.ScheduleItemModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author nikitaremnev
 */

public class ScheduleDataSource implements IScheduleDataSource<String, Date, Date, ScheduleItemModel> {

    private static final int ROWS_COUNT = 8;
    private static final int START_HOUR = 8;
    private static final int END_HOUR = 21;
    private static final int WEEK_COUNT = 7;
    private static final int FIRST_DAY_OF_WEEK = 1;
    private static final int COLUMN_COUNT = END_HOUR - START_HOUR + 1;

    private final Map<Integer, List<ScheduleItemModel>> mItems = new WeakHashMap<>();

    private Calendar mCalendar = Calendar.getInstance();

    private Calendar mOriginalCalendar = Calendar.getInstance();

    public ScheduleDataSource() {
        int dayOfWeekIndex = 0;
        while (dayOfWeekIndex < WEEK_COUNT) {
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
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(mOriginalCalendar.getTime());
        int firstDayOfWeek = mCalendar.getFirstDayOfWeek();
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
        return mItems.get(rowIndex - 1).get(columnIndex - 1);
    }

    @Override
    public void moveToTheNextWeek() {
        mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) + WEEK_COUNT);
    }

    @Override
    public void moveToThePreviousWeek() {
        mOriginalCalendar.set(Calendar.DAY_OF_YEAR, mOriginalCalendar.get(Calendar.DAY_OF_YEAR) - WEEK_COUNT);
    }

    @Override
    public String getWeekTitle() {
        return null;
    }

    @Override
    public void clearSchedule() {
        int dayOfWeekIndex = 0;
        while (dayOfWeekIndex < WEEK_COUNT) {
            List<ScheduleItemModel> scheduleItemModelList = mItems.get(dayOfWeekIndex);
            for (int i = 0; i < scheduleItemModelList.size(); i++) {
                scheduleItemModelList.get(i).setSelected(false);
            }
            dayOfWeekIndex ++;
        }
    }

    @Override
    public Date getWeekStart() {
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(mOriginalCalendar.getTime());
        int firstDayOfWeek = mCalendar.getFirstDayOfWeek();
        mCalendar.set(Calendar.HOUR, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.DAY_OF_YEAR, mCalendar.get(Calendar.DAY_OF_YEAR) - (getCurrentDayOfWeek() - firstDayOfWeek));
        return mCalendar.getTime();
    }

    @Override
    public Date getWeekEnd() {
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(mOriginalCalendar.getTime());
        int firstDayOfWeek = mCalendar.getFirstDayOfWeek();
        mCalendar.set(Calendar.HOUR, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.DAY_OF_YEAR, mCalendar.get(Calendar.DAY_OF_YEAR) - (getCurrentDayOfWeek() - firstDayOfWeek - WEEK_COUNT));
        return mCalendar.getTime();
    }

    @Override
    public void setWorkingInterval(Date date) {
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(date);
        int currentHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        Log.e("test", "currentHour: " + currentHour);
        if (isHourInBorders(currentHour)) {

            mItems.get(getCurrentDayOfWeek() - 1).get(currentHour - START_HOUR).setSelected(true);
        }
    }

    @Override
    public Map<Date, List<Date>> getItems(boolean selected) {
        Map<Date, List<Date>> resultItems = new HashMap<>();
        int dayOfWeekIndex = 0;
        while (dayOfWeekIndex < WEEK_COUNT) {
            List<ScheduleItemModel> scheduleItemModelList = mItems.get(dayOfWeekIndex);
            List<Date> subItems = new ArrayList<>();
            for (int i = 0; i < scheduleItemModelList.size(); i++) {
                if (scheduleItemModelList.get(i).isSelected() == selected) {
                    subItems.add(getColumnHeaderData(i));
                }
            }
            resultItems.put(getRowHeaderData(dayOfWeekIndex), subItems);
            dayOfWeekIndex ++;
        }
        return resultItems;
    }

    private int getCurrentDayOfWeek() {
        return mCalendar.get(Calendar.DAY_OF_WEEK) == FIRST_DAY_OF_WEEK ? WEEK_COUNT : mCalendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    private boolean isHourInBorders(int hour) {
        return hour >= START_HOUR && hour < END_HOUR;
    }

}
