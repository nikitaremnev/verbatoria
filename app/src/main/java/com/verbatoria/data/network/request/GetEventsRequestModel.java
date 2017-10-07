package com.verbatoria.data.network.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * TODO: Paging not realized
 *
 * @author nikitaremnev
 */
public class GetEventsRequestModel {

    private int mPerPage;

    private int mPage;

    private String mFromTime;

    private String mToTime;

    public GetEventsRequestModel() {

    }

    public int getPerPage() {
        return mPerPage;
    }

    public GetEventsRequestModel setPerPage(int perPage) {
        mPerPage = perPage;
        return this;
    }

    public int getPage() {
        return mPage;
    }

    public GetEventsRequestModel setPage(int page) {
        mPage = page;
        return this;
    }

    @JsonGetter("from_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFromTime() {
        return mFromTime;
    }

    public GetEventsRequestModel setFromTime(String fromTime) {
        mFromTime = fromTime;
        return this;
    }

    @JsonGetter("to_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getToTime() {
        return mToTime;
    }

    public GetEventsRequestModel setToTime(String toTime) {
        mToTime = toTime;
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
        GetEventsRequestModel that = (GetEventsRequestModel) o;
        return mPerPage == that.mPerPage &&
                mPage == that.mPage &&
                Objects.equal(mFromTime, that.mFromTime) &&
                Objects.equal(mToTime, that.mToTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mPerPage, mPage, mFromTime, mToTime);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mPerPage", mPerPage)
                .add("mPage", mPage)
                .add("mFromTime", mFromTime)
                .add("mToTime", mToTime)
                .toString();
    }
}
