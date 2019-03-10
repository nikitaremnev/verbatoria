package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

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

    private boolean mArchimed;

    private boolean mHobby;

    private boolean mIsArchimedAllowed;

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

    @JsonGetter("archimed")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public boolean getArchimed() {
        return mArchimed;
    }

    public void setArchimed(boolean archimed) {
        mArchimed = archimed;
    }

    @JsonGetter("hobby")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public boolean getHobby() {
        return mHobby;
    }

    public void setHobby(boolean hobby) {
        mHobby = hobby;
    }

    @JsonGetter("is_archimed_allowed")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public boolean getIsArchimedAllowed() {
        return mIsArchimedAllowed;
    }

    public void setIsArchimedAllowed(boolean isArchimedAllowed) {
        mIsArchimedAllowed = isArchimedAllowed;
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
        return Objects.equals(mId, that.mId) &&
                Objects.equals(mStartAt, that.mStartAt) &&
                Objects.equals(mEndAt, that.mEndAt) &&
                Objects.equals(mChild, that.mChild) &&
                Objects.equals(mReport, that.mReport) &&
                Objects.equals(mInstantReport, that.mInstantReport) &&
                Objects.equals(mArchimed, that.mArchimed) &&
                Objects.equals(mHobby, that.mHobby) &&
                Objects.equals(mIsArchimedAllowed, that.mIsArchimedAllowed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mStartAt, mEndAt, mChild, mReport, mInstantReport, mArchimed, mHobby, mIsArchimedAllowed);
    }


}
