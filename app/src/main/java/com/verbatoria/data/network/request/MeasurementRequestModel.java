package com.verbatoria.data.network.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель записи измерения для отправки
 *
 * @author nikitaremnev
 */
public class MeasurementRequestModel {

    private long mReportId;

    private long mActionId;

    private String mDeviceId;

    private String mBCIId;

    private String mEventId;

    private String mLogopedModeId;

    private String mWord;

    private String mBlock;

    private String mMistake;

    private String mReserveBlank1;

    private String mReserveBlank2;

    private long mAttentionValue;

    private long mMediationValue;

    private long mDeltaValue;

    private long mThetaValue;

    private long mLowAlphaValue;

    private long mHighAlphaValue;

    private long mLowBetaValue;

    private long mHighBetaValue;

    private long mLowGammaValue;

    private long mMidGammaValue;

    private String mCreatedAtDate;

    private String mUpdatedAtDate;

    public MeasurementRequestModel() {

    }

    @JsonGetter("report_id")
    public long getReportId() {
        return mReportId;
    }

    @JsonSetter("report_id")
    public MeasurementRequestModel setReportId(long reportId) {
        mReportId = reportId;
        return this;
    }

    @JsonGetter("action_id")
    public long getActionId() {
        return mActionId;
    }

    @JsonSetter("action_id")
    public MeasurementRequestModel setActionId(long actionId) {
        mActionId = actionId;
        return this;
    }

    @JsonGetter("device_id")
    public String getDeviceId() {
        return mDeviceId;
    }

    @JsonSetter("device_id")
    public MeasurementRequestModel setDeviceId(String deviceId) {
        mDeviceId = deviceId;
        return this;
    }

    @JsonGetter("bci_id")
    public String getBCIId() {
        return mBCIId;
    }

    @JsonSetter("bci_id")
    public MeasurementRequestModel setBCIId(String BCIId) {
        mBCIId = BCIId;
        return this;
    }

    @JsonGetter("event_id")
    public String getEventId() {
        return mEventId;
    }

    @JsonSetter("event_id")
    public MeasurementRequestModel setEventId(String eventId) {
        mEventId = eventId;
        return this;
    }

    @JsonGetter("logoped_mode_id")
    public String getLogopedModeId() {
        return mLogopedModeId;
    }

    @JsonSetter("logoped_mode_id")
    public MeasurementRequestModel setLogopedModeId(String logopedModeId) {
        mLogopedModeId = logopedModeId;
        return this;
    }

    @JsonGetter("word")
    public String getWord() {
        return mWord;
    }

    @JsonSetter("word")
    public MeasurementRequestModel setWord(String word) {
        mWord = word;
        return this;
    }

    @JsonGetter("block")
    public String getBlock() {
        return mBlock;
    }

    @JsonSetter("block")
    public MeasurementRequestModel setBlock(String block) {
        mBlock = block;
        return this;
    }

    @JsonGetter("mistake")
    public String getMistake() {
        return mMistake;
    }

    @JsonSetter("mistake")
    public MeasurementRequestModel setMistake(String mistake) {
        mMistake = mistake;
        return this;
    }

    @JsonGetter("reserve_blank1")
    public String getReserveBlank1() {
        return mReserveBlank1;
    }

    @JsonSetter("reserve_blank1")
    public MeasurementRequestModel setReserveBlank1(String reserveBlank1) {
        mReserveBlank1 = reserveBlank1;
        return this;
    }

    @JsonGetter("reserve_blank2")
    public String getReserveBlank2() {
        return mReserveBlank2;
    }

    @JsonSetter("reserve_blank2")
    public MeasurementRequestModel setReserveBlank2(String reserveBlank2) {
        mReserveBlank2 = reserveBlank2;
        return this;
    }

    @JsonGetter("attention")
    public long getAttentionValue() {
        return mAttentionValue;
    }

    @JsonSetter("attention")
    public MeasurementRequestModel setAttentionValue(long attentionValue) {
        mAttentionValue = attentionValue;
        return this;
    }

    @JsonGetter("mediation")
    public long getMediationValue() {
        return mMediationValue;
    }

    @JsonSetter("mediation")
    public MeasurementRequestModel setMediationValue(long mediationValue) {
        mMediationValue = mediationValue;
        return this;
    }

    @JsonGetter("delta")
    public long getDeltaValue() {
        return mDeltaValue;
    }

    @JsonSetter("delta")
    public MeasurementRequestModel setDeltaValue(long deltaValue) {
        mDeltaValue = deltaValue;
        return this;
    }

    @JsonGetter("theta")
    public long getThetaValue() {
        return mThetaValue;
    }

    @JsonSetter("theta")
    public MeasurementRequestModel setThetaValue(long thetaValue) {
        mThetaValue = thetaValue;
        return this;
    }

    @JsonGetter("low_alpha")
    public long getLowAlphaValue() {
        return mLowAlphaValue;
    }

    @JsonSetter("low_alpha")
    public MeasurementRequestModel setLowAlphaValue(long lowAlphaValue) {
        mLowAlphaValue = lowAlphaValue;
        return this;
    }

