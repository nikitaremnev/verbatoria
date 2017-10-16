package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель ответа от сервера - объект отчета
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportResponseModel {

    private String mId;

    private String mChildId;

    private String mLocationId;

    private String mVerbatologId;

    private String mReportId;

    private String mStatus;

    private String mCreatedAt;

    private String mUpdatedAt;

    public ReportResponseModel() {

    }

    @JsonGetter("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @JsonGetter("child_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getChildId() {
        return mChildId;
    }

    public ReportResponseModel setChildId(String childId) {
        mChildId = childId;
        return this;
    }

    @JsonGetter("location_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocationId() {
        return mLocationId;
    }

    public ReportResponseModel setLocationId(String locationId) {
        mLocationId = locationId;
        return this;
    }

    @JsonGetter("verbatolog_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getVerbatologId() {
        return mVerbatologId;
    }

    public ReportResponseModel setVerbatologId(String verbatologId) {
        mVerbatologId = verbatologId;
        return this;
    }

    @JsonGetter("report_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReportId() {
        return mReportId;
    }

    public ReportResponseModel setReportId(String reportId) {
        mReportId = reportId;
        return this;
    }

    @JsonGetter("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStatus() {
        return mStatus;
    }

    public ReportResponseModel setStatus(String status) {
        mStatus = status;
        return this;
    }

    @JsonGetter("created_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCreatedAt() {
        return mCreatedAt;
    }

    public ReportResponseModel setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
        return this;
    }

    @JsonGetter("updated_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public ReportResponseModel setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
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
        ReportResponseModel that = (ReportResponseModel) o;
        return Objects.equal(mId, that.mId) &&
                Objects.equal(mChildId, that.mChildId) &&
                Objects.equal(mLocationId, that.mLocationId) &&
                Objects.equal(mVerbatologId, that.mVerbatologId) &&
                Objects.equal(mReportId, that.mReportId) &&
                Objects.equal(mStatus, that.mStatus) &&
                Objects.equal(mCreatedAt, that.mCreatedAt) &&
                Objects.equal(mUpdatedAt, that.mUpdatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mChildId, mLocationId, mVerbatologId, mReportId, mStatus, mCreatedAt, mUpdatedAt);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mChildId", mChildId)
                .add("mLocationId", mLocationId)
                .add("mVerbatologId", mVerbatologId)
                .add("mReportId", mReportId)
                .add("mStatus", mStatus)
                .add("mCreatedAt", mCreatedAt)
                .add("mUpdatedAt", mUpdatedAt)
                .toString();
    }
}