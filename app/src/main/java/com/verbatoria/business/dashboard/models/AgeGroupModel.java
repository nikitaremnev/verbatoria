package com.verbatoria.business.dashboard.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Реализация модели для возрастной группы для Архимеда
 *
 * @author nikitaremnev
 */

public class AgeGroupModel implements Parcelable {

    private int mMinAge;

    private int mMaxAge;

    private boolean mIsArchimedAllowed;

    public AgeGroupModel() {
    }

    public int getMinAge() {
        return mMinAge;
    }

    public AgeGroupModel setMinAge(int mMinAge) {
        this.mMinAge = mMinAge;
        return this;
    }

    public int getMaxAge() {
        return mMaxAge;
    }

    public AgeGroupModel setMaxAge(int mMaxAge) {
        this.mMaxAge = mMaxAge;
        return this;
    }

    public boolean isIsArchimedAllowed() {
        return mIsArchimedAllowed;
    }

    public AgeGroupModel setIsArchimedAllowed(boolean mIsArchimedAllowed) {
        this.mIsArchimedAllowed = mIsArchimedAllowed;
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
        AgeGroupModel that = (AgeGroupModel) o;
        return mMinAge == that.mMinAge &&
                mMaxAge == that.mMaxAge &&
                mIsArchimedAllowed == that.mIsArchimedAllowed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mMinAge, mMaxAge, mIsArchimedAllowed);
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mMinAge);
        dest.writeInt(this.mMaxAge);
        dest.writeInt(this.mIsArchimedAllowed ? 1 : 0);
    }

    protected AgeGroupModel(Parcel in) {
        this.mMinAge = in.readInt();
        this.mMaxAge = in.readInt();
        this.mIsArchimedAllowed = in.readInt() == 1;
    }

    public static final Creator<AgeGroupModel> CREATOR = new Creator<AgeGroupModel>() {
        @Override
        public AgeGroupModel createFromParcel(Parcel source) {
            return new AgeGroupModel(source);
        }

        @Override
        public AgeGroupModel[] newArray(int size) {
            return new AgeGroupModel[size];
        }
    };

}
