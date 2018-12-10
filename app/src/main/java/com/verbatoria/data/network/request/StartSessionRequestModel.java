package com.verbatoria.data.network.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 *
 * Модель запроса старта сессии
 *
 * @author nikitaremnev
 */
public class StartSessionRequestModel {

    private String mEventId;

    public StartSessionRequestModel() {

    }

    @JsonGetter("event_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEventId() {
        return mEventId;
    }

    public StartSessionRequestModel setEventId(String eventId) {
        mEventId = eventId;
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
        StartSessionRequestModel that = (StartSessionRequestModel) o;
        return Objects.equals(mEventId, that.mEventId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mEventId);
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
