package com.verbatoria.business.schedule.datasource;

import java.util.Date;

/**
 * @author nikitaremnev
 */

public interface IScheduleDataSource<TFirstHeaderDataType, TRowHeaderDataType, TColumnHeaderDataType, TItemDataType> {

    int getRowsCount();

    int getColumnsCount();

    TFirstHeaderDataType getFirstHeaderData();

    TRowHeaderDataType getRowHeaderData(int index);

    TColumnHeaderDataType getColumnHeaderData(int index);

    TItemDataType getItemData(int rowIndex, int columnIndex);

    void moveToTheNextWeek();

    void moveToThePreviousWeek();

    String getWeekTitle();

    void clearSchedule();

    Date getWeekStart();

    Date getWeekEnd();

    void setWorkingInterval(Date date);


}