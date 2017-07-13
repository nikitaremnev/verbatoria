package com.verbatoria.business.dashboard.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.verbatoria.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Реализация модели для ребенка, записанного на сеанс нейрометрии
 *
 * @author nikitaremnev
 */
public class ChildModel implements Parcelable {

    private String mId;

    private String mName;

    private Date mBirthday;

    public ChildModel() {

    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getBirthday() {
        return mBirthday;
    }

    public String getBirthdayDateString() throws ParseException {
        try {
            return DateUtils.toString(mBirthday);
        } catch (ParseException e) {
            throw e;
        }
    }

    public void setBirthday(Date birthday) {
        mBirthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChildModel that = (ChildModel) o;
        return Objects.equal(mId, that.mId) &&
                Objects.equal(mName, that.mName) &&
                Objects.equal(mBirthday, that.mBirthday);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mName, mBirthday);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mName", mName)
                .add("mBirthday", mBirthday)
                .toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mName);
        dest.writeLong(this.mBirthday != null ? this.mBirthday.getTime() : -1);
    }

    protected ChildModel(Parcel in) {
        this.mId = in.readString();
        this.mName = in.readString();
        long tmpMBirthday = in.readLong();
        this.mBirthday = tmpMBirthday == -1 ? null : new Date(tmpMBirthday);
    }

    public static final Parcelable.Creator<ChildModel> CREATOR = new Parcelable.Creator<ChildModel>() {
        @Override
        public ChildModel createFromParcel(Parcel source) {
            return new ChildModel(source);
        }

        @Override
        public ChildModel[] newArray(int size) {
            return new ChildModel[size];
        }
    };
}
