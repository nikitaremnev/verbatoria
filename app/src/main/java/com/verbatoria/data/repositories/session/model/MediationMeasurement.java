package com.verbatoria.data.repositories.session.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Сущность для получения mediation из репозитория
 *
 * @author nikitaremnev
 */
public class MediationMeasurement extends BaseMeasurement {

    private long mMediationValue;

    public MediationMeasurement() {

    }

    public long getMediationValue() {
        return mMediationValue;
    }

    public MediationMeasurement setMediationValue(long mediationValue) {
        mMediationValue = mediationValue;
        return this;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public MediationMeasurement setTimestamp(long timestamp) {
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
        MediationMeasurement that = (MediationMeasurement) o;
        return mMediationValue == that.mMediationValue;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), mMediationValue);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mMediationValue", mMediationValue)
                .toString();
    }
}
