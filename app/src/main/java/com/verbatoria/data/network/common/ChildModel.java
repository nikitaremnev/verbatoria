package com.verbatoria.data.network.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * @author nikitaremnev
 */

public class ChildModel implements Parcelable {

    private String mName;

    private String mBirthday;

    private String mId;

    private String mClientId;

    public ChildModel() {

    }

    @JsonGetter("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() {
        return mName;
    }

    public ChildModel setName(String name) {
        mName = name;
        return this;
    }

    @JsonGetter("birth_day")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBirthday() {
        return mBirthday;
    }

    public ChildModel setBirthday(String birthday) {
        mBirthday = birthday;
        return this;
    }

    @JsonGetter("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getId() {
        return mId;
    }

    public ChildModel setId(String id) {
        mId = id;
        return this;
    }

    @JsonGetter("client_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getClientId() {
        return mClientId;
    }

    public ChildModel setClientId(String clientId) {
        mClientId = clientId;
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
        ChildModel that = (ChildModel) o;
        return Objects.equal(mName, that.mName) &&
                Objects.equal(mBirthday, that.mBirthday) &&
                Objects.equal(mId, that.mId) &&
                Objects.equal(mClientId, that.mClientId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mName, mBirthday, mId, mClientId);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mBirthday);
        dest.writeString(this.mId);
        dest.writeString(this.mClientId);
    }

    protected ChildModel(Parcel in) {
        this.mName = in.readString();
        this.mBirthday = in.readString();
        this.mId = in.readString();
        this.mClientId = in.readString();
    }

    public static final Creator<ChildModel> CREATOR = new Creator<ChildModel>() {
        @Override
        public ChildModel createFromParcel(Parcel source) {
            return new ChildModel(source);
        }

        @Override
        public ChildModel[] newArray(int size) {
            return new ChildModel[size];
        }
    };

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mName", mName)
                .add("mBirthday", mBirthday)
                .add("mId", mId)
                .add("mClientId", mClientId)
                .toString();
    }
}
