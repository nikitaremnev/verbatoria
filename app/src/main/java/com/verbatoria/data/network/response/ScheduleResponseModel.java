package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

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
                Objects.equal(mNextPage, that.mNextPage) &&
                Objects.equal(mPreviousPage, that.mPreviousPage) &&
                Objects.equal(mScheduleItems, that.mScheduleItems);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mTotalEntries, mPerPage, mCurrentPage, mNextPage, mPreviousPage, mScheduleItems);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mTotalEntries", mTotalEntries)
                .add("mPerPage", mPerPage)
                .add("mCurrentPage", mCurrentPage)
                .add("mNextPage", mNextPage)
                .add("mPreviousPage", mPreviousPage)
                .add("mScheduleItems", mScheduleItems)
                .toString();
    }
}
