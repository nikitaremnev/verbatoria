package com.verbatoria.data.network.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildModel implements Parcelable {

    private String mName;

    private String mBirthday;

    private String mId;

    private String mClientId;

    private String mGender;

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
    @Nullable
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

    @JsonGetter("gender")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGender() {
        return mGender;
    }

    public ChildModel setGender(String gender) {
        mGender = gender;
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
        return Objects.equals(mName, that.mName) &&
                Objects.equals(mBirthday, that.mBirthday) &&
                Objects.equals(mId, that.mId) &&
                Objects.equals(mClientId, that.mClientId) &&
                Objects.equals(mGender, that.mGender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mName, mBirthday, mId, mClientId, mGender);
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
        dest.writeString(this.mGender);
    }

    protected ChildModel(Parcel in) {
        this.mName = in.readString();
        this.mBirthday = in.readString();
        this.mId = in.readString();
        this.mClientId = in.readString();
        this.mGender = in.readString();
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
        return Objects.toString(this);
    }

}
