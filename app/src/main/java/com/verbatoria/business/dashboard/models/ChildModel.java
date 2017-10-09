package com.verbatoria.business.dashboard.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.verbatoria.utils.DateUtils;

import java.util.Date;

/**
 * Реализация модели для ребенка, записанного на сеанс нейрометрии
 *
 * @author nikitaremnev
 */
public class ChildModel implements Parcelable {

    private String mId;

    private String mClientId;

    private String mName;

    private Date mBirthday;

    public ChildModel() {

    }

    public String getId() {
        return mId;
    }

    public ChildModel setId(String id) {
        mId = id;
        return this;
    }

    public String getName() {
        return mName;
    }

    public ChildModel setName(String name) {
        mName = name;
        return this;
    }

    public Date getBirthday() {
        if (mBirthday == null) {
            mBirthday = new Date();
        }
        return mBirthday;
    }

    public String getBirthdayDateString() {
        try {
            return DateUtils.toUIDateString(mBirthday);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public ChildModel setBirthday(Date birthday) {
        mBirthday = birthday;
        return this;
    }

    public String getClientId() {
        return mClientId;
    }

    public ChildModel setClientId(String clientId) {
        mClientId = clientId;
        return this;
    }

    public boolean isFull() {
        Log.e("test", "mName: " + TextUtils.isEmpty(mName));
        Log.e("test", "mBirthday: " + (mBirthday == null));
        return !(TextUtils.isEmpty(mName) || mBirthday == null);
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
                Objects.equal(mBirthday, that.mBirthday) &&
                Objects.equal(mClientId, that.mClientId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mName, mBirthday, mClientId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mName", mName)
                .add("mBirthday", mBirthday)
                .add("mClientId", mClientId)
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
        dest.writeString(this.mClientId);

    }

    protected ChildModel(Parcel in) {
        this.mId = in.readString();
        this.mName = in.readString();
        long tmpMBirthday = in.readLong();
        this.mBirthday = tmpMBirthday == -1 ? null : new Date(tmpMBirthday);
        this.mClientId = in.readString();

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
