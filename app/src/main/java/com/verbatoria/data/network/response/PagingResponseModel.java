package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Базовый объект для пейджинг ответов сервера
 *
 * @author nikitaremnev
 */
public class PagingResponseModel {

    protected int mTotalEntries;

    protected int mPerPage;

    protected int mCurrentPage;

    protected Integer mNextPage;

    protected Integer mPreviousPage;

    public PagingResponseModel() {

    }

    @JsonGetter("total_entries")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public int getTotalEntries() {
        return mTotalEntries;
    }

    public PagingResponseModel setTotalEntries(int totalEntries) {
        mTotalEntries = totalEntries;
        return this;
    }

    @JsonGetter("per_page")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public int getPerPage() {
        return mPerPage;
    }

    public PagingResponseModel setPerPage(int perPage) {
        mPerPage = perPage;
        return this;
    }

    @JsonGetter("current_page")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public int getCurrentPage() {
        return mCurrentPage;
    }

    public PagingResponseModel setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
        return this;
    }

    @JsonGetter("next_page")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getNextPage() {
        return mNextPage;
    }

    public PagingResponseModel setNextPage(Integer nextPage) {
        mNextPage = nextPage;
        return this;
    }

    @JsonGetter("previous_page")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getPreviousPage() {
        return mPreviousPage;
    }

    public PagingResponseModel setPreviousPage(Integer previousPage) {
        mPreviousPage = previousPage;
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
        PagingResponseModel that = (PagingResponseModel) o;
        return mTotalEntries == that.mTotalEntries &&
                mPerPage == that.mPerPage &&
                mCurrentPage == that.mCurrentPage &&
                Objects.equal(mNextPage, that.mNextPage) &&
                Objects.equal(mPreviousPage, that.mPreviousPage);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mTotalEntries, mPerPage, mCurrentPage, mNextPage, mPreviousPage);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mTotalEntries", mTotalEntries)
                .add("mPerPage", mPerPage)
                .add("mCurrentPage", mCurrentPage)
                .add("mNextPage", mNextPage)
                .add("mPreviousPage", mPreviousPage)
                .toString();
    }
}

