package com.verbatoria.business.schedule.datasource;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    Map<Date, List<Date>> getItems(boolean selected);

}