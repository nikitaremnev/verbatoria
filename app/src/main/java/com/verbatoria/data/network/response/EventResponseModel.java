package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель ответа от сервера на запрос событий нейрометриста
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventResponseModel {

    private String mId;

    private String mStartAt;

    private String mEndAt;

    private boolean mInstantReport;

    private ChildResponseModel mChild;

    private ReportResponseModel mReport;

    public EventResponseModel() {

    }

    @JsonGetter("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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

    @JsonGetter("instant_report")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public boolean getInstantReport() {
        return mInstantReport;
    }

    public void setInstantReport(boolean instantReport) {
        mInstantReport = instantReport;
    }

    @JsonGetter("child")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ChildResponseModel getChild() {
        return mChild;
    }

    public void setChild(ChildResponseModel child) {
        mChild = child;
    }

    @JsonGetter("report")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ReportResponseModel getReport() {
        return mReport;
    }

    public EventResponseModel setReport(ReportResponseModel report) {
        mReport = report;
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
        EventResponseModel that = (EventResponseModel) o;
        return Objects.equal(mId, that.mId) &&
                Objects.equal(mStartAt, that.mStartAt) &&
                Objects.equal(mEndAt, that.mEndAt) &&
                Objects.equal(mChild, that.mChild) &&
                Objects.equal(mReport, that.mReport) &&
                Objects.equal(mInstantReport, that.mInstantReport);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mStartAt, mEndAt, mChild, mReport, mInstantReport);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mStartAt", mStartAt)
                .add("mEndAt", mEndAt)
                .add("mChild", mChild)
                .add("mReport", mReport)
                .add("mIsInstantReport", mInstantReport)
                .toString();
    }
}
