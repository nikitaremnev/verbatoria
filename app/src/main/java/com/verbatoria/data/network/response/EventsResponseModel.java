package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 *
 * Модель ответа от сервера на запрос событий нейрометриста
 *
 * @author nikitaremnev
 */
public class EventsResponseModel {

    private int mTotalEntries;

    private int mPerPage;

    private int mCurrentPage;

    private Integer mNextPage;

    private Integer mPreviousPage;

    private List<EventResponseModel> mEvents;


    public EventsResponseModel() {

    }

    @JsonGetter("total_entries")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public int getTotalEntries() {
        return mTotalEntries;
    }

    public EventsResponseModel setTotalEntries(int totalEntries) {
        mTotalEntries = totalEntries;
        return this;
    }

    @JsonGetter("per_page")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public int getPerPage() {
        return mPerPage;
    }

    public EventsResponseModel setPerPage(int perPage) {
        mPerPage = perPage;
        return this;
    }

    @JsonGetter("current_page")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public int getCurrentPage() {
        return mCurrentPage;
    }

    public EventsResponseModel setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
        return this;
    }

    @JsonGetter("next_page")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getNextPage() {
        return mNextPage;
    }

    public EventsResponseModel setNextPage(Integer nextPage) {
        mNextPage = nextPage;
        return this;
    }

    @JsonGetter("previous_page")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getPreviousPage() {
        return mPreviousPage;
    }

    public EventsResponseModel setPreviousPage(Integer previousPage) {
        mPreviousPage = previousPage;
        return this;
    }

    @JsonGetter("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<EventResponseModel> getEvents() {
        return mEvents;
    }

    public EventsResponseModel setEvents(List<EventResponseModel> events) {
        mEvents = events;
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
        EventsResponseModel that = (EventsResponseModel) o;
        return mTotalEntries == that.mTotalEntries &&
                mPerPage == that.mPerPage &&
                mCurrentPage == that.mCurrentPage &&
                Objects.equal(mNextPage, that.mNextPage) &&
                Objects.equal(mPreviousPage, that.mPreviousPage) &&
                Objects.equal(mEvents, that.mEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mTotalEntries, mPerPage, mCurrentPage, mNextPage, mPreviousPage, mEvents);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mTotalEntries", mTotalEntries)
                .add("mPerPage", mPerPage)
                .add("mCurrentPage", mCurrentPage)
                .add("mNextPage", mNextPage)
                .add("mPreviousPage", mPreviousPage)
                .add("mEvents", mEvents)
                .toString();
    }
}
