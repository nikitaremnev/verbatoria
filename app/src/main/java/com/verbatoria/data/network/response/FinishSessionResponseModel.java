package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель ответа от сервера на запрос старта сессии
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class FinishSessionResponseModel {

    private String mId;

    private String mChildId;

    private String mVerbatologId;

    private String mLocationId;

    private String mCreatedAt;

    private String mUpdatedAt;

    private String mStatus;


    public FinishSessionResponseModel() {

    }

    @JsonGetter("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getId() {
        return mId;
    }

    @JsonSetter("id")
    public void setId(String id) {
        mId = id;
    }

    @JsonGetter("child_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getChildId() {
        return mChildId;
    }

    @JsonSetter("child_id")
    public void setChildId(String childId) {
        mChildId = childId;
    }

    @JsonGetter("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStatus() {
        return mStatus;
    }

    @JsonSetter("status")
    public FinishSessionResponseModel setStatus(String status) {
        mStatus = status;
        return this;
    }

    @JsonGetter("verbatolog_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getVerbatologId() {
        return mVerbatologId;
    }

    @JsonSetter("verbatolog_id")
    public void setVerbatologId(String verbatologId) {
        mVerbatologId = verbatologId;
    }

    @JsonGetter("location_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocationId() {
        return mLocationId;
    }

    @JsonSetter("location_id")
    public void setLocationId(String locationId) {
        mLocationId = locationId;
    }

    @JsonGetter("created_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCreatedAt() {
        return mCreatedAt;
    }

    @JsonSetter("created_at")
    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    @JsonGetter("updated_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    @JsonSetter("updated_at")
    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FinishSessionResponseModel that = (FinishSessionResponseModel) o;
        return Objects.equal(mId, that.mId) &&
                Objects.equal(mChildId, that.mChildId) &&
                Objects.equal(mVerbatologId, that.mVerbatologId) &&
                Objects.equal(mLocationId, that.mLocationId) &&
                Objects.equal(mCreatedAt, that.mCreatedAt) &&
                Objects.equal(mUpdatedAt, that.mUpdatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mChildId, mVerbatologId, mLocationId, mCreatedAt, mUpdatedAt);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mChildId", mChildId)
                .add("mVerbatologId", mVerbatologId)
                .add("mLocationId", mLocationId)
                .add("mCreatedAt", mCreatedAt)
                .add("mUpdatedAt", mUpdatedAt)
                .toString();
    }
}
