package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

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
                Objects.equals(mNextPage, that.mNextPage) &&
                Objects.equals(mPreviousPage, that.mPreviousPage) &&
                Objects.equals(mEvents, that.mEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mTotalEntries, mPerPage, mCurrentPage, mNextPage, mPreviousPage, mEvents);
    }


}
