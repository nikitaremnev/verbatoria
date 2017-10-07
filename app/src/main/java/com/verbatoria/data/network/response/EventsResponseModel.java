package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)

public class EventsResponseModel extends PagingResponseModel {


    private List<EventResponseModel> mEvents;


    public EventsResponseModel() {

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