    @JsonGetter("high_alpha")
    public long getHighAlphaValue() {
        return mHighAlphaValue;
    }

    @JsonSetter("high_alpha")
    public MeasurementRequestModel setHighAlphaValue(long highAlphaValue) {
        mHighAlphaValue = highAlphaValue;
        return this;
    }

    @JsonGetter("low_beta")
    public long getLowBetaValue() {
        return mLowBetaValue;
    }

    @JsonSetter("low_beta")
    public MeasurementRequestModel setLowBetaValue(long lowBetaValue) {
        mLowBetaValue = lowBetaValue;
        return this;
    }

    @JsonGetter("high_beta")
    public long getHighBetaValue() {
        return mHighBetaValue;
    }

    @JsonSetter("high_beta")
    public MeasurementRequestModel setHighBetaValue(long highBetaValue) {
        mHighBetaValue = highBetaValue;
        return this;
    }

    @JsonGetter("low_gamma")
    public long getLowGammaValue() {
        return mLowGammaValue;
    }

    @JsonSetter("low_gamma")
    public MeasurementRequestModel setLowGammaValue(long lowGammaValue) {
        mLowGammaValue = lowGammaValue;
        return this;
    }

    @JsonGetter("mid_gamma")
    public long getMidGammaValue() {
        return mMidGammaValue;
    }

    @JsonSetter("mid_gamma")
    public MeasurementRequestModel setMidGammaValue(long midGammaValue) {
        mMidGammaValue = midGammaValue;
        return this;
    }

    @JsonGetter("created_at")
    public String getCreatedAtDate() {
        return mCreatedAtDate;
    }

    @JsonSetter("created_at")
    public MeasurementRequestModel setCreatedAtDate(String createdAtDate) {
        mCreatedAtDate = createdAtDate;
        return this;
    }

    @JsonGetter("updated_at")
    public String getUpdatedAtDate() {
        return mUpdatedAtDate;
    }

    @JsonSetter("updated_at")
    public MeasurementRequestModel setUpdatedAtDate(String updatedAtDate) {
        mUpdatedAtDate = updatedAtDate;
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
        MeasurementRequestModel that = (MeasurementRequestModel) o;
        return mReportId == that.mReportId &&
                mActionId == that.mActionId &&
                mAttentionValue == that.mAttentionValue &&
                mMediationValue == that.mMediationValue &&
                mDeltaValue == that.mDeltaValue &&
                mThetaValue == that.mThetaValue &&
                mLowAlphaValue == that.mLowAlphaValue &&
                mHighAlphaValue == that.mHighAlphaValue &&
                mLowBetaValue == that.mLowBetaValue &&
                mHighBetaValue == that.mHighBetaValue &&
                mLowGammaValue == that.mLowGammaValue &&
                mMidGammaValue == that.mMidGammaValue &&
                Objects.equal(mDeviceId, that.mDeviceId) &&
                Objects.equal(mBCIId, that.mBCIId) &&
                Objects.equal(mEventId, that.mEventId) &&
                Objects.equal(mLogopedModeId, that.mLogopedModeId) &&
                Objects.equal(mWord, that.mWord) &&
                Objects.equal(mBlock, that.mBlock) &&
                Objects.equal(mMistake, that.mMistake) &&
                Objects.equal(mReserveBlank1, that.mReserveBlank1) &&
                Objects.equal(mReserveBlank2, that.mReserveBlank2) &&
                Objects.equal(mCreatedAtDate, that.mCreatedAtDate) &&
                Objects.equal(mUpdatedAtDate, that.mUpdatedAtDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mReportId, mActionId, mDeviceId, mBCIId, mEventId, mLogopedModeId, mWord, mBlock, mMistake, mReserveBlank1, mReserveBlank2, mAttentionValue, mMediationValue, mDeltaValue, mThetaValue, mLowAlphaValue, mHighAlphaValue, mLowBetaValue, mHighBetaValue, mLowGammaValue, mMidGammaValue, mCreatedAtDate, mUpdatedAtDate);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mReportId", mReportId)
                .add("mActionId", mActionId)
                .add("mDeviceId", mDeviceId)
                .add("mBCIId", mBCIId)
                .add("mEventId", mEventId)
                .add("mLogopedModeId", mLogopedModeId)
                .add("mWord", mWord)
                .add("mBlock", mBlock)
                .add("mMistake", mMistake)
                .add("mReserveBlank1", mReserveBlank1)
                .add("mReserveBlank2", mReserveBlank2)
                .add("mAttentionValue", mAttentionValue)
                .add("mMediationValue", mMediationValue)
                .add("mDeltaValue", mDeltaValue)
                .add("mThetaValue", mThetaValue)
                .add("mLowAlphaValue", mLowAlphaValue)
                .add("mHighAlphaValue", mHighAlphaValue)
                .add("mLowBetaValue", mLowBetaValue)
                .add("mHighBetaValue", mHighBetaValue)
                .add("mLowGammaValue", mLowGammaValue)
                .add("mMidGammaValue", mMidGammaValue)
                .add("mCreatedAtDate", mCreatedAtDate)
                .add("mUpdatedAtDate", mUpdatedAtDate)
                .toString();
    }


}
