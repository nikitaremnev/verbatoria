package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 *
 * Модель ответа от сервера на запрос информации о вербатологе
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerbatologInfoResponseModel {

    private String mFirstName;

    private String mLastName;

    private String mMiddleName;

    private String mPhone;

    private String mEmail;

    private String mLocationId;

    private boolean mIsArchimedAllowed;

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

    @JsonGetter("is_archimed_allowed")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public boolean getIsArchimedAllowed() {
        return mIsArchimedAllowed;
    }

    public void setIsArchimedAllowed(boolean mIsArchimedAllowed) {
        this.mIsArchimedAllowed = mIsArchimedAllowed;
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
        return Objects.equals(mFirstName, that.mFirstName) &&
                Objects.equals(mLastName, that.mLastName) &&
                Objects.equals(mMiddleName, that.mMiddleName) &&
                Objects.equals(mPhone, that.mPhone) &&
                Objects.equals(mEmail, that.mEmail) &&
                Objects.equals(mLocationId, that.mLocationId) &&
                Objects.equals(mIsArchimedAllowed, that.mIsArchimedAllowed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mFirstName, mLastName, mMiddleName, mPhone, mEmail, mLocationId, mIsArchimedAllowed);
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }

}
