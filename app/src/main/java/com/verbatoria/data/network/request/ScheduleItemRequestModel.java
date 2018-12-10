package com.verbatoria.data.network.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 *
 * Модель запроса на сервер - добавление расписания
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class ScheduleItemRequestModel {

    private String mFromTime;

    private String mToTime;

    public ScheduleItemRequestModel() {

    }

    @JsonGetter("from_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFromTime() {
        return mFromTime;
    }

    public ScheduleItemRequestModel setFromTime(String fromTime) {
        mFromTime = fromTime;
        return this;
    }

    @JsonGetter("to_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getToTime() {
        return mToTime;
    }

    public ScheduleItemRequestModel setToTime(String toTime) {
        mToTime = toTime;
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
        ScheduleItemRequestModel that = (ScheduleItemRequestModel) o;
        return Objects.equals(mFromTime, that.mFromTime) &&
                Objects.equals(mToTime, that.mToTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mFromTime, mToTime);
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
