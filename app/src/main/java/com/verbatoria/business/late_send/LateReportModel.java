package com.verbatoria.business.late_send;

import java.util.Date;
import java.util.Objects;

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
        return Objects.equals(mSessionId, that.mSessionId) &&
                Objects.equals(mStartAt, that.mStartAt) &&
                Objects.equals(mEndAt, that.mEndAt) &&
                Objects.equals(mChildName, that.mChildName) &&
                Objects.equals(mReportId, that.mReportId) &&
                Objects.equals(mReportFileName, that.mReportFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mSessionId, mStartAt, mEndAt, mChildName, mReportId, mReportFileName);
    }


}
