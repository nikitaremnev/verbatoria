package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.verbatoria.business.dashboard.models.ChildModel;

import java.util.List;

/**
 *
 * Модель ответа от сервера на поиск детей
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildsResponseModel extends PagingResponseModel {


    private List<ChildModel> mData;

    public ChildsResponseModel() {

    }

    @JsonGetter("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ChildModel> getChilds() {
        return mData;
    }

    public ChildsResponseModel setChilds(List<ChildModel> childs) {
        mData = childs;
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
        ChildsResponseModel that = (ChildsResponseModel) o;
        return mTotalEntries == that.mTotalEntries &&
                mPerPage == that.mPerPage &&
                mCurrentPage == that.mCurrentPage &&
                Objects.equal(mNextPage, that.mNextPage) &&
                Objects.equal(mPreviousPage, that.mPreviousPage) &&
                Objects.equal(mData, that.mData);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mTotalEntries, mPerPage, mCurrentPage, mNextPage, mPreviousPage, mData);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mTotalEntries", mTotalEntries)
                .add("mPerPage", mPerPage)
                .add("mCurrentPage", mCurrentPage)
                .add("mNextPage", mNextPage)
                .add("mPreviousPage", mPreviousPage)
                .add("mData", mData)
                .toString();
    }
}