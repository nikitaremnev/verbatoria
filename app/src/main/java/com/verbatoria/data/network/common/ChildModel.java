package com.verbatoria.data.network.common;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * @author nikitaremnev
 */

public class ChildModel {

    private String mName;

    private String mBirthday;

    public ChildModel() {

    }

    @JsonGetter("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() {
        return mName;
    }

    public ChildModel setName(String name) {
        mName = name;
        return this;
    }

    @JsonGetter("birth_day")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBirthday() {
        return mBirthday;
    }

    public ChildModel setBirthday(String birthday) {
        mBirthday = birthday;
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
        ChildModel that = (ChildModel) o;
        return Objects.equal(mName, that.mName) &&
                Objects.equal(mBirthday, that.mBirthday);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mName, mBirthday);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mName", mName)
                .add("mBirthday", mBirthday)
                .toString();
    }
}
