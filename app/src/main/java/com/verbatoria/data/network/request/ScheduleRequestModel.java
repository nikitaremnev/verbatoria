package com.verbatoria.data.network.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 *
 * Модель запроса на сервер - добавление расписания
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class ScheduleRequestModel {

    private List<ScheduleItemRequestModel> mScheduleItems;

    public ScheduleRequestModel() {

    }

    @JsonGetter("schedule_entries")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ScheduleItemRequestModel> getScheduleItems() {
        return mScheduleItems;
    }

    public ScheduleRequestModel setScheduleItems(List<ScheduleItemRequestModel> scheduleItems) {
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
        ScheduleRequestModel that = (ScheduleRequestModel) o;
        return Objects.equal(mScheduleItems, that.mScheduleItems);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mScheduleItems);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mScheduleItems", mScheduleItems)
                .toString();
    }
}
