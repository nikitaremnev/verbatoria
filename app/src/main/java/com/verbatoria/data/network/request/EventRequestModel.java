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
public class EventRequestModel {

    private String mChildId;

    private String mLocationId;

    private String mStartAt;

    private String mEndAt;

    private boolean mIsInstantReport;

    public EventRequestModel() {

    }

    @JsonGetter("child_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getChildId() {
        return mChildId;
    }

    public EventRequestModel setChildId(String childId) {
        mChildId = childId;
        return this;
    }

    @JsonGetter("location_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocationId() {
        return mLocationId;
    }

    public EventRequestModel setLocationId(String locationId) {
        mLocationId = locationId;
        return this;
    }

    @JsonGetter("start_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStartAt() {
        return mStartAt;
    }

    public EventRequestModel setStartAt(String startAt) {
        mStartAt = startAt;
        return this;
    }

    @JsonGetter("end_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEndAt() {
        return mEndAt;
    }

    public EventRequestModel setEndAt(String endAt) {
        mEndAt = endAt;
        return this;
    }
    @JsonGetter("instant_report")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public boolean isInstantReport() {
        return mIsInstantReport;
    }

    public EventRequestModel setIsInstantReport(boolean isInstantReport) {
        this.mIsInstantReport = isInstantReport;
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
        EventRequestModel that = (EventRequestModel) o;
        return Objects.equal(mChildId, that.mChildId) &&
                Objects.equal(mLocationId, that.mLocationId) &&
                Objects.equal(mStartAt, that.mStartAt) &&
                Objects.equal(mEndAt, that.mEndAt) &&
                Objects.equal(mIsInstantReport, that.mIsInstantReport);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mChildId, mLocationId, mStartAt, mEndAt, mIsInstantReport);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mChildId", mChildId)
                .add("mLocationId", mLocationId)
                .add("mStartAt", mStartAt)
                .add("mEndAt", mEndAt)
                .add("mIsInstantReport", mIsInstantReport)
                .toString();
    }
}
