package com.verbatoria.data.repositories.session.model;

import java.util.Objects;

/**
 * Базовая сущность Measurement
 *
 * @author nikitaremnev
 */
public class BaseMeasurement {

    long mTimestamp;

    BaseMeasurement() {

    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public BaseMeasurement setTimestamp(long timestamp) {
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
        BaseMeasurement that = (BaseMeasurement) o;
        return mTimestamp == that.mTimestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mTimestamp);
    }


}
