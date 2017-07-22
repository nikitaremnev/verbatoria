package com.verbatoria.data.repositories.session.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Сущность для получения eeg из репозитория
 *
 * @author nikitaremnev
 */
public class EEGMeasurement extends BaseMeasurement {

    private long mDeltaValue;
    private long mThetaValue;
    private long mLowAlphaValue;
    private long mHighAlphaValue;
    private long mLowBetaValue;
    private long mHighBetaValue;
    private long mLowGammaValue;
    private long mMidGammaValue;

    public EEGMeasurement() {

    }

    public long getDeltaValue() {
        return mDeltaValue;
    }

    public EEGMeasurement setDeltaValue(long deltaValue) {
        mDeltaValue = deltaValue;
        return this;
    }

    public long getThetaValue() {
        return mThetaValue;
    }

    public EEGMeasurement setThetaValue(long thetaValue) {
        mThetaValue = thetaValue;
        return this;
    }

    public long getLowAlphaValue() {
        return mLowAlphaValue;
    }

    public EEGMeasurement setLowAlphaValue(long lowAlphaValue) {
        mLowAlphaValue = lowAlphaValue;
        return this;
    }

    public long getHighAlphaValue() {
        return mHighAlphaValue;
    }

    public EEGMeasurement setHighAlphaValue(long highAlphaValue) {
        mHighAlphaValue = highAlphaValue;
        return this;
    }

    public long getLowBetaValue() {
        return mLowBetaValue;
    }

    public EEGMeasurement setLowBetaValue(long lowBetaValue) {
        mLowBetaValue = lowBetaValue;
        return this;
    }

    public long getHighBetaValue() {
        return mHighBetaValue;
    }

    public EEGMeasurement setHighBetaValue(long highBetaValue) {
        mHighBetaValue = highBetaValue;
        return this;
    }

    public long getLowGammaValue() {
        return mLowGammaValue;
    }

    public EEGMeasurement setLowGammaValue(long lowGammaValue) {
        mLowGammaValue = lowGammaValue;
        return this;
    }

    public long getMidGammaValue() {
        return mMidGammaValue;
    }

    public EEGMeasurement setMidGammaValue(long midGammaValue) {
        mMidGammaValue = midGammaValue;
        return this;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public EEGMeasurement setTimestamp(long timestamp) {
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
        EEGMeasurement that = (EEGMeasurement) o;
        return mDeltaValue == that.mDeltaValue &&
                mThetaValue == that.mThetaValue &&
                mLowAlphaValue == that.mLowAlphaValue &&
                mHighAlphaValue == that.mHighAlphaValue &&
                mLowBetaValue == that.mLowBetaValue &&
                mHighBetaValue == that.mHighBetaValue &&
                mLowGammaValue == that.mLowGammaValue &&
                mMidGammaValue == that.mMidGammaValue;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), mDeltaValue, mThetaValue, mLowAlphaValue, mHighAlphaValue, mLowBetaValue, mHighBetaValue, mLowGammaValue, mMidGammaValue);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mDeltaValue", mDeltaValue)
                .add("mThetaValue", mThetaValue)
                .add("mLowAlphaValue", mLowAlphaValue)
                .add("mHighAlphaValue", mHighAlphaValue)
                .add("mLowBetaValue", mLowBetaValue)
                .add("mHighBetaValue", mHighBetaValue)
                .add("mLowGammaValue", mLowGammaValue)
                .add("mMidGammaValue", mMidGammaValue)
                .toString();
    }
}
