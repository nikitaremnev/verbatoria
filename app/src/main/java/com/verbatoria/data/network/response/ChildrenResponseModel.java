package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.verbatoria.data.network.common.ChildModel;

import java.util.List;
import java.util.Objects;

/**
 *
 * Модель ответа от сервера на поиск детей
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildrenResponseModel extends PagingResponseModel {

    private List<ChildModel> mData;

    public ChildrenResponseModel() {

    }

    @JsonGetter("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ChildModel> getChilds() {
        return mData;
    }

    public ChildrenResponseModel setChilds(List<ChildModel> childs) {
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
        ChildrenResponseModel that = (ChildrenResponseModel) o;
        return mTotalEntries == that.mTotalEntries &&
                mPerPage == that.mPerPage &&
                mCurrentPage == that.mCurrentPage &&
                Objects.equals(mNextPage, that.mNextPage) &&
                Objects.equals(mPreviousPage, that.mPreviousPage) &&
                Objects.equals(mData, that.mData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mTotalEntries, mPerPage, mCurrentPage, mNextPage, mPreviousPage, mData);
    }



}
