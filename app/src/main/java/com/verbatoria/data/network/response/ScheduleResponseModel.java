package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

/**
 *
 * Модель ответа от сервера на запрос расписания
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class ScheduleResponseModel extends PagingResponseModel {

    private List<ScheduleItemResponseModel> mScheduleItems;

    public ScheduleResponseModel() {

    }

    @JsonGetter("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ScheduleItemResponseModel> getScheduleItems() {
        return mScheduleItems;
    }

    public ScheduleResponseModel setScheduleItems(List<ScheduleItemResponseModel> scheduleItems) {
        mScheduleItems = scheduleItems;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScheduleResponseModel that = (ScheduleResponseModel) o;
        return mTotalEntries == that.mTotalEntries &&
                mPerPage == that.mPerPage &&
                mCurrentPage == that.mCurrentPage &&
                Objects.equals(mNextPage, that.mNextPage) &&
                Objects.equals(mPreviousPage, that.mPreviousPage) &&
                Objects.equals(mScheduleItems, that.mScheduleItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mTotalEntries, mPerPage, mCurrentPage, mNextPage, mPreviousPage, mScheduleItems);
    }


}
