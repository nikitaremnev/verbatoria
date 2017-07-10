package com.verbatoria.data.network.request;

import com.bluelinelabs.logansquare.annotation.JsonObject;
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
@JsonObject
public class AddEventRequestModel {

    private String mChildId;

    private String mLocationId;

    private String mStartAt;

    private String mEndAt;

    public AddEventRequestModel() {

    }

    @JsonGetter("child_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getChildId() {
        return mChildId;
    }

    public void setChildId(String childId) {
        mChildId = childId;
    }

    @JsonGetter("location_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocationId() {
        return mLocationId;
    }

    public void setLocationId(String locationId) {
        mLocationId = locationId;
    }

    @JsonGetter("start_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStartAt() {
        return mStartAt;
    }

    public void setStartAt(String startAt) {
        mStartAt = startAt;
    }

    @JsonGetter("end_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEndAt() {
        return mEndAt;
    }

    public void setEndAt(String endAt) {
        mEndAt = endAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddEventRequestModel that = (AddEventRequestModel) o;
        return Objects.equal(mChildId, that.mChildId) &&
                Objects.equal(mLocationId, that.mLocationId) &&
                Objects.equal(mStartAt, that.mStartAt) &&
                Objects.equal(mEndAt, that.mEndAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mChildId, mLocationId, mStartAt, mEndAt);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mChildId", mChildId)
                .add("mLocationId", mLocationId)
                .add("mStartAt", mStartAt)
                .add("mEndAt", mEndAt)
                .toString();
    }
}
