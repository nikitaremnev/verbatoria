package com.verbatoria.business.dashboard.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Реализация модели для записи нейрометрии
 *
 * @author nikitaremnev
 */
public class LocationModel implements Parcelable {

    private String mName;

    private String mAddress;

    private String mPartner;

    private String mCity;

    private String mCountry;

    public LocationModel() {

    }

    public String getName() {
        return mName;
    }

    public LocationModel setName(String name) {
        mName = name;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public LocationModel setAddress(String address) {
        mAddress = address;
        return this;
    }

    public String getPartner() {
        return mPartner;
    }

    public LocationModel setPartner(String partner) {
        mPartner = partner;
        return this;
    }

    public String getCity() {
        return mCity;
    }

    public LocationModel setCity(String city) {
        mCity = city;
        return this;
    }

    public String getCountry() {
        return mCountry;
    }

    public LocationModel setCountry(String country) {
        mCountry = country;
        return this;
    }

    public String getCityCountry() {
        return mCity + ", " + mCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocationModel that = (LocationModel) o;
        return Objects.equal(mName, that.mName) &&
                Objects.equal(mAddress, that.mAddress) &&
                Objects.equal(mPartner, that.mPartner) &&
                Objects.equal(mCity, that.mCity) &&
                Objects.equal(mCountry, that.mCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mName, mAddress, mPartner, mCity, mCountry);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mName", mName)
                .add("mAddress", mAddress)
                .add("mPartner", mPartner)
                .add("mCity", mCity)
                .add("mCountry", mCountry)
                .toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mAddress);
        dest.writeString(this.mPartner);
        dest.writeString(this.mCity);
        dest.writeString(this.mCountry);
    }

    protected LocationModel(Parcel in) {
        this.mName = in.readString();
        this.mAddress = in.readString();
        this.mPartner = in.readString();
        this.mCity = in.readString();
        this.mCountry = in.readString();
    }

    public static final Creator<LocationModel> CREATOR = new Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel source) {
            return new LocationModel(source);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };
}