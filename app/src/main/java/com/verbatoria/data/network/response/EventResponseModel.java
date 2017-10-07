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

    private ChildResponseModel mChild;

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

    @JsonGetter("child")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ChildResponseModel getChild() {
        return mChild;
    }

    public void setChild(ChildResponseModel child) {
        mChild = child;
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
                Objects.equal(mChild, that.mChild);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mStartAt, mEndAt, mChild);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mStartAt", mStartAt)
                .add("mEndAt", mEndAt)
                .add("mChild", mChild)
                .toString();
    }
}
