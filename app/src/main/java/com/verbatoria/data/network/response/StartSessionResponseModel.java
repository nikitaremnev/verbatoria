package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Objects;

/**
 *
 * Модель ответа от сервера на запрос старта сессии
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StartSessionResponseModel {

    private String mId;

    private String mChildId;

    private String mVerbatologId;

    private String mLocationId;

    private String mCreatedAt;

    private String mUpdatedAt;

    private String mStatus;

    public StartSessionResponseModel() {

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
    public StartSessionResponseModel setStatus(String status) {
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
        StartSessionResponseModel that = (StartSessionResponseModel) o;
        return Objects.equals(mId, that.mId) &&
                Objects.equals(mChildId, that.mChildId) &&
                Objects.equals(mVerbatologId, that.mVerbatologId) &&
                Objects.equals(mLocationId, that.mLocationId) &&
                Objects.equals(mCreatedAt, that.mCreatedAt) &&
                Objects.equals(mUpdatedAt, that.mUpdatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mChildId, mVerbatologId, mLocationId, mCreatedAt, mUpdatedAt);
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
