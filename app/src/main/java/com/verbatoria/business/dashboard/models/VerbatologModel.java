package com.verbatoria.business.dashboard.models;

import android.text.TextUtils;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 * Реализация модели для Вербатолога
 *
 * @author nikitaremnev
 */
public class VerbatologModel {

    private String mFirstName;

    private String mLastName;

    private String mMiddleName;

    private String mPhone;

    private String mEmail;

    private String mLocationId;

    private boolean mIsArchimedAllowed;

    private List<EventModel> mEvents;

    public VerbatologModel() {

    }

    public String getFullName() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(mLastName)) {
            stringBuilder.append(mLastName);
        }
        if (!TextUtils.isEmpty(mFirstName)) {
            stringBuilder.append(TextUtils.isEmpty(mLastName) ? mFirstName : " " + mFirstName);
        }
        if (!TextUtils.isEmpty(mMiddleName)) {
            stringBuilder.append(TextUtils.isEmpty(mFirstName) ? mMiddleName : " " + mMiddleName);
        }
        return stringBuilder.toString();
    }

    public String getFirstName() {
        return mFirstName;
    }

    public VerbatologModel setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public VerbatologModel setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getMiddleName() {
        return mMiddleName;
    }

    public VerbatologModel setMiddleName(String middleName) {
        mMiddleName = middleName;
        return this;
    }

    public String getPhone() {
        return mPhone;
    }

    public VerbatologModel setPhone(String phone) {
        mPhone = phone;
        return this;
    }

    public String getEmail() {
        return mEmail;
    }

    public VerbatologModel setEmail(String email) {
        mEmail = email;
        return this;
    }

    public String getLocationId() {
        return mLocationId;
    }

    public VerbatologModel setLocationId(String locationId) {
        mLocationId = locationId;
        return this;
    }

    public boolean isArchimedAllowed() {
        return mIsArchimedAllowed;
    }

    public VerbatologModel setIsArchimedAllowed(boolean mIsArchimedAllowed) {
        this.mIsArchimedAllowed = mIsArchimedAllowed;
        return this;
    }

    public List<EventModel> getEvents() {
        return mEvents;
    }

    public VerbatologModel setEvents(List<EventModel> events) {
        mEvents = events;
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
        VerbatologModel that = (VerbatologModel) o;
        return Objects.equal(mFirstName, that.mFirstName) &&
                Objects.equal(mLastName, that.mLastName) &&
                Objects.equal(mMiddleName, that.mMiddleName) &&
                Objects.equal(mPhone, that.mPhone) &&
                Objects.equal(mEmail, that.mEmail) &&
                Objects.equal(mLocationId, that.mLocationId) &&
                Objects.equal(mIsArchimedAllowed, that.mIsArchimedAllowed) &&
                Objects.equal(mEvents, that.mEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mFirstName, mLastName, mMiddleName, mPhone, mEmail, mLocationId, mIsArchimedAllowed, mEvents);
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
                .add("mIsArchimedAllowed", mIsArchimedAllowed)
                .add("mEvents", mEvents)
                .toString();
    }
}
