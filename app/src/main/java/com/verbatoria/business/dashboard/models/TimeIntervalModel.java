package com.verbatoria.business.dashboard.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.verbatoria.utils.DateUtils;

import java.util.Date;

/**
 * Реализация модели для выбора времени записи нейрометрии
 *
 * @author nikitaremnev
 */
public class TimeIntervalModel implements Parcelable {

    private Date mStartAt;

    private Date mEndAt;

    private boolean mIsAvailable;

    public TimeIntervalModel() {
        mIsAvailable = true;
    }

    public Date getStartAt() {
        return mStartAt;
    }

    public TimeIntervalModel setStartAt(Date startAt) {
        mStartAt = startAt;
        return this;
    }

    public Date getEndAt() {
        return mEndAt;
    }

    public TimeIntervalModel setEndAt(Date endAt) {
        mEndAt = endAt;
        return this;
    }

    public boolean isAvailable() {
        return mIsAvailable;
    }

    public TimeIntervalModel setAvailable(boolean available) {
        mIsAvailable = available;
        return this;
    }

    public String getIntervalString() {
        return DateUtils.periodToString(mStartAt, mEndAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeIntervalModel that = (TimeIntervalModel) o;
        return mIsAvailable == that.mIsAvailable &&
                Objects.equal(mStartAt, that.mStartAt) &&
                Objects.equal(mEndAt, that.mEndAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mStartAt, mEndAt, mIsAvailable);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mStartAt", mStartAt)
                .add("mEndAt", mEndAt)
                .add("mIsAvailable", mIsAvailable)
                .toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mStartAt != null ? this.mStartAt.getTime() : -1);
        dest.writeLong(this.mEndAt != null ? this.mEndAt.getTime() : -1);
        dest.writeByte(this.mIsAvailable ? (byte) 1 : (byte) 0);
    }

    protected TimeIntervalModel(Parcel in) {
        long tmpMStartAt = in.readLong();
        this.mStartAt = tmpMStartAt == -1 ? null : new Date(tmpMStartAt);
        long tmpMEndAt = in.readLong();
        this.mEndAt = tmpMEndAt == -1 ? null : new Date(tmpMEndAt);
        this.mIsAvailable = in.readByte() != 0;
    }

    public static final Creator<TimeIntervalModel> CREATOR = new Creator<TimeIntervalModel>() {
        @Override
        public TimeIntervalModel createFromParcel(Parcel source) {
            return new TimeIntervalModel(source);
        }

        @Override
        public TimeIntervalModel[] newArray(int size) {
            return new TimeIntervalModel[size];
        }
    };
}
