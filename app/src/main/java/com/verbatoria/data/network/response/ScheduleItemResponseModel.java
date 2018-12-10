package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 *
 * Модель ответа от сервера на запрос расписания
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class ScheduleItemResponseModel {

    private String mId;

    private String mFromTime;

    private String mToTime;

    public ScheduleItemResponseModel() {

    }

    @JsonGetter("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getId() {
        return mId;
    }

    public ScheduleItemResponseModel setId(String id) {
        mId = id;
        return this;
    }

    @JsonGetter("from_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFromTime() {
        return mFromTime;
    }

    public ScheduleItemResponseModel setFromTime(String fromTime) {
        mFromTime = fromTime;
        return this;
    }

    @JsonGetter("to_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getToTime() {
        return mToTime;
    }

    public ScheduleItemResponseModel setToTime(String toTime) {
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
        ScheduleItemResponseModel that = (ScheduleItemResponseModel) o;
        return Objects.equals(mId, that.mId) &&
                Objects.equals(mFromTime, that.mFromTime) &&
                Objects.equals(mToTime, that.mToTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mFromTime, mToTime);
    }


}
