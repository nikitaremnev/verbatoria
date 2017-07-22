package com.verbatoria.data.repositories.session.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Сущность для получения attention из репозитория
 *
 * @author nikitaremnev
 */
public class AttentionMeasurement extends BaseMeasurement {

    private long mAttentionValue;

    public AttentionMeasurement() {

    }

    public long getAttentionValue() {
        return mAttentionValue;
    }

    public AttentionMeasurement setAttentionValue(long attentionValue) {
        mAttentionValue = attentionValue;
        return this;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public AttentionMeasurement setTimestamp(long timestamp) {
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
        AttentionMeasurement that = (AttentionMeasurement) o;
        return mAttentionValue == that.mAttentionValue;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), mAttentionValue);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mAttentionValue", mAttentionValue)
                .toString();
    }
}
