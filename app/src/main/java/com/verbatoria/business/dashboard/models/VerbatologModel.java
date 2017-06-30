package com.verbatoria.business.dashboard.models;

import android.content.Context;
import android.text.TextUtils;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.remnev.verbatoriamini.R;

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

    private List<EventModel> mEvents;

    public VerbatologModel() {

    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getMiddleName() {
        return mMiddleName;
    }

    public void setMiddleName(String middleName) {
        mMiddleName = middleName;
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

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public List<EventModel> getEvents() {
        return mEvents;
    }

    public void setEvents(List<EventModel> events) {
        mEvents = events;
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
                Objects.equal(mEvents, that.mEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mFirstName, mLastName, mMiddleName, mPhone, mEmail, mEvents);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mFirstName", mFirstName)
                .add("mLastName", mLastName)
                .add("mMiddleName", mMiddleName)
                .add("mPhone", mPhone)
                .add("mEmail", mEmail)
                .add("mEvents", mEvents)
                .toString();
    }
}
