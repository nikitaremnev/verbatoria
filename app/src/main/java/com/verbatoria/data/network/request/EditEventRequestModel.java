package com.verbatoria.data.network.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель ответа от сервера на запрос событий нейрометриста
 *
 * @author nikitaremnev
 */
public class EditEventRequestModel {

    private EventRequestModel mEvent;

    public EditEventRequestModel() {

    }

    @JsonGetter("event")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public EventRequestModel getEvent() {
        return mEvent;
    }

    public EditEventRequestModel setEvent(EventRequestModel event) {
        mEvent = event;
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
        EditEventRequestModel that = (EditEventRequestModel) o;
        return Objects.equal(mEvent, that.mEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mEvent);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mEvent", mEvent)
                .toString();
    }
}
