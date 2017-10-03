package com.verbatoria.data.network.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * @author nikitaremnev
 */

public class ClientModel implements Parcelable {

    private String mName;

    private String mEmail;

    private String mPhone;

    public ClientModel() {

    }

    @JsonGetter("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() {
        return mName;
    }

    public ClientModel setName(String name) {
        mName = name;
        return this;
    }

    @JsonGetter("email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEmail() {
        return mEmail;
    }

    public ClientModel setEmail(String email) {
        mEmail = email;
        return this;
    }

    @JsonGetter("phone")
    @Nullable
    public String getPhone() {
        return mPhone;
    }

    public ClientModel setPhone(String phone) {
        mPhone = phone;
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
        ClientModel that = (ClientModel) o;
        return Objects.equal(mName, that.mName) &&
                Objects.equal(mEmail, that.mEmail) &&
                Objects.equal(mPhone, that.mPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mName, mEmail, mPhone);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mName", mName)
                .add("mEmail", mEmail)
                .add("mPhone", mPhone)
                .toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mEmail);
        dest.writeString(this.mPhone);
    }

    protected ClientModel(Parcel in) {
        this.mName = in.readString();
        this.mEmail = in.readString();
        this.mPhone = in.readString();
    }

    public static final Parcelable.Creator<ClientModel> CREATOR = new Parcelable.Creator<ClientModel>() {
        @Override
        public ClientModel createFromParcel(Parcel source) {
            return new ClientModel(source);
        }

        @Override
        public ClientModel[] newArray(int size) {
            return new ClientModel[size];
        }
    };
}
