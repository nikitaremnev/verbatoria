package com.verbatoria.data.network.request;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 * @author nikitaremnev
 */

public class ClientRequestModel implements Parcelable {

    private String mName;

    private String mEmail;

    private String mPhone;

    private String mPhoneCountry;

    public ClientRequestModel() {

    }

    @JsonGetter("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() {
        return mName;
    }

    public ClientRequestModel setName(String name) {
        mName = name;
        return this;
    }

    @JsonGetter("email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEmail() {
        return mEmail;
    }

    public ClientRequestModel setEmail(String email) {
        mEmail = email;
        return this;
    }

    @JsonGetter("phone")
    @Nullable
    public String getPhone() {
        return mPhone;
    }

    public ClientRequestModel setPhone(String phone) {
        mPhone = phone;
        return this;
    }

    @JsonGetter("phone_country")
    @Nullable
    public String getPhoneCountry() {
        return mPhoneCountry;
    }

    public ClientRequestModel setPhoneCountry(String phoneCountry) {
        mPhoneCountry = phoneCountry;
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
        ClientRequestModel that = (ClientRequestModel) o;
        return Objects.equals(mName, that.mName) &&
                Objects.equals(mEmail, that.mEmail) &&
                Objects.equals(mPhone, that.mPhone) &&
                Objects.equals(mPhoneCountry, that.mPhoneCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mName, mEmail, mPhone, mPhoneCountry);
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
        dest.writeString(this.mName);
        dest.writeString(this.mEmail);
        dest.writeString(this.mPhone);
        dest.writeString(this.mPhoneCountry);
    }

    protected ClientRequestModel(Parcel in) {
        this.mName = in.readString();
        this.mEmail = in.readString();
        this.mPhone = in.readString();
        this.mPhoneCountry = in.readString();
    }

    public static final Parcelable.Creator<ClientRequestModel> CREATOR = new Parcelable.Creator<ClientRequestModel>() {
        @Override
        public ClientRequestModel createFromParcel(Parcel source) {
            return new ClientRequestModel(source);
        }

        @Override
        public ClientRequestModel[] newArray(int size) {
            return new ClientRequestModel[size];
        }
    };
}
