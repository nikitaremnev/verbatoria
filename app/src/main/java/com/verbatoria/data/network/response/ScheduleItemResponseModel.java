package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

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
        return Objects.equal(mId, that.mId) &&
                Objects.equal(mFromTime, that.mFromTime) &&
                Objects.equal(mToTime, that.mToTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mFromTime, mToTime);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mFromTime", mFromTime)
                .add("mToTime", mToTime)
                .toString();
    }
}
