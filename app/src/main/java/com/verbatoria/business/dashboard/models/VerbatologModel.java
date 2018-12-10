package com.verbatoria.business.dashboard.models;

import android.text.TextUtils;
import java.util.List;
import java.util.Objects;

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
        return Objects.equals(mFirstName, that.mFirstName) &&
                Objects.equals(mLastName, that.mLastName) &&
                Objects.equals(mMiddleName, that.mMiddleName) &&
                Objects.equals(mPhone, that.mPhone) &&
                Objects.equals(mEmail, that.mEmail) &&
                Objects.equals(mLocationId, that.mLocationId) &&
                Objects.equals(mIsArchimedAllowed, that.mIsArchimedAllowed) &&
                Objects.equals(mEvents, that.mEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mFirstName, mLastName, mMiddleName, mPhone, mEmail, mLocationId, mIsArchimedAllowed, mEvents);
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
