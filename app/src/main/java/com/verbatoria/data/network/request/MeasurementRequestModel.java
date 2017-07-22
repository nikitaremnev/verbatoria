package com.verbatoria.data.network.request;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель запроса результатов
 *
 * @author nikitaremnev
 */
@JsonObject
public class MeasurementRequestModel {


//    integer report_id Ссылка на Report
//    string device_id
//    string bci_id
//    string event_id
//    string logoped_mode_id
//    integer action_id
//    string word
//    string block
//    string mistake
//    string reserve_blank1
//    string reserve_blank2
//    integer attention
//    integer meditation
//    integer delta
//    integer theta
//
//    integer low_alpha
//    integer high_alpha
//    integer low_beta
//    integer high_beta
//    integer low_gamma
//    integer mid_gamma
//    datetime created_at время создания записи
//    datetime updated_at

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

    public long getReportId() {
        return mReportId;
    }

    public MeasurementRequestModel setReportId(long reportId) {
        mReportId = reportId;
        return this;
    }

    public long getActionId() {
        return mActionId;
    }

    public MeasurementRequestModel setActionId(long actionId) {
        mActionId = actionId;
        return this;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public MeasurementRequestModel setDeviceId(String deviceId) {
        mDeviceId = deviceId;
        return this;
    }

    public String getBCIId() {
        return mBCIId;
    }

    public MeasurementRequestModel setBCIId(String BCIId) {
        mBCIId = BCIId;
        return this;
    }

    public String getEventId() {
        return mEventId;
    }

    public MeasurementRequestModel setEventId(String eventId) {
        mEventId = eventId;
        return this;
    }

    public String getLogopedModeId() {
        return mLogopedModeId;
    }

    public MeasurementRequestModel setLogopedModeId(String logopedModeId) {
        mLogopedModeId = logopedModeId;
        return this;
    }

    public String getWord() {
        return mWord;
    }

    public MeasurementRequestModel setWord(String word) {
        mWord = word;
        return this;
    }

    public String getBlock() {
        return mBlock;
    }

    public MeasurementRequestModel setBlock(String block) {
        mBlock = block;
        return this;
    }

    public String getMistake() {
        return mMistake;
    }

    public MeasurementRequestModel setMistake(String mistake) {
        mMistake = mistake;
        return this;
    }

    public String getReserveBlank1() {
        return mReserveBlank1;
    }

    public MeasurementRequestModel setReserveBlank1(String reserveBlank1) {
        mReserveBlank1 = reserveBlank1;
        return this;
    }

    public String getReserveBlank2() {
        return mReserveBlank2;
    }

    public MeasurementRequestModel setReserveBlank2(String reserveBlank2) {
        mReserveBlank2 = reserveBlank2;
        return this;
    }

    public long getAttentionValue() {
        return mAttentionValue;
    }

    public MeasurementRequestModel setAttentionValue(long attentionValue) {
        mAttentionValue = attentionValue;
        return this;
    }

    public long getMediationValue() {
        return mMediationValue;
    }

    public MeasurementRequestModel setMediationValue(long mediationValue) {
        mMediationValue = mediationValue;
        return this;
    }

    public long getDeltaValue() {
        return mDeltaValue;
    }

    public MeasurementRequestModel setDeltaValue(long deltaValue) {
        mDeltaValue = deltaValue;
        return this;
    }

    public long getThetaValue() {
        return mThetaValue;
    }

    public MeasurementRequestModel setThetaValue(long thetaValue) {
        mThetaValue = thetaValue;
        return this;
    }

    public long getLowAlphaValue() {
        return mLowAlphaValue;
    }

    public MeasurementRequestModel setLowAlphaValue(long lowAlphaValue) {
        mLowAlphaValue = lowAlphaValue;
        return this;
    }

    public long getHighAlphaValue() {
        return mHighAlphaValue;
    }

    public MeasurementRequestModel setHighAlphaValue(long highAlphaValue) {
        mHighAlphaValue = highAlphaValue;
        return this;
    }

    public long getLowBetaValue() {
        return mLowBetaValue;
    }

    public MeasurementRequestModel setLowBetaValue(long lowBetaValue) {
        mLowBetaValue = lowBetaValue;
        return this;
    }

    public long getHighBetaValue() {
        return mHighBetaValue;
    }

    public MeasurementRequestModel setHighBetaValue(long highBetaValue) {
        mHighBetaValue = highBetaValue;
        return this;
    }

    public long getLowGammaValue() {
        return mLowGammaValue;
    }

    public MeasurementRequestModel setLowGammaValue(long lowGammaValue) {
        mLowGammaValue = lowGammaValue;
        return this;
    }

    public long getMidGammaValue() {
        return mMidGammaValue;
    }

    public MeasurementRequestModel setMidGammaValue(long midGammaValue) {
        mMidGammaValue = midGammaValue;
        return this;
    }

    public String getCreatedAtDate() {
        return mCreatedAtDate;
    }

    public MeasurementRequestModel setCreatedAtDate(String createdAtDate) {
        mCreatedAtDate = createdAtDate;
        return this;
    }

    public String getUpdatedAtDate() {
        return mUpdatedAtDate;
    }

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
