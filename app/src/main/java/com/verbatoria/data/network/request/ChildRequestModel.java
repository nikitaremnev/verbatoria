package com.verbatoria.data.network.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.verbatoria.data.network.common.ChildModel;

/**
 * @author nikitaremnev
 */

public class ChildRequestModel {

    private ChildModel mChild;

    public ChildRequestModel() {

    }

    @JsonGetter("child")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ChildModel getChild() {
        return mChild;
    }

    public ChildRequestModel setChild(ChildModel child) {
        mChild = child;
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
        ChildRequestModel that = (ChildRequestModel) o;
        return Objects.equal(mChild, that.mChild);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mChild);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mChild", mChild)
                .toString();
    }
}
