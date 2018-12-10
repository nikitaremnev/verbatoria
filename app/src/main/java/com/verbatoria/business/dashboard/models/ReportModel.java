package com.verbatoria.business.dashboard.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

/**
 *
 * Реализация модели для отчета
 *
 * @author nikitaremnev
 */
public class ReportModel implements Parcelable {

    public class STATUS {
        public static final String NEW = "new";
        public static final String CANCELED = "cancelled";
        public static final String UPLOADED = "uploaded";
        public static final String READY = "ready";
        public static final String SENT = "sent";
//        new - просто создан, это сразу как только запись в календарь попала
//        uploaded - когда ты загрузил все результаты измерений на сервер
//        ready - когда результаты обработаны и отчет готов
//        sent - когда отчет выслан клиенту
    }

    private String mId;

    private String mChildId;

    private String mLocationId;

    private String mVerbatologId;

    private String mReportId;

    private String mStatus;

    private Date mCreatedAt;

    private Date mUpdatedAt;

    public ReportModel() {

    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getChildId() {
        return mChildId;
    }

    public ReportModel setChildId(String childId) {
        mChildId = childId;
        return this;
    }

    public String getLocationId() {
        return mLocationId;
    }

    public ReportModel setLocationId(String locationId) {
        mLocationId = locationId;
        return this;
    }

    public String getVerbatologId() {
        return mVerbatologId;
    }

    public ReportModel setVerbatologId(String verbatologId) {
        mVerbatologId = verbatologId;
        return this;
    }

    public String getReportId() {
        return mReportId;
    }

    public ReportModel setReportId(String reportId) {
        mReportId = reportId;
        return this;
    }

    public String getStatus() {
        return mStatus;
    }

    public ReportModel setStatus(String status) {
        mStatus = status;
        return this;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public ReportModel setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return mUpdatedAt;
    }

    public ReportModel setUpdatedAt(Date updatedAt) {
        mUpdatedAt = updatedAt;
        return this;
    }

    public boolean isNew() {
        return mStatus.equals(STATUS.NEW);
    }

    public boolean isCanceled() {
        return mStatus.equals(STATUS.CANCELED);
    }

    public boolean isSent() {
        return mStatus.equals(STATUS.SENT);
    }

    public boolean isReadyOrSent() {
        return mStatus.equals(STATUS.SENT) || mStatus.equals(STATUS.READY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReportModel that = (ReportModel) o;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mChildId);
        dest.writeString(this.mLocationId);
        dest.writeString(this.mVerbatologId);
        dest.writeString(this.mReportId);
        dest.writeString(this.mStatus);
        dest.writeLong(this.mCreatedAt != null ? this.mCreatedAt.getTime() : -1);
        dest.writeLong(this.mUpdatedAt != null ? this.mUpdatedAt.getTime() : -1);
    }

    protected ReportModel(Parcel in) {
        this.mId = in.readString();
        this.mChildId = in.readString();
        this.mLocationId = in.readString();
        this.mVerbatologId = in.readString();
        this.mReportId = in.readString();
        this.mStatus = in.readString();
        long tmpMCreatedAt = in.readLong();
        this.mCreatedAt = tmpMCreatedAt == -1 ? null : new Date(tmpMCreatedAt);
        long tmpMUpdatedAt = in.readLong();
        this.mUpdatedAt = tmpMUpdatedAt == -1 ? null : new Date(tmpMUpdatedAt);
    }

    public static final Parcelable.Creator<ReportModel> CREATOR = new Parcelable.Creator<ReportModel>() {
        @Override
        public ReportModel createFromParcel(Parcel source) {
            return new ReportModel(source);
        }

        @Override
        public ReportModel[] newArray(int size) {
            return new ReportModel[size];
        }
    };
}
