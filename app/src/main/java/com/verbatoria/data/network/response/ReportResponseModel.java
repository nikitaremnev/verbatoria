package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

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
        return Objects.equals(mId, that.mId) &&
                Objects.equals(mChildId, that.mChildId) &&
                Objects.equals(mLocationId, that.mLocationId) &&
                Objects.equals(mVerbatologId, that.mVerbatologId) &&
                Objects.equals(mReportId, that.mReportId) &&
                Objects.equals(mStatus, that.mStatus) &&
                Objects.equals(mCreatedAt, that.mCreatedAt) &&
                Objects.equals(mUpdatedAt, that.mUpdatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mChildId, mLocationId, mVerbatologId, mReportId, mStatus, mCreatedAt, mUpdatedAt);
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
