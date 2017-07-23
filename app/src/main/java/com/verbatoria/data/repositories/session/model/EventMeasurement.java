package com.verbatoria.data.repositories.session.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Сущность ActivityEvent для получения событий активностей из репозитория
 *
 * @author nikitaremnev
 */
public class EventMeasurement extends BaseMeasurement {

    private long mActivityCode;

    public EventMeasurement() {

    }

    public long getActivityCode() {
        return mActivityCode;
    }

    public EventMeasurement setActivityCode(long activityCode) {
        mActivityCode = activityCode;
        return this;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public EventMeasurement setTimestamp(long timestamp) {
        mTimestamp = timestamp;
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
        if (!super.equals(o)) {
            return false;
        }
        EventMeasurement that = (EventMeasurement) o;
        return mActivityCode == that.mActivityCode;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), mActivityCode);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mActivityCode", mActivityCode)
                .toString();
    }
}