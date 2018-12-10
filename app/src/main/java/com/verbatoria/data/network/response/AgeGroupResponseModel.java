package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 *
 * Модель ответа от сервера - одна возрастная группа
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgeGroupResponseModel {

    private int mMinAge;

    private int mMaxAge;

    private String mFullName;

    private boolean mIsArchimedAllowed;

    public AgeGroupResponseModel() {

    }

    @JsonGetter("min_age")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public int getMinAge() {
        return mMinAge;
    }

    public AgeGroupResponseModel setMinAge(int mMinAge) {
        this.mMinAge = mMinAge;
        return this;
    }

    @JsonGetter("max_age")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public int getMaxAge() {
        return mMaxAge;
    }

    public AgeGroupResponseModel setMaxAge(int mMaxAge) {
        this.mMaxAge = mMaxAge;
        return this;
    }

    @JsonGetter("full_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFullName() {
        return mFullName;
    }

    public AgeGroupResponseModel setFullName(String mFullName) {
        this.mFullName = mFullName;
        return this;
    }

    @JsonGetter("is_archimed_allowed")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public boolean isIsArchimedAllowed() {
        return mIsArchimedAllowed;
    }

    public AgeGroupResponseModel setIsArchimedAllowed(boolean mIsArchimedAllowed) {
        this.mIsArchimedAllowed = mIsArchimedAllowed;
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
        AgeGroupResponseModel that = (AgeGroupResponseModel) o;
        return mMinAge == that.mMinAge &&
                mMaxAge == that.mMaxAge &&
                mFullName == that.mFullName &&
                mIsArchimedAllowed == that.mIsArchimedAllowed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mMinAge, mMaxAge, mFullName, mIsArchimedAllowed);
    }



}
