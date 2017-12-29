package com.verbatoria.business.late_send.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Date;

/**
 * Реализация модели для поздней отправки отчета
 *
 * @author nikitaremnev
 */
public class LateReportModel {

    private String mSessionId;

    private Date mStartAt;

    private Date mEndAt;

    private String mChildName;

    private String mReportId;

    private String mReportFileName;

    public LateReportModel() {

    }

    public String getSessionId() {
        return mSessionId;
    }

    public LateReportModel setSessionId(String sessionId) {
        mSessionId = sessionId;
        return this;
    }

    public Date getStartAt() {
        return mStartAt;
    }

    public LateReportModel setStartAt(Date startAt) {
        mStartAt = startAt;
        return this;
    }

    public Date getEndAt() {
        return mEndAt;
    }

    public LateReportModel setEndAt(Date endAt) {
        mEndAt = endAt;
        return this;
    }

    public String getChildName() {
        return mChildName;
    }

    public LateReportModel setChildName(String childName) {
        mChildName = childName;
        return this;
    }

    public String getReportId() {
        return mReportId;
    }

    public LateReportModel setReportId(String reportId) {
        mReportId = reportId;
        return this;
    }

    public String getReportFileName() {
        return mReportFileName;
    }

    public LateReportModel setReportFileName(String reportFileName) {
        mReportFileName = reportFileName;
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
        LateReportModel that = (LateReportModel) o;
        return Objects.equal(mSessionId, that.mSessionId) &&
                Objects.equal(mStartAt, that.mStartAt) &&
                Objects.equal(mEndAt, that.mEndAt) &&
                Objects.equal(mChildName, that.mChildName) &&
                Objects.equal(mReportId, that.mReportId) &&
                Objects.equal(mReportFileName, that.mReportFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mSessionId, mStartAt, mEndAt, mChildName, mReportId, mReportFileName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mSessionId", mSessionId)
                .add("mStartAt", mStartAt)
                .add("mEndAt", mEndAt)
                .add("mChildName", mChildName)
                .add("mReportId", mReportId)
                .add("mReportFileName", mReportFileName)
                .toString();
    }
}
