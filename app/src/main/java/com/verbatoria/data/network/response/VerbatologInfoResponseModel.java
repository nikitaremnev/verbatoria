package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель ответа от сервера на запрос информации о вербатологе
 *
 * @author nikitaremnev
 */
public class VerbatologInfoResponseModel {

    private String mFirstName;

    private String mLastName;

    private String mMiddleName;

    private String mPhone;

    private String mEmail;

    private String mLocationId;

    public VerbatologInfoResponseModel() {

    }

    @JsonGetter("first_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    @JsonGetter("last_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    @JsonGetter("middle_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMiddleName() {
        return mMiddleName;
    }

    public void setMiddleName(String middleName) {
        mMiddleName = middleName;
    }

    @JsonGetter("phone")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    @JsonGetter("email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    @JsonGetter("location_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocationId() {
        return mLocationId;
    }

    public VerbatologInfoResponseModel setLocationId(String locationId) {
        mLocationId = locationId;
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
        VerbatologInfoResponseModel that = (VerbatologInfoResponseModel) o;
        return Objects.equal(mFirstName, that.mFirstName) &&
                Objects.equal(mLastName, that.mLastName) &&
                Objects.equal(mMiddleName, that.mMiddleName) &&
                Objects.equal(mPhone, that.mPhone) &&
                Objects.equal(mEmail, that.mEmail) &&
                Objects.equal(mLocationId, that.mLocationId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mFirstName, mLastName, mMiddleName, mPhone, mEmail, mLocationId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mFirstName", mFirstName)
                .add("mLastName", mLastName)
                .add("mMiddleName", mMiddleName)
                .add("mPhone", mPhone)
                .add("mEmail", mEmail)
                .add("mLocationId", mLocationId)
                .toString();
    }
}
